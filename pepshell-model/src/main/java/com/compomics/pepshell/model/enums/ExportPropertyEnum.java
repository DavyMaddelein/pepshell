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
