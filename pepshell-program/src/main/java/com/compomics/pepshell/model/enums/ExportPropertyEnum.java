package com.compomics.pepshell.model.enums;

/**
 *
 * @author Davy Maddelein
 */
public enum ExportPropertyEnum implements PropertyEnum {

    EXPORTFILE("export.fileformat", "CSV"),
    LASTACCESSIONMASKEXPORTFOLDER("pepshell.accessionmaskfolder", System.getProperty("user.home"));

    private String key;
    private String defaultValue;

    private ExportPropertyEnum(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
}
