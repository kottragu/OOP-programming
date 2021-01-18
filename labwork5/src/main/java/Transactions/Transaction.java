package Transactions;

import java.util.UUID;

public interface Transaction {
    public UUID getId();
    public Transaction getTransaction();
    public String getTo();
    public String getFrom();
    public TypeOfTransaction getType();
    public Double getMoney();
}
