package com.compomics.peppi.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum DataBasePropertyEnum implements PropertyEnum {

    DBNAME("database.name","mydbname"), DBURL("database.url","mydbserver.com"), DBUSERNAME("database.username","user");
    private final String keyName;
    private final String defaultValue;

    private DataBasePropertyEnum(String keyName,String defaultValue) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return keyName;
    }
    
    
    public String getDefaultValue(){
        return defaultValue;
    }
    
    public static final EnumSet<DataBasePropertyEnum> allEnumValues = EnumSet.allOf(DataBasePropertyEnum.class);
}
