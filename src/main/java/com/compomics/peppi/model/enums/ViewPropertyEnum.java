package com.compomics.peppi.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum ViewPropertyEnum implements PropertyEnum {

    PROTEINACCESSIONTYPE("view.preferredaccessiontype", "uniprot");
    private final String property;
    private final String defaultValue;

    private ViewPropertyEnum(String property, String defaultValue) {
        this.property = property;
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return property;
    }

    public EnumSet allEnumValues() {
        return EnumSet.allOf(ViewPropertyEnum.class);
    }
}
