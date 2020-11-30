package Bank;

import Bank.Client.Client;
import BankAccount.Credit;
import BankAccount.Debit;
import BankAccount.Deposit;
import BankAccount.IBankAccount;
import Transactions.CompletedTransactions;
import Transactions.ITransaction;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public abstract class Bank {
    static Integer count = 0;
    static ArrayList<IBank> banks = new ArrayList<> ();

    public static ArrayList<IBank> getBanks() {
        return banks;
    }

    public UUID createClient(String firstName, String secondName, Map<Client, ArrayList<IBankAccount>> clients) {
        Client client = new Client(firstName, secondName);
        clients.put(client, new ArrayList<>());
        return client.getId();
    }

    private void checkExistClient(UUID clientID, Map<Client, ArrayList<IBankAccount>> clients) throws Exception {
        if (clients.keySet().stream().noneMatch(id -> id.getId().equals(clientID))) {
            throw new Exception("Client with " + clientID + " id doesn't exist");
        }
    }


    private Client getClient(UUID clientId, Map<Client, ArrayList<IBankAccount>> clients) {
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


    public String createDebitBankAccount(UUID clientID, Map<Client, ArrayList<IBankAccount>> clients, double debitPercent, String bankID) throws Exception {
        checkExistClient(clientID, clients);
        IBankAccount bankAccount = new Debit(debitPercent/365,bankID);
        clients.get(getClient(clientID,clients)).add(bankAccount);
        return bankAccount.getId();
    }


    public String createDepositBankAccount(UUID clientID, double  money, Integer days, Map<Double, Double> depositCondition, Map<Client, ArrayList<IBankAccount>> clients, String bankID) throws Exception {
        checkExistClient(clientID,clients);
        IBankAccount bankAccount = new Deposit(money,days, findPercent(money,depositCondition)/365.0, bankID);
        clients.get(getClient(clientID,clients)).add(bankAccount);
        return bankAccount.getId();
    }


    public String createCreditAccount(UUID clientID, Map<Client, ArrayList<IBankAccount>> clients, String bankID, double limit, double commission) throws Exception {
        checkExistClient(clientID, clients);
        IBankAccount bankAccount = new Credit(limit,commission,bankID);
        clients.get(getClient(clientID,clients)).add(bankAccount);
        return  bankAccount.getId();
    }

    private boolean transferAndWithdraw(double money, String bankAccountID, double number, Map<Client, ArrayList<IBankAccount>> clients) {
        for (Client client: clients.keySet()) {
            for (IBankAccount bankAccount: clients.get(client)) {
                if (bankAccountID.equals(bankAccount.getId()) && !client.isDoubtful()) {
                    return bankAccount.withdrawMoney(money);
                } else if (bankAccountID.equals(bankAccount.getId()) && client.isDoubtful() && money <= number) {
                    return  bankAccount.withdrawMoney(money);
                }
                /* if (bankAccount.getId().equals(bankAccountID)) {
                    if (!client.isDoubtful() || money <= limitDoubtfulWithdraw) {
                        return bankAccount.withdrawMoney(money);
                    } else
                        return false;
                } */

                //вот эти два ифа делают одно и то же, спросить как лучше
            }
        }
        return false;
    }


    public boolean transfer(double money, String bankAccountIdFrom, String bankAccountIdTo, IBank _bank) throws Exception {
        IBank bankFrom = null;
        IBank bankTo = null;
        for (IBank bank: banks) {
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

    public boolean zp(double money, String bankAccountID, Map<Client, ArrayList<IBankAccount>> clients) {
        for (Client client: clients.keySet()) {
            for (IBankAccount bankAccount: clients.get(client)) {
                if (bankAccount.getId().equals(bankAccountID)) {
                    bankAccount.addMoney(money);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean withdraw(double money, String bankAccountID, double limitDoubtfulWithdraw, Map<Client, ArrayList<IBankAccount>> clients) {
        return transferAndWithdraw(money, bankAccountID, limitDoubtfulWithdraw, clients);
    }

    private boolean hardWithdrawMoney(ITransaction t) {
        for (IBank bank: banks) {
            for (Client client : bank.getClients().keySet()) {
                for (IBankAccount _account : bank.getClients().get(client)) {
                    if (_account.getId().equals(t.getTo())) {
                        _account.hardWithdrawMoney(t.getMoney());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hardAddMoney(ITransaction t) {
        for (IBank bank: banks) {
            for (Client client : bank.getClients().keySet()) {
                for (IBankAccount _account : bank.getClients().get(client)) {
                    if (_account.getId().equals(t.getFrom())) {
                        _account.addMoney(t.getMoney());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean cancelTransaction(UUID transactionID, Map<Client, ArrayList<IBankAccount>> clients, String bankID) {
        if (!CompletedTransactions.getCompletedTransactions().isExist(transactionID))
            return false;
        ITransaction t = CompletedTransactions.getCompletedTransactions().getTransaction(transactionID);
        if (t.getFrom() == null) { //если было зачисление
            if (!t.getTo().substring(0, 1).equals(bankID)) {
                return false;
            } else {
                return hardWithdrawMoney(t);
            }
        } else if (t.getTo() == null) { // если было снятие
            if (!t.getFrom().substring(0, 1).equals(bankID)) {
                return false;
            } else {
                return hardAddMoney(t);
            }
        } else if (t.getFrom() != null && t.getTo() != null) {
            if (!t.getFrom().substring(0, 1).equals(bankID) && !t.getTo().substring(0, 1).equals(bankID)) {
                return false;
            } else {
                return hardAddMoney(t) && hardWithdrawMoney(t);
            }
        }
        return false;
    }
}
