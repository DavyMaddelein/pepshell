package com.compomics.pepshell.model.enums;

/**
 * Created by Davy Maddelein on 19/11/2014.
 */
public enum StartupPropertyEnum implements PropertyEnum{

    MAXMEMORY("memory.max","128M"),
    PLUGINFOLDERS("plugin.folder.location","./resources/plugins");


    private final String keyName;
    private final String defaultValue;

    private StartupPropertyEnum(String keyName, String defaultValue) {
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
