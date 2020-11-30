package Create;

import Backup.TypeOfAlgo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class SeparatedCreateAlgorithm implements ICreateAlgorithm {

    public SeparatedCreateAlgorithm() {}

    @Override
    public void create(ArrayList<String> filePaths, String backupPath) throws IOException { //backupPath must be with restore point ID
        File directory = new File(backupPath);
        directory.mkdirs();
        for (String path: filePaths) {
            File file = new File( backupPath + File.separator + path.substring(path.lastIndexOf(File.separator)));
            file.createNewFile();

        }
        String resultPath;
        for (String path: filePaths) {
            resultPath = backupPath + File.separator + path.substring(path.lastIndexOf(File.separator));
            Files.copy(Paths.get(path), Paths.get(resultPath), StandardCopyOption.REPLACE_EXISTING);
        }
    }


    @Override
    public long getSize(String backupPath) {
        long resultSize = 0;
        File directory = new File(backupPath);
        for (File file: Objects.requireNonNull(directory.listFiles())) {
            resultSize += file.length();
        }
        return resultSize;
    }

    @Override
    public TypeOfAlgo getType() {
        return TypeOfAlgo.SEPARATED;
    }

}
