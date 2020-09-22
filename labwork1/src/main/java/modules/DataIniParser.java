package modules;

import java.util.ArrayList;
import java.util.List;

public class DataIniParser<T> {
    private ArrayList<Section> arrayList;

    public DataIniParser(){
        arrayList = new ArrayList<Section>();
    }

    public void add(Section section){
        arrayList.add(section);
    }

    public Section get(int index){
        return arrayList.get(index);
    }

    public String[] getSectionsNames(){
        List<String> parametersNames = new ArrayList<String>();
        for (Section s: arrayList){
            parametersNames.add(s.getSectionName());
        }
        return parametersNames.toArray(new String[parametersNames.size()-1]);
    }

    public String[] getParametersNames(String section) throws Exception {

        for(Section s: arrayList){
            if (s.getSectionName().equals(section)){
                return s.getParametersNames();
            }
        }

        throw new Exception("Incorrect section names");
    }

    public String tryGetString(String section, String parameter) throws Exception {

        for (Section s: arrayList) {
            if (s.getSectionName().equals(section)){
                if (s.getExistSectionValue(parameter))
                    return s.getSectionValue(parameter);
                else
                    throw new Exception("Parameter " + parameter + " doesn't exist in " + section);
            }
        }
        throw new Exception("Section's name doesn't exist");
    }

    public int size() {
        return arrayList.size();
    }

    public int tryGetInt(String section, String parameter) throws Exception {
        for (Section s: arrayList) {
            if (s.getSectionName().equals(section)) {
                if (s.getExistSectionValue(parameter))
                    try {
                        return Integer.parseInt(s.getSectionValue(parameter));
                    } catch (Exception e) {
                        throw new Exception("Value (" + s.getSectionValue(parameter) + ") can't cast to integer");
                    }
                else
                    throw new Exception("Parameter " + parameter + " doesn't exist in " + section);
            }
        }
        throw new Exception("Selected section (" + section + ") doesn't exist");
    }

    public Double tryGetDouble(String section, String parameter) throws Exception {
        for (Section s: arrayList) {
            if (s.getSectionName().equals(section)) {
                if (s.getExistSectionValue(parameter))
                    try {
                        return Double.parseDouble(s.getSectionValue(parameter));
                    } catch (Exception e) {
                        throw new Exception("Value (" + s.getSectionValue(parameter) + ") can't cast to integer");
                    }
                else
                    throw new Exception("Parameter " + parameter + " doesn't exist in " + section);
            }
        }
        throw new Exception("Selected section (" + section + ") doesn't exist");
    }

}
