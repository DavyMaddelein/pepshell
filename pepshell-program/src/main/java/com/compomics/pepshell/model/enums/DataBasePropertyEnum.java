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
public enum DataBasePropertyEnum implements PropertyEnum {

    DBNAME("database.name", "mydbname"), DBURL("database.url", "mydbserver.com"), DBUSERNAME("database.username", "user"),
    LINKDBNAME("linkdb.name", "mydbname"),LINKDBURL("linkdb.url", "mydbserver.com"),LINKDBUSERNAME("linkdb.username", "user"),
    ASKLINKDB("linkdb.askagain","yes")
    ;
    private final String keyName;
    private final String defaultValue;

    private DataBasePropertyEnum(String keyName, String defaultValue) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return keyName;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public static final EnumSet<DataBasePropertyEnum> allEnumValues = EnumSet.allOf(DataBasePropertyEnum.class);
}
