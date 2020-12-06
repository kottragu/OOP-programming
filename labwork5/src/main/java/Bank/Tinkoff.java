package Bank;

import Bank.Client.Client;
import BankAccount.BankAccount;
import Transactions.CompletedTransactions;
import Transactions.DefaultTransaction;
import Transactions.TypeOfTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tinkoff extends Bank implements BankMethod {
    private static Tinkoff tinkoff = null;
    private Map<Client, ArrayList<BankAccount>> clients;
    private double debitPercent = 0.03;
    private String bankID;
    private Map<Double, Double> depositCondition; //сумма-проценты

    private double commission = 0.033;
    private double limit = 10000;

    private double limitDoubtfulTransfer = 4000;
    private double limitDoubtfulWithdraw = 2500;

    private Tinkoff() {
        clients = new HashMap<>();
        bankID = count.toString();
        count++;
        depositCondition = new HashMap<>();
        depositCondition.put(15000.0, 0.049);
        depositCondition.put(28000.0, 0.057);
        depositCondition.put(80000.0, 0.076);
        depositCondition.put(115000.0, 0.082);
    }

    public static Tinkoff getTinkoff() {
        if (tinkoff == null){
            tinkoff = new Tinkoff();
            banks.add(Tinkoff.getTinkoff());
        }
        return tinkoff;
    }

    @Override
    public Client getClient(UUID id) throws Exception {
        for (Client client: clients.keySet()) {
            if (client.getId().equals(id)) {
                return client;
            }
        }
        throw new Exception("Client doesn't exit");
    }

    public UUID createClient(String firstName, String secondName) throws Exception {
        return super.createClient(firstName, secondName, clients);
    }

    public UUID updateClient(UUID clientID, String address) throws Exception {
        return super.updateClient(clientID, clients, address);
    }

    public UUID updateClient(UUID clientID, Integer passport) throws Exception {
        return super.updateClient(clientID, clients, passport);
    }

    public UUID updateClient(UUID clientID, String address, Integer passport) throws Exception {
        return super.updateClient(clientID, clients, passport, address);
    }

    public boolean withdraw(double money, String bankAccountID) {
        if (!bankAccountID.substring(0, 1).equals(bankID))
            return false;
        boolean result = super.withdraw(money, bankAccountID, limitDoubtfulWithdraw, clients);
        if (result)
            CompletedTransactions.getCompletedTransactions().addTransaction(new DefaultTransaction(bankAccountID, null, money, TypeOfTransaction.WITHDRAW));
        return result;
    }

    @Override
    public boolean addMoney(double money, String bankAccountID) {
        if (!bankAccountID.substring(0, 1).equals(bankID))
            return false;
        boolean result = super.zp(money, bankAccountID, clients);
        if (result)
            CompletedTransactions.getCompletedTransactions().addTransaction(new DefaultTransaction(null, bankAccountID, money, TypeOfTransaction.ZARPLATA));
        return result;
    }

    @Override
    public String getId() {
        return bankID;
    }

    @Override
    public boolean existAccount(String id) {
        for (Client client : clients.keySet()) {
            if (clients.get(client).stream().anyMatch(account -> account.getId().equals(id))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double getMoney(String bankAccountId) throws Exception {
        for (Client client: clients.keySet()) {
            for (BankAccount bankAccount: clients.get(client)) {
                if (bankAccount.getId().equals(bankAccountId)) {
                    return bankAccount.getMoney();
                }
            }
        }
        throw new Exception("вложенны в капитал прожиточного минимума");
    }

    @Override
    public String createCreditAccount(UUID clientID) throws Exception {
        return super.createCreditAccount(clientID, clients, bankID, limit, commission);
    }

    public String createDebitBankAccount(UUID clientID) throws Exception {
        return super.createDebitBankAccount(clientID, clients, debitPercent, bankID);
    }

    public String createDepositBankAccount(UUID clientID, double money, Integer days) throws Exception {
        return super.createDepositBankAccount(clientID, money, days, depositCondition, clients, bankID);
    }


    public boolean transfer(String bankAccountIdFrom, String bankAccountIdTo, double money) throws Exception {
        if (!bankAccountIdFrom.substring(0, 1).equals(bankID))
            return false;
        boolean result = super.transfer(money, bankAccountIdFrom, bankAccountIdTo, this);
        if (result)
            CompletedTransactions.getCompletedTransactions().addTransaction(new DefaultTransaction(bankAccountIdFrom, bankAccountIdTo, money, TypeOfTransaction.TRANSFER));
        return result;
    }

    @Override
    public Map<Client, ArrayList<BankAccount>> getClients() {
        return clients;
    }

    @Override
    public double getLimitDoubtfulTransfer() {
        return limitDoubtfulTransfer;
    }

    @Override
    public double getLimitDoubtfulWithdraw() {
        return limitDoubtfulWithdraw;
    }

    @Override
    public double getCommission() {
        return commission;
    }

    @Override
    public double getLimit() {
        return limit;
    }

    @Override
    public boolean cancelTransaction(UUID transactionID) {
        return super.cancelTransaction(transactionID, clients, bankID);
    }

    @Override
    public void nextDay() {
        for (Client client: clients.keySet()) {
            clients.get(client).forEach(BankAccount::nextDay);
        }
    }
}
