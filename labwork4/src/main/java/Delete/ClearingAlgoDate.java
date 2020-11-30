package Delete;

import Backup.RestorePoint;

import java.util.ArrayList;
import java.util.Date;

public class ClearingAlgoDate implements IClearingAlgo {
    private Date date;

    ClearingAlgoDate (Date date){
        this.date = date;
    }

    @Override
    public ArrayList<RestorePoint> clear(ArrayList<RestorePoint> rps) {
        ArrayList<RestorePoint> result = new ArrayList<>();
        for (RestorePoint rp: rps) {
            if (rp.getDate().before(date)) {
                result.add(rp);
            }
        }
        return result;
    }
}
