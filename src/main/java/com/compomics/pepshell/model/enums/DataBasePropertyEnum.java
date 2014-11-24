package com.compomics.pepshell.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public enum DataBasePropertyEnum implements PropertyEnum {

    DBNAME("database.name", "mydbname"), DBURL("database.url", "mydbserver.com"), DBUSERNAME("database.username", "user"),
    LINKDBNAME("linkdb.name", "mydbname"),LINKDBURL("linkdb.url", "mydbserver.com"),LINKDBUSERNAME("linkdb.username", "user"),
    ASKLINKDB("linkdb.askagain","yes")
    ;
    private final String keyName;
    private final String defaultValue;

    private DataBasePropertyEnum(String keyName, String defaultValue) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return keyName;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public static final EnumSet<DataBasePropertyEnum> allEnumValues = EnumSet.allOf(DataBasePropertyEnum.class);
}
