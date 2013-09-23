package com.compomics.peppi.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum ViewPropertyEnum implements PropertyEnum {

    PROTEINACCESSIONTYPE("view.preferredaccessiontype", "uniprot"),
    PREFERREDENZYME("protein.preferredenzyme", "trypsin");
    private final String property;
    private final String defaultValue;

    private ViewPropertyEnum(String property, String defaultValue) {
        this.property = property;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return property;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
    //TODO: make interface method reference implementing class?
    public static EnumSet allEnumValues = EnumSet.allOf(ViewPropertyEnum.class);
}
