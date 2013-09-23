package com.compomics.peppi.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum PDBPropertyEnum implements PropertyEnum {

    FILELOCATION("pdb.filelocation",System.getProperty("user.home"));
    private final String keyName;
    private final String defaultValue;

    private PDBPropertyEnum(String keyName,String aDefaultValue) {
        this.keyName = keyName;
        this.defaultValue = aDefaultValue;
    }

    public String getKey() {
        return this.keyName;
    }
    
    public String getDefaultValue(){
        return defaultValue;
    }
    
    public static final EnumSet<PDBPropertyEnum> allEnumValues = EnumSet.allOf(PDBPropertyEnum.class);

}