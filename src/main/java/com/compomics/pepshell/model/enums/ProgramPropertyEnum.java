package com.compomics.pepshell.model.enums;

/**
 *
 * @author Davy
 */
public enum ProgramPropertyEnum implements PropertyEnum {

    LASTACCESSIONMASKEXPORTFOLDER("pepshell.accessionmaskfolder", System.getProperty("user.home"));

    private final String keyName;
    private final String defaultValue;

    private ProgramPropertyEnum(String keyName, String defaultValue) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return this.keyName;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

}
