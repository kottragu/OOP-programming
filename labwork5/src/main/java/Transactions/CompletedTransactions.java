package Transactions;

import java.util.ArrayList;
import java.util.UUID;

public class CompletedTransactions {
    private static CompletedTransactions thisClass = null;
    private ArrayList<ITransaction> completedTransactions;

    private CompletedTransactions() {
        completedTransactions = new ArrayList<ITransaction>();
    }

    public static CompletedTransactions getCompletedTransactions() {
        if (thisClass == null)
            thisClass = new CompletedTransactions();
        return thisClass;
    }

    public void addTransaction(ITransaction transaction) {
        completedTransactions.add(transaction);
    }

    public ITransaction getTransaction(UUID transactionID) {
        for (ITransaction t: completedTransactions) {
            if (t.getId().equals(transactionID)) {
                return t;
            }
        }
        return null;
    }

    public ITransaction getLastTransaction() throws Exception {
        if (completedTransactions.size() > 0)
            return completedTransactions.get(completedTransactions.size()-1);
        throw new Exception("Unavailable request");
    }
    public boolean isExist(UUID transactionID) {
        return completedTransactions.stream().anyMatch(tr -> tr.getId().equals(transactionID));
    }

    public void removeTransaction(ITransaction transaction) {
        completedTransactions.remove(transaction);
    }
}
