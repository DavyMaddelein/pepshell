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

package com.compomics.pepshellstandalone;

import com.compomics.pepshell.model.enums.PropertyEnum;

import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public enum ViewPropertyEnum implements PropertyEnum {

    PROTEINACCESSIONTYPE("view.preferredaccessiontype", "uniprot"),
    PREFERREDENZYME("protein.preferredenzyme", "trypsin"),
    PROTEINFASTALOCATION("protein.fastalocation", ""),
    PROTEININFOWEBSITE("protein.externaldatalocation", "www.uniprot.org/uniprot/"),
    LOADMASKINGFILEAUTOMATICALLY("protein.loadproteinmaskonstartup", "no"),
    PROTEINMASKLOCATION("protein.proteinmasklocation", System.getProperty("user.home") + ".compomics/pepshell/proteinmask.txt"),
    LASTUSEDLOADINGDIRECTORY("view.lastloadingdirectory",System.getProperty("user.home"));
    private final String property;
    private final String defaultValue;

    ViewPropertyEnum(String property, String defaultValue) {
        this.property = property;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return property;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
    //TODO: make interface method reference implementing class?
    public static EnumSet allEnumValues = EnumSet.allOf(ViewPropertyEnum.class);
}
