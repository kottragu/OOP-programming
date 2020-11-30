package Delete;

import Backup.RestorePoint;

import java.util.ArrayList;

public class ClearingAlgoSize implements IClearingAlgo {
    private long size;
    public ClearingAlgoSize(long size) {
        this.size = size;
    }
    @Override
    public ArrayList<RestorePoint> clear(ArrayList<RestorePoint> rps) {
        ArrayList<RestorePoint> removeRps = new ArrayList<>();
        long existSize = 0;
        for (RestorePoint rp: rps) {
            existSize += rp.getSize();
        }
        int i = 0;
        while (existSize > size) {
            existSize -= rps.get(i).getSize();
            removeRps.add(rps.get(i));
            i++;
        }
        return removeRps;
    }
}
