package com.compomics.peppi.model.enums;

/**
 *
 * @author Davy
 */
public enum ExportPropertyEnum {
    
    
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
    
    public String getDefaultvalue(){
        return defaultValue;
    }
}
