package com.compomics.pepshell.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public enum DataRetrievalPropertyEnum implements PropertyEnum {

    UNIPROTTOGI("protein.uniprottoncbi", ""),
    GITOUNIPROT("protein.ncbitouniprot", "http://www.uniprot.org/mapping/?from=P_GI&to=ACC&format=tab&query="),
    GETPDBFORUNIPROT("protein.pdbforuniprot", "http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=");

    private String defaultValue;
    private String key;

    private DataRetrievalPropertyEnum(String key, String value) {
        this.key = key;
        this.defaultValue = value;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public static EnumSet allEnumValues = EnumSet.allOf(ViewPropertyEnum.class);
}
