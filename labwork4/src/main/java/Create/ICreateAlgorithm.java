package Create;

import java.util.ArrayList;

public interface ICreateAlgorithm {

    public void setData(ArrayList<String> filePaths, String backupPath) throws Exception;

    public long getSize(String backupPath);
}
