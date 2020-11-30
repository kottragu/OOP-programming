package BankAccount;

import java.util.UUID;

public class Debit implements IBankAccount {
    double money = 0;
    double everyDayPercent;
    String id;
    int month = 30;
    double monthlyMoney = 0;

    public Debit(double everyDayPercent, String bankID) {
        this.everyDayPercent = everyDayPercent;
        id = bankID + UUID.randomUUID().toString();
    }

    public Debit(Double money, double everyDayPercent, String bankID) {
        this.money = money;
        this.everyDayPercent = everyDayPercent;
        id = bankID + UUID.randomUUID().toString();
    }

    @Override
    public void nextDay() {
        month--;
        if (monthlyMoney != 0) {
            monthlyMoney += money * everyDayPercent;
        } else {
            money += money * everyDayPercent + monthlyMoney;
            monthlyMoney = 0.0;
            month = 30;
        }
    }

    @Override
    public void addMoney(double money) {
        this.money += money;
    }

    @Override
    public boolean withdrawMoney(double money) {
        if (this.money >= money) {
            this.money -= money;
            return true;
        }
        return false;
    }

    @Override
    public void hardWithdrawMoney(double money) {
        this.money -= money;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getMoney() {
        return money;
    }
}
