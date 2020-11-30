package Create;

import Backup.TypeOfAlgo;

import java.util.ArrayList;

public interface ICreateAlgorithm {

    public void create(ArrayList<String> filePaths, String backupPath) throws Exception;

    public long getSize(String backupPath);
    public TypeOfAlgo getType();
}
