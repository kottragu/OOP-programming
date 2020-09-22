
import java.io.File;



public class Main {

    public static void main(String[] argv) throws Exception {
        IniParser iniParser = new IniParser();
        var rez = iniParser.Parse(new File("./src/main/resources/test.ini"));
        String[] strings = rez.getParametersNames("internetInfo");
        for (String s: strings){
            System.out.println(s);
        }

    }
}
