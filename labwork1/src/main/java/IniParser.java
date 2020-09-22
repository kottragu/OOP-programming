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


    public String valuesWithoutSpaces(String valueWithSpaces) {
        while (valueWithSpaces.startsWith(" ") || valueWithSpaces.endsWith(" ")) {
            if (valueWithSpaces.startsWith(" ")) {
                valueWithSpaces = valueWithSpaces.substring(1);
            }
            if (valueWithSpaces.endsWith(" ")) {
                valueWithSpaces = valueWithSpaces.substring(0,valueWithSpaces.length()-1);
            }
        }
        return valueWithSpaces;
    }

    public String[] valuesWithoutSpaces(String[] valueWithSpaces) {
        List<String> arrayList = new ArrayList<>();
        for (String s: valueWithSpaces) {
            while (s.startsWith(" ") || s.endsWith(" ")) {
                if (s.startsWith(" ")) {
                    s = s.substring(1);
                }
                if (s.endsWith(" ")) {
                    s = s.substring(0, s.length() - 1);
                }
            }
            arrayList.add(s);
        }
        return arrayList.toArray(new String[valueWithSpaces.length-1]);
    }




    public DataIniParser Parse(File fileAddress) throws Exception {
        if (!fileAddress.getName().endsWith(".ini")){
            throw new Exception("Doesn't *.ini file");
        }
        workFile = fileAddress;

        sc = new Scanner(workFile);
        String line;
        boolean flag = false;
        Section section = null;
        while (sc.hasNext()) {
            line = sc.nextLine();

            if(line.contains(";")) {
                line = line.substring(0,line.indexOf(";"));
            }

            line = valuesWithoutSpaces(line);

            if (line.contains("[") & line.contains("]")){
                section = new Section();
                section.setSectionName(line.substring(line.indexOf("[")+1,line.indexOf("]")));
                flag = true;
                continue;
            }
            if (line.equals("")) {
                if (flag){
                    dataIniParser.add(section); //если первые строки пустые, то не буде добавлять
                }
                flag = false;
                continue;
            }

            if (flag) {
                if (line.contains("=")) {
                    String[] values = line.split("=");
                    values = valuesWithoutSpaces(values);
                    if(values.length < 2) {
                        values = new String[] {values[0], " "};
                    }

                    section.setDataSection(values[0],values[1]);
                } else
                    throw new Exception("Incorrect parameter in line: " + line);
            }
            if (!sc.hasNext()) {
                dataIniParser.add(section);
            }
        }


        /*
        while (sc.hasNext()){
            line = sc.nextLine();

            if (line.contains("[") & line.contains("]")) {
                String sectionName = line.substring(line.indexOf("[")+1,line.indexOf("]"));
                Section section = new Section();
                section.setSectionValue(sectionName);


                if (sc.hasNext()) {
                    line = sc.nextLine();

                    while (line.contains("=")) {
                        if(line.contains(";")) {
                            line = line.substring(0,line.indexOf(";")-1);
                        }
                        String[] values = line.split("=");
                        values = valuesWithoutSpaces(values);
                        if(values.length < 2){
                            values = new String[] {values[0], " "};
                        }

                        section.setDataSection(values[0],values[1]);

                        if(sc.hasNext()) {
                            line = sc.nextLine();
                        } else {
                            dataIniParser.add(section);
                            break;
                        }
                    }
                }
                dataIniParser.add(section);
            }
        }
         */
        if (section == null){
            throw new Exception("Empty file");
        }
        dataIniParser.add(section);
        return dataIniParser;
    }

}
