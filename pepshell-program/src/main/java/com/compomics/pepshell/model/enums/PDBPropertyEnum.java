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

import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
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