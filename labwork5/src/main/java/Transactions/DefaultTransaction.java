package Transactions;

import java.util.UUID;

public class DefaultTransaction implements Transaction {
    private final String from;
    private final String to;
    private final Double money;
    private final UUID id;
    private TypeOfTransaction type;

    public DefaultTransaction(String from, String to, Double howMuch, TypeOfTransaction type) {
        this.from = from;
        this.to = to;
        this.type = type;
        money = howMuch;
        id = UUID.randomUUID();
    }

    @Override
    public DefaultTransaction getTransaction() {
        return this;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public TypeOfTransaction getType() {
        return type;
    }

    @Override
    public Double getMoney() {
        return money;
    }
}
