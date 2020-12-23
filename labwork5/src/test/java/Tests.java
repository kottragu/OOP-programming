import Bank.Raiffeizen;
import Bank.Sberbank;
import Bank.Tinkoff;
import TimeMachine.TimeMachine;
import Transactions.CompletedTransactions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void test1() throws Exception {
        UUID sc = Sberbank.getSberbank().createClient("Василий","Петрович");
        Throwable exception = assertThrows(Exception.class, () -> Raiffeizen.getRaiffeizen().createCreditAccount(sc));
        assertTrue(exception.getMessage().contains("id doesn't exist"));
    }

    @Test
    public void test2() throws Exception {
        Tinkoff t = Tinkoff.getTinkoff();
        Raiffeizen r = Raiffeizen.getRaiffeizen();
        UUID rc1 = r.createClient("Дядя", "Вася");
        String rid = r.createDepositBankAccount(rc1, 25000, 25);
        TimeMachine tm = new TimeMachine();

        tm.timeGo(31);
        assertEquals((int) r.getMoney(rid), 25094);

        UUID tc1 = t.createClient("Дарт", "Вейдер");
        String tc1Credit = t.createCreditAccount(tc1);
        t.addMoney(50000.0,tc1Credit);
        Assert.assertFalse(t.withdraw(20000,tc1Credit));

        t.updateClient(tc1, "подземный переход в Купчино");
        Assert.assertTrue(t.withdraw(20000,tc1Credit));

        Assert.assertTrue(t.transfer(tc1Credit, rid, 25000));
        Throwable exception = assertThrows(Exception.class,() -> t.getMoney(rid));
        assertEquals(exception.getMessage(), "вложенны в капитал прожиточного минимума");

        assertEquals(t.getMoney(tc1Credit), 5000);
        assertEquals(50094, (int) r.getMoney(rid));

        UUID lastTransaction = CompletedTransactions.getCompletedTransactions().getLastTransaction().getId();
        Assert.assertTrue(t.cancelTransaction(lastTransaction));
        assertEquals(25094, (int) r.getMoney(rid));
        assertEquals(30000, t.getMoney(tc1Credit));
    }

}
