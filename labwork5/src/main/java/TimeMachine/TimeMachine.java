package TimeMachine;

import Bank.*;
public class TimeMachine extends Bank  {
    public void timeGo(int days) throws Exception {
        if (days < 0) {
            throw new Exception("0 дней? Серьёзно?");
        }
        while (days != 0) {
            Bank.getBanks().forEach(IBank::nextDay);
            days--;
        }
    }
}
