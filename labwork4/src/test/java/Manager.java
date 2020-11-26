import Backup.Backup;
import Delete.ClearingRP;
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
        backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\t2.txt");
        backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\test.txt");
        backup.createRestorePoint("shared", "full");
        ZipFile zf = new ZipFile(backup.getResultPath() + File.separator + backup.getId() + File.separator + "0" + File.separator + "Backup.zip");
        assertEquals(zf.size(),2);
        zf.close();
        backup.createRestorePoint("separated", "full");
        ClearingRP clearingRP = new ClearingRP();
        clearingRP.setCount(1);
        backup.setClearingRestorePoint(clearingRP);
        backup.clearRestorePoint();
        assertEquals(backup.getBackupLength(),1);
    }

    @Test
    public void test2() throws Exception {
        Backup backup = new Backup();
        backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\t2.txt");
        backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\test.txt");
        backup.createRestorePoint("separated", "full");
        backup.createRestorePoint("separated", "full");
        assertEquals(backup.getBackupSize(), 400);
        ClearingRP clearingRP = new ClearingRP();
        clearingRP.setSize(250);
        backup.setClearingRestorePoint(clearingRP);
        backup.clearRestorePoint();
        Path path = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString());
        assertEquals((int) Files.list(path).count(),1);
    }

    @Test
    public void test3() throws Exception {
        Backup backup = new Backup();
        backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\t2.txt");
        backup.createRestorePoint("separated", "incremental");
        backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\test.txt");
        backup.createRestorePoint("separated","incremental");
        Path path1 = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString() + File.separator + "0");
        Path path2 = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString() + File.separator + "1");
        assertEquals((int) Files.list(path1).count(),1);
        assertEquals((int) Files.list(path2).count(),1);
   }

    @Test
    public void test4() throws Exception {
       Backup backup = new Backup();
       backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\t2.txt");
       backup.createRestorePoint("separated", "full");
       backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\t3.txt");
       backup.createRestorePoint("shared", "full");
       ClearingRP clearingRP = new ClearingRP();
       clearingRP.setSize(800);
       clearingRP.setCount(2);
       backup.setClearingRestorePoint(clearingRP);
       backup.clearRestorePoint();
       Path path = Paths.get("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents" + File.separator + backup.getId().toString());
       assertEquals((int) Files.list(path).count(),2);
       backup.addFile("C:\\Users\\alexe\\IdeaProjects\\OOP-programming\\labwork4\\src\\main\\resources\\test.txt");
       backup.createRestorePoint("shared", "full");
       backup.clearRestorePoint();
       assertEquals((int) Files.list(path).count(),1);
    }
}
