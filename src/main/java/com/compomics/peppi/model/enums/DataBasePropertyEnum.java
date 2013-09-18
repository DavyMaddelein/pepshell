package com.compomics.peppi.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum DataBasePropertyEnum implements PropertyEnum {

    DBNAME("database.name"), DBURL("database.url"), DBUSERNAME("database.username");
    private final String dbPropertyTerms;

    private DataBasePropertyEnum(String keyName) {
        this.dbPropertyTerms = keyName;
    }

    public String getValue() {
        return dbPropertyTerms;
    }
    public static final EnumSet<DataBasePropertyEnum> allEnumValues = EnumSet.allOf(DataBasePropertyEnum.class);
}
