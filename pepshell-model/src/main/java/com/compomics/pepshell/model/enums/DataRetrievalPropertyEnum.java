/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.model.enums;

/**
 *
 * @author Davy Maddelein
 */
public enum DataRetrievalPropertyEnum implements PropertyEnum {

    UNIPROTTOGI("protein.uniprottoncbi", ""),
    GITOUNIPROT("protein.ncbitouniprot", "http://www.uniprot.org/mapping/?from=P_GI&to=ACC&format=tab&query="),
    GETPDBFORUNIPROT("protein.pdbforuniprot", "http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot="),
    PREFERREDENZYME("protein.enzyme","trypsin"),
    PROTEININFOWEBSITE("protein.externaldatalocation", "www.uniprot.org/uniprot/");


    private final String defaultValue;
    private final String key;

    DataRetrievalPropertyEnum(String key, String value) {
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
