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

    public ArrayList<RestorePoint> Clear(ArrayList<RestorePoint> rps, IClearingAlgo ... clearingAlgos) {
        ArrayList<RestorePoint> removeRps = new ArrayList<>();
        for (IClearingAlgo algo: clearingAlgos) {
            removeRps.addAll(algo.clear(rps));
        }

        checkDuplicate(removeRps);
        return removeRps;
    }
}
