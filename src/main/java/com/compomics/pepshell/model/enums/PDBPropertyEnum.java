package com.compomics.pepshell.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum PDBPropertyEnum implements PropertyEnum {

    FILELOCATION("pdb.filelocation", System.getProperty("user.home")),
    SAVELOCATION("pdb.savelocation", System.getProperty("java.io.tmpdir"));
    private final String keyName;
    private final String defaultValue;

    private PDBPropertyEnum(String keyName, String aDefaultValue) {
        this.keyName = keyName;
        this.defaultValue = aDefaultValue;
    }

    @Override
    public String getKey() {
        return this.keyName;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
    public static final EnumSet<PDBPropertyEnum> allEnumValues = EnumSet.allOf(PDBPropertyEnum.class);
}