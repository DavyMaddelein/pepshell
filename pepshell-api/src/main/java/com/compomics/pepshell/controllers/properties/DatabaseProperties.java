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

package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public final class DatabaseProperties extends AbstractProperties {

    private static DatabaseProperties databaseProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/dbpreferences");

    private DatabaseProperties(File aPropertyFile, EnumSet DataBasePropertyEnumSet) throws FileNotFoundException, IOException {
        super(DatabaseProperties.propertyFile, DataBasePropertyEnumSet);
    }
    
    private DatabaseProperties(EnumSet DataBasePropertyEnumSet){
        super(DataBasePropertyEnumSet);
    }

    public static DatabaseProperties getInstance() {
        if (databaseProperties == null) {
            try {
                databaseProperties = new DatabaseProperties(propertyFile, DataBasePropertyEnum.allEnumValues);
            } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
            databaseProperties = new DatabaseProperties(DataBasePropertyEnum.allEnumValues);
            }
        }
        return databaseProperties;
    }
}