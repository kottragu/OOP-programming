package Create;

import Backup.TypeOfAlgo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SharedCreateAlgorithm implements ICreateAlgorithm {

    @Override
    public void create(ArrayList<String> filePaths, String backupPath) throws Exception {
        File file = new File(backupPath + File.separator + "Backup.zip");
        file.createNewFile();
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupPath + File.separator + "Backup.zip"));
        for (String s: filePaths) {
            FileInputStream fis = new FileInputStream(new File(s));
            ZipEntry ze = new ZipEntry(s.substring(s.lastIndexOf(File.separator)+1));
            zos.putNextEntry(ze);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zos.write(buffer);
            zos.closeEntry();
            fis.close();
        }
        zos.close();
    }

    @Override
    public long getSize(String backupPath) {
        File file = new File(backupPath + File.separator + "Backup.zip");
        return file.length();
    }

    @Override
    public TypeOfAlgo getType() {
        return TypeOfAlgo.SHARED;
    }
}
