package Delete;

import Backup.RestorePoint;
import java.io.File;

public class SeparatedDeleteAlgorithm implements IDeleteAlgorithm {

    @Override
    public void delete(RestorePoint rp) {
        File file = new File(rp.getResultPath());
        for(File _file: file.listFiles()) {
            _file.delete();
        }
        file.delete();
    }
}
