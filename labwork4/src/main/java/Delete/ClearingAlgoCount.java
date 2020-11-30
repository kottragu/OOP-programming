package Delete;

import Backup.RestorePoint;

import java.util.ArrayList;

public class ClearingAlgoCount implements IClearingAlgo {
    int count;

    public ClearingAlgoCount(int count) {
        this.count = count;
    }

    @Override
    public ArrayList<RestorePoint> clear(ArrayList<RestorePoint> rps) {
        ArrayList<RestorePoint> removeRps = new ArrayList<>();
        for (int i = 0; i < rps.size() - count; i++) {
            removeRps.add(rps.get(i));
        }
        return removeRps;
    }
}
