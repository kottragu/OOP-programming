package BankAccount;

import java.util.UUID;

public class Credit implements IBankAccount {
    Double limit; //по модулю!
    Double commission;
    String id;
    Double money = 0D;

    public Credit(double limit, double commission, String bankID) {
        this.limit = limit;
        this.commission = commission;
        id = bankID + UUID.randomUUID().toString();
    }

    Credit(double limit, double commission, String bankID, double money) {
        this(limit, commission, bankID);
        this.money = money;
    }

    @Override
    public void nextDay() {

    }

    @Override
    public void addMoney(double money) {
        this.money += money;
    }

    @Override
    public boolean withdrawMoney(double money) {
        if (this.money >= 0 && money <= this.money + limit) {
            this.money -= money;
            return true;
        } else if (this.money < 0 && money*(1 + commission) <= limit - Math.abs(this.money)) {
            this.money -= money*(1 + commission);
            return true;
        } else
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
