package Delete;

import Backup.RestorePoint;
import java.io.File;

public class SharedDeleteAlgorithm implements IDeleteAlgorithm {

    @Override
    public void delete(RestorePoint rp) throws Exception {
        File file = new File(rp.getResultPath() + File.separator + "Backup.zip");
        boolean rez = file.delete();
        if (!rez) {
            throw new Exception("Smth went wrong");
        }
        file = new File(rp.getResultPath());
        rez = file.delete();
        if (!rez) {
            throw new Exception("Smth went wrong (2)");
        }
    }
}
