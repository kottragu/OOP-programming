package Bank;

import Bank.Client.Builder;
import Bank.Client.Client;
import Bank.Client.ClientBuilder;
import BankAccount.Credit;
import BankAccount.Debit;
import BankAccount.Deposit;
import BankAccount.BankAccount;
import Transactions.CompletedTransactions;
import Transactions.Transaction;
import Transactions.TypeOfTransaction;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public abstract class Bank {
    static Integer count = 0;
    static ArrayList<BankMethod> banks = new ArrayList<> ();

    public static ArrayList<BankMethod> getBanks() {
        return banks;
    }

    public UUID createClient(String firstName, String secondName, Map<Client, ArrayList<BankAccount>> clients) throws Exception {
        Builder builder = new ClientBuilder();
        Client client = builder.setName(firstName, secondName).create();
        clients.put(client, new ArrayList<>());
        return client.getId();
    }

    public UUID updateClient (UUID id, Map<Client, ArrayList<BankAccount>> clients, Integer passport) throws Exception {
        Client oldClient = getClient(id, clients);
        ArrayList<BankAccount> bankAccounts = clients.get(oldClient);
        Builder builder = new ClientBuilder();
        Client newClient = builder.updateClient(oldClient).setPassport(passport).create();
        clients.remove(oldClient);
        clients.put(newClient, bankAccounts);
        return newClient.getId();
    }

    public UUID updateClient (UUID id, Map<Client, ArrayList<BankAccount>> clients, String address) throws Exception {
        Client oldClient = getClient(id, clients);
        ArrayList<BankAccount> bankAccounts = clients.get(oldClient);
        Builder builder = new ClientBuilder();
        Client newClient = builder.updateClient(oldClient).setAddress(address).create();
        clients.remove(oldClient);
        clients.put(newClient, bankAccounts);
        return newClient.getId();
    }
    public UUID updateClient (UUID id, Map<Client, ArrayList<BankAccount>> clients, Integer passport, String address) throws Exception {
        Client oldClient = getClient(id, clients);
        ArrayList<BankAccount> bankAccounts = clients.get(oldClient);
        Builder builder = new ClientBuilder();
        Client newClient = builder.updateClient(oldClient).setAddress(address).setPassport(passport).create();
        clients.remove(oldClient);
        clients.put(newClient, bankAccounts);
        return newClient.getId();
    }
    private boolean checkExistClient(UUID clientID, Map<Client, ArrayList<BankAccount>> clients) throws Exception {
        if (clients.keySet().stream().noneMatch(id -> id.getId().equals(clientID))) {
            throw new Exception("Client with " + clientID + " id doesn't exist");
        }
        return true;
    }


    private Client getClient(UUID clientId, Map<Client, ArrayList<BankAccount>> clients) throws Exception {
        checkExistClient(clientId, clients);
        for(Client c: clients.keySet()) {
            if (c.getId().equals(clientId)) {
                return c;
            }
        }
        return null; //вообще оно невозможно, тк из-за checkExistClient у нас он гарантированно существует
    }

    private double findPercent(double money, Map<Double, Double> depositCondition) {
        Double highPercent = 0.0;
        double tempMoney = 0.0;
        for (Double depositMoney: depositCondition.keySet()) {
            if (depositMoney < money && depositMoney > tempMoney) {
                tempMoney = depositMoney;
                highPercent = depositCondition.get(depositMoney);
            }
        }
        return highPercent;
    }


    public String createDebitBankAccount(UUID clientID, Map<Client, ArrayList<BankAccount>> clients, double debitPercent, String bankID) throws Exception {
        checkExistClient(clientID, clients);
        BankAccount bankAccount = new Debit(debitPercent/365,bankID);
        clients.get(getClient(clientID,clients)).add(bankAccount);
        return bankAccount.getId();
    }


    public String createDepositBankAccount(UUID clientID, double  money, Integer days, Map<Double, Double> depositCondition, Map<Client, ArrayList<BankAccount>> clients, String bankID) throws Exception {
        checkExistClient(clientID,clients);
        BankAccount bankAccount = new Deposit(money,days, findPercent(money,depositCondition)/365.0, bankID);
        clients.get(getClient(clientID,clients)).add(bankAccount);
        return bankAccount.getId();
    }


    public String createCreditAccount(UUID clientID, Map<Client, ArrayList<BankAccount>> clients, String bankID, double limit, double commission) throws Exception {
        checkExistClient(clientID, clients);
        BankAccount bankAccount = new Credit(limit,commission,bankID);
        clients.get(getClient(clientID,clients)).add(bankAccount);
        return  bankAccount.getId();
    }

    private boolean transferAndWithdraw(double money, String bankAccountID, double number, Map<Client, ArrayList<BankAccount>> clients) {
        for (Client client: clients.keySet()) {
            for (BankAccount bankAccount: clients.get(client)) {
                if (bankAccountID.equals(bankAccount.getId()) && !client.isDoubtful()) {
                    return bankAccount.withdrawMoney(money);
                } else if (bankAccountID.equals(bankAccount.getId()) && client.isDoubtful() && money <= number) {
                    return  bankAccount.withdrawMoney(money);
                }
            }
        }
        return false;
    }


    public boolean transfer(double money, String bankAccountIdFrom, String bankAccountIdTo, BankMethod _bank) throws Exception {
        BankMethod bankFrom = null;
        BankMethod bankTo = null;
        for (BankMethod bank: banks) {
            if (bank.getId().equals(bankAccountIdFrom.substring(0,1))) {
                bankFrom = bank;
            }
            if (bank.getId().equals(bankAccountIdTo.substring(0,1))) {
                bankTo = bank;
            }
        }
        if (bankFrom == null || bankTo == null) {
            throw new Exception("At least 1 of this bank account doesn't exist");
        }

        if (bankFrom.existAccount(bankAccountIdFrom) && bankTo.existAccount(bankAccountIdTo)){
            if (transferAndWithdraw(money,bankAccountIdFrom, bankFrom.getLimitDoubtfulTransfer(), bankFrom.getClients())) {
                zp(money, bankAccountIdTo, bankTo.getClients());
                return true;
            }
        }
        return false;
    }

    public boolean zp(double money, String bankAccountID, Map<Client, ArrayList<BankAccount>> clients) {
        for (Client client: clients.keySet()) {
            for (BankAccount bankAccount: clients.get(client)) {
                if (bankAccount.getId().equals(bankAccountID)) {
                    bankAccount.addMoney(money);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean withdraw(double money, String bankAccountID, double limitDoubtfulWithdraw, Map<Client, ArrayList<BankAccount>> clients) {
        return transferAndWithdraw(money, bankAccountID, limitDoubtfulWithdraw, clients);
    }

    private boolean hardWithdrawMoney(Transaction t) {
        for (BankMethod bank: banks) {
            for (Client client : bank.getClients().keySet()) {
                for (BankAccount _account : bank.getClients().get(client)) {
                    if (_account.getId().equals(t.getTo())) {
                        _account.hardWithdrawMoney(t.getMoney());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hardAddMoney(Transaction t) {
        for (BankMethod bank: banks) {
            for (Client client : bank.getClients().keySet()) {
                for (BankAccount _account : bank.getClients().get(client)) {
                    if (_account.getId().equals(t.getFrom())) {
                        _account.addMoney(t.getMoney());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean cancelTransaction(UUID transactionID, Map<Client, ArrayList<BankAccount>> clients, String bankID) {
        if (!CompletedTransactions.getCompletedTransactions().isExist(transactionID))
            return false;

        Transaction t = CompletedTransactions.getCompletedTransactions().getTransaction(transactionID);

        if (t.getType().equals(TypeOfTransaction.ZARPLATA)) { //если было зачисление
            if (!t.getTo().substring(0, 1).equals(bankID)) {
                return false;
            } else {
                return hardWithdrawMoney(t);
            }
        } else if (t.getType().equals(TypeOfTransaction.WITHDRAW)) { // если было снятие
            if (!t.getFrom().substring(0, 1).equals(bankID)) {
                return false;
            } else {
                return hardAddMoney(t);
            }
        } else if (t.getType().equals(TypeOfTransaction.TRANSFER)) {
            if (!t.getFrom().substring(0, 1).equals(bankID) && !t.getTo().substring(0, 1).equals(bankID)) {
                return false;
            } else {
                return hardAddMoney(t) && hardWithdrawMoney(t);
            }
        }
        return false;
    }
}