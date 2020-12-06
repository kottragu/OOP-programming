package Bank;

import Bank.Client.Client;
import BankAccount.BankAccount;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public interface BankMethod {
    public UUID createClient(String firstName, String secondName) throws Exception;
    public String createDebitBankAccount(UUID clientID) throws Exception;
    public String createDepositBankAccount(UUID clientID, double  money, Integer days) throws Exception;
    public String createCreditAccount(UUID clientID) throws Exception;
    public boolean withdraw(double money, String bankAccountID);
    public boolean addMoney(double money, String bankAccountID);
    public double getMoney(String bankAccountId) throws Exception;
    public String getId();
    public Client getClient(UUID id) throws Exception;
    public boolean existAccount(String id);
    public boolean transfer(String bankAccountIdFrom, String bankAccountIdTo, double money) throws Exception;
    public UUID updateClient(UUID clientID, String address) throws Exception;
    public UUID updateClient(UUID clientID, Integer passport) throws Exception;
    public UUID updateClient(UUID clientID, String address, Integer passport) throws Exception;
    public Map<Client, ArrayList<BankAccount>> getClients();
    public double getLimitDoubtfulTransfer();
    public double getLimitDoubtfulWithdraw();
    public double getCommission();
    public double getLimit();
    public boolean cancelTransaction(UUID transaction) throws Exception;
    public void nextDay();
}
