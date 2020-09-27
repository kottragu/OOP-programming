
import org.junit.jupiter.api.Test;

import java.io.File;



public class Main {

    @Test
    public void testProgram1() throws Exception {
        System.out.print("\n\n---TEST1---\n\n");
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test1.ini"));
        System.out.println(rez.tryGetString("internetInfo", "Crimea"));
        System.out.println(rez.tryGetInt("internetInfo", "internet"));
        System.out.println(rez.tryGetDouble("common","try"));
        System.out.println(rez.tryGetInt("common", "try"));
    }

    @Test
    public void testProgram2() throws Exception {
        System.out.print("\n\n---TEST2---\n\n");
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test2.ini"));
    }

    @Test
    public void testProgram3() throws Exception {
        System.out.print("\n\n---TEST3---\n\n");
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test3.ini"));
        String[] s = rez.getSectionsNames();
        for (String string: s){
            System.out.println(string);
        }
    }

    @Test
    public void testProgram4() throws Exception {
        System.out.print("\n\n---TEST4--\n\n");
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test.txt"));
    }
}
