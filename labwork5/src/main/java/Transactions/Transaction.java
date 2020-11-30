package Transactions;

import java.util.UUID;

public class Transaction implements ITransaction {
    private final String from;
    private final String to;
    private final Double money;
    private final UUID id;

    public Transaction(String from, String to, Double howMuch) {
        this.from = from;
        this.to = to;
        money = howMuch;
        id = UUID.randomUUID();
    }

    public Transaction getTransaction() {
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public Double getMoney() {
        return money;
    }
}
