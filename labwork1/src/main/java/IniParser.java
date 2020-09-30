import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import modules.*;

public class IniParser {
    private Scanner sc;
    private File workFile;
    private DataIniParser dataIniParser;

    public IniParser() {
        dataIniParser = new DataIniParser();
    }

    public DataIniParser Parse(File fileAddress) throws Exception {
        if (!fileAddress.getName().endsWith(".ini")){
            throw new Exception("Doesn't *.ini file");
        }
        workFile = fileAddress;

        sc = new Scanner(workFile);
        String line;
        boolean isSection = false;
        Section section = null;
        while (sc.hasNextLine()) {
            line = sc.nextLine();

            if(line.contains(";")) {
                line = line.substring(0,line.indexOf(";"));
            }
            line = line.trim();

            if (line.contains("[") & line.contains("]")){
                section = new Section();
                section.setSectionName(line.substring(line.indexOf("[")+1,line.indexOf("]")));
                isSection = true;
                continue;
            }
            if (line.equals("")) {
                if (isSection){
                    dataIniParser.add(section); //если первые строки пустые, то не будет добавлять
                }
                isSection = false;
                continue;
            }

            if (isSection) {
                if (line.contains("=")) {
                    String[] values = line.split("=");
                    for(int i=0; i < values.length; i++){
                        values[i] = values[i].trim();
                    }
                    if(values.length < 2) {
                        values = new String[] {values[0], " "};
                    }
                    section.setDataSection(values[0],values[1]);
                    continue;
                } else
                    throw new Exception("Incorrect parameter in line: " + line);
            }
            if (!sc.hasNextLine() && isSection) {
                dataIniParser.add(section);
            }
        }

        if (section == null){
            throw new Exception("Empty file");
        }
        return dataIniParser;
    }

}
