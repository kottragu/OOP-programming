package BankAccount;

import java.util.UUID;

public interface BankAccount {
    public void nextDay();
    public void addMoney(double money);
    public boolean withdrawMoney(double money);
    public void hardWithdrawMoney(double money);
    public String getId();
    public double getMoney();
}
