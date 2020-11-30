package BankAccount;

import java.util.UUID;

public class Deposit implements IBankAccount {
    double money;
    double everyDayPercent;
    String id;
    Integer days;
    int month = 30;
    Double monthlyMoney = 0.0;
    boolean block = true;

    public Deposit(Double money, Integer days, double everyDayPercent, String bankID) {
        this.money = money;
        this.days = days;
        this.everyDayPercent = everyDayPercent;
        id = bankID + UUID.randomUUID().toString();
    }

    @Override
    public void nextDay() {
        days--;
        month--;
        if (month != 0) {
            monthlyMoney += money*everyDayPercent;
        } else {
            money += money * everyDayPercent + monthlyMoney;
            monthlyMoney = 0.0;
            month = 30;
        }
        if (days.equals(0)) {
            block = false;
        }
    }

    @Override
    public void addMoney(double money) {
        this.money += money;
    }

    @Override
    public boolean withdrawMoney(double money) {
        if (!block && this.money >= money) {
            this.money -= money;
            return true;
        }
        return false;
    }

    @Override
    public void hardWithdrawMoney(double money) {
        this.money -= money;
    }

    public void startNewTime(Integer days, double everyDayPercent) {
        this.days = days;
        this.everyDayPercent = everyDayPercent;
        block = true;
    }

    public String getId() {
        return id;
    }

    @Override
    public double getMoney() {
        return money;
    }
}
