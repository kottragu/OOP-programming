package modules;

import java.util.*;

public class Section{
    private String sectionValue;
    private Map<String,String> dataSection = new HashMap<String, String>();

    public void setSectionName(String name) {
        this.sectionValue = name;
    }

    public void setDataSection(String key, String value){
        dataSection.put(key, value );
    }

    public boolean getExistSectionValue(String parameter){
        return dataSection.containsKey(parameter);
    }

    public String[] getParametersNames() {
        List<String> list = new ArrayList<String> (dataSection.keySet());
        return list.toArray(new String[list.size()-1]);
    }

    public String getSectionName(){
        return sectionValue;
    }

    public String getSectionValue(String parameter){
        return dataSection.get(parameter);
    }
}