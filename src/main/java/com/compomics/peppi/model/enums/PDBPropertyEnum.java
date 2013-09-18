package com.compomics.peppi.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum PDBPropertyEnum implements PropertyEnum {

    FILELOCATION("pdb.filelocation");
    private final String keyName;

    private PDBPropertyEnum(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return this.keyName;
    }
    
    public static final EnumSet<PDBPropertyEnum> allEnumValues = EnumSet.allOf(PDBPropertyEnum.class);

}