package Delete;

import Backup.RestorePoint;

import java.util.ArrayList;

public interface IClearingAlgo {
    public ArrayList<RestorePoint> clear(ArrayList<RestorePoint> rps);
}
