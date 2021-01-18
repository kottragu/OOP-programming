package Transactions;

import java.util.UUID;

public interface ITransaction {
    public UUID getId();
    public ITransaction getTransaction();
    public String getTo();
    public String getFrom();
    public Double getMoney();
}
