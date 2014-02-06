package com.compomics.pepshell.model.enums;

/**
 *
 * @author Davy
 */
public enum ExportPropertyEnum implements PropertyEnum{
    
    
    EXPORTFILE("export.fileformat","CSV");
    
    
    private String key;
    private String defaultValue;
    
    
    private ExportPropertyEnum(String key,String defaultValue){
        this.key = key;
        this.defaultValue = defaultValue;
    }
    
    public String getKey(){
        return key;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
}
