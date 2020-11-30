import Backup.*;
import Create.SeparatedCreateAlgorithm;
import Create.SharedCreateAlgorithm;
import Delete.ClearingAlgoCount;
import Delete.ClearingAlgoSize;
import Delete.ClearingRP;
import Delete.TypeOfClearing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

public class Manager {
   @Test
    public void test1() throws Exception {
        Backup backup = new Backup();
        backup.addFile("src\\main\\resources\\t2.txt");
        backup.addFile("src\\main\\resources\\test.txt");
        backup.createRestorePoint(new SharedCreateAlgorithm(), TypeOfRestorePoint.FULL);
        ZipFile zf = new ZipFile(backup.getResultPath() + File.separator + backup.getId() + File.separator + "0" + File.separator + "Backup.zip");
        assertEquals(zf.size(),2);
        zf.close();
        backup.createRestorePoint(new SeparatedCreateAlgorithm(), TypeOfRestorePoint.FULL);
        backup.clearRestorePoint(TypeOfClearing.ONE, new ClearingAlgoCount(1));
        assertEquals(1, backup.getBackupLength());
    }

    @Test
    public void test2() throws Exception {
        Backup backup = new Backup();
        backup.addFile("src\\main\\resources\\t2.txt");
        backup.addFile("src\\main\\resources\\test.txt");
        backup.createRestorePoint(new SeparatedCreateAlgorithm(), TypeOfRestorePoint.FULL);
        backup.createRestorePoint(new SeparatedCreateAlgorithm(), TypeOfRestorePoint.FULL);
        assertEquals(backup.getBackupSize(), 400);
        backup.clearRestorePoint((TypeOfClearing.ONE), new ClearingAlgoSize(250));
        Path path = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString());
        assertEquals(1, (int) Files.list(path).count());
    }

    @Test
    public void test3() throws Exception {
        Backup backup = new Backup();
        backup.addFile("src\\main\\resources\\t2.txt");
        backup.createRestorePoint(new SeparatedCreateAlgorithm(), TypeOfRestorePoint.INCREMENTAL);
        backup.addFile("src\\main\\resources\\test.txt");
        backup.createRestorePoint(new SeparatedCreateAlgorithm(), TypeOfRestorePoint.INCREMENTAL);
        Path path1 = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString() + File.separator + "0");
        Path path2 = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString() + File.separator + "1");
        assertEquals(1, (int) Files.list(path1).count());
        assertEquals(1, (int) Files.list(path2).count());
   }

    @Test
    public void test4() throws Exception {
       Backup backup = new Backup();
       backup.addFile("src\\main\\resources\\t2.txt");
       backup.createRestorePoint(new SeparatedCreateAlgorithm(),TypeOfRestorePoint.FULL);
       backup.addFile("src\\main\\resources\\t3.txt");
       backup.createRestorePoint(new SharedCreateAlgorithm(), TypeOfRestorePoint.FULL);
       backup.clearRestorePoint(TypeOfClearing.ONE,new ClearingAlgoSize(800), new ClearingAlgoCount(2));
       Path path = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString());
       assertEquals((int) Files.list(path).count(),2);
       backup.addFile("src\\main\\resources\\test.txt");
       backup.createRestorePoint(new SharedCreateAlgorithm(),TypeOfRestorePoint.FULL);
       backup.clearRestorePoint(TypeOfClearing.ONE, new ClearingAlgoSize(800), new ClearingAlgoCount(2));
       assertEquals(1, (int) Files.list(path).count());
    }
}
