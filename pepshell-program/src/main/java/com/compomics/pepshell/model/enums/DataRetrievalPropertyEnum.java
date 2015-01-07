package com.compomics.pepshell.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public enum DataRetrievalPropertyEnum implements PropertyEnum {

    UNIPROTTOGI("protein.uniprottoncbi", ""),
    GITOUNIPROT("protein.ncbitouniprot", "http://www.uniprot.org/mapping/?from=P_GI&to=ACC&format=tab&query="),
    GETPDBFORUNIPROT("protein.pdbforuniprot", "http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=");

    private final String defaultValue;
    private final String key;

    private DataRetrievalPropertyEnum(String key, String value) {
        this.key = key;
        this.defaultValue = value;
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
