
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Main {

    @Test
    public void testProgram1() throws Exception {
        System.out.print("\n\n---TEST1---\n\n");
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test1.ini"));
        assertEquals(1990, rez.tryGetInt("internetInfo", "internet"));
        assertEquals(1.11, rez.tryGetDouble("common","try"), 1);
        Throwable exception = assertThrows(modules.CustomException.class, () -> rez.tryGetInt("common", "try"));
        assertEquals("Value (1.111111111111111111) can't cast to Integer", exception.getMessage());
    }

    @Test
    public void testProgram2() {
        System.out.print("\n\n---TEST2---\n\n");
        IniParser iniParser = new IniParser();
        Throwable exception = assertThrows(Exception.class, () -> iniParser.Parse(new File("./src/main/resources/test2.ini")));
        assertEquals("Incorrect parameter in line: Dolg? dolg.", exception.getMessage());
    }

    @Test
    public void testProgram3() throws Exception {
        System.out.print("\n\n---TEST3---\n\n");
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test3.ini"));
        String[] s = rez.getSectionsNames();
        assertTrue(Arrays.equals(s, new String[]{"common", "INFO", "internetInfo"}));
    }

    @Test
    public void testProgram4() {
        System.out.print("\n\n---TEST4--\n\n");
        IniParser iniParser = new IniParser();
        Throwable exception = assertThrows(Exception.class, () -> iniParser.Parse(new File("./src/main/resources/test.txt")));
        assertEquals("Doesn't *.ini file", exception.getMessage());
    }
}
