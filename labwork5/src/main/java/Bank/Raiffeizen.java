package Bank;

import Bank.Client.Client;
import BankAccount.IBankAccount;
import Transactions.CompletedTransactions;
import Transactions.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Raiffeizen extends Bank implements IBank{
    private static Raiffeizen raiffeizen = null;
    private Map<Client, ArrayList<IBankAccount>> clients;
    private double debitPercent = 0.035;
    private String bankID;
    private Map<Double, Double> depositCondition; //сумма-проценты

    private double commission = 0.04;
    private double limit = 20000;

    private double limitDoubtfulTransfer = 7000;
    private double limitDoubtfulWithdraw = 4000;

    private Raiffeizen() {
        clients = new HashMap<>();
        bankID = count.toString();
        count++;
        depositCondition = new HashMap<>();
        depositCondition.put(23000.0, 0.046);
        depositCondition.put(39000.0, 0.052);
        depositCondition.put(720000.0, 0.069);
        depositCondition.put(108000.0, 0.079);
    }

    public static Raiffeizen getRaiffeizen() {
        if (raiffeizen == null) {
            raiffeizen = new Raiffeizen();
            banks.add(Raiffeizen.getRaiffeizen());
        }
        return raiffeizen;
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
    @Override
    public UUID createClient(String firstName, String secondName) {
        return super.createClient(firstName, secondName, clients);
    }

    @Override
    public boolean withdraw(double money, String bankAccountID) {
        if (!bankAccountID.substring(0, 1).equals(bankID))
            return false;
        boolean result = super.withdraw(money, bankAccountID, limitDoubtfulWithdraw, clients);
        if (result)
            CompletedTransactions.getCompletedTransactions().addTransaction(new Transaction(bankAccountID, null, money));
        return result;
    }

    @Override
    public boolean addMoney(double money, String bankAccountID) {
        if (!bankAccountID.substring(0, 1).equals(bankID))
            return false;
        boolean result = super.zp(money, bankAccountID, clients);
        if (result)
            CompletedTransactions.getCompletedTransactions().addTransaction(new Transaction(null, bankAccountID, money));
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
            for (IBankAccount bankAccount: clients.get(client)) {
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
            CompletedTransactions.getCompletedTransactions().addTransaction(new Transaction(bankAccountIdFrom, bankAccountIdTo, money));
        return result;
    }

    @Override
    public Map<Client, ArrayList<IBankAccount>> getClients() {
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
            clients.get(client).forEach(IBankAccount::nextDay);
        }
    }
}
