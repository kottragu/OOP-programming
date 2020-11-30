package Delete;

import Backup.RestorePoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClearingRP {

    public ClearingRP() {
    }

    private void checkDuplicate(ArrayList<RestorePoint> rps) {
        Set<RestorePoint> set = new HashSet<>(rps);
        rps.clear();
        rps.addAll(set);
    }

    public ArrayList<RestorePoint> check(ArrayList<RestorePoint> rps, int maxCount) {
        ArrayList<RestorePoint> result = new ArrayList<>();
        for (RestorePoint rp: rps) {
            int count = 0;
            for (RestorePoint _rp: rps) {
                if (rp.equals(_rp)) {
                    count++;
                }
            }
            if (count == maxCount) {
                result.add(rp);
            }
        }
        checkDuplicate(result);
        return result;
    }

    public ArrayList<RestorePoint> Clear(TypeOfClearing typeOfClearing, ArrayList<RestorePoint> rps, IClearingAlgo ... clearingAlgos) {
        ArrayList<RestorePoint> removeRps = new ArrayList<>();
        if (typeOfClearing.equals(TypeOfClearing.ONE)) {
            for (IClearingAlgo algo : clearingAlgos) {
                removeRps.addAll(algo.clear(rps));
            }
            checkDuplicate(removeRps);
        } else if (typeOfClearing.equals(TypeOfClearing.ALL)){
            ArrayList<RestorePoint> _removeRps = new ArrayList<>();
            for (IClearingAlgo algo : clearingAlgos) {
                _removeRps.addAll(algo.clear(rps));
            }
            removeRps.addAll(check(_removeRps, clearingAlgos.length));
        }
        return removeRps;
    }
}
