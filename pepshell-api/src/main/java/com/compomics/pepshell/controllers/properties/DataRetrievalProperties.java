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

import com.compomics.pepshell.model.enums.DataRetrievalPropertyEnum;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public class DataRetrievalProperties extends AbstractProperties {

    private static DataRetrievalProperties dataRetrievalProperties;
    private static final File dataRetrievalPropertiesFile = new File(System.getProperty("user.home") + "/.compomics/pepshell/dataRetrievalPreferences");

    private DataRetrievalProperties() {
        super(EnumSet.allOf(DataRetrievalPropertyEnum.class));
    }

    private DataRetrievalProperties(File aPropertyFile) throws IOException {
        super(aPropertyFile, EnumSet.allOf(DataRetrievalPropertyEnum.class));
    }

            public static DataRetrievalProperties getInstance() {
        if (dataRetrievalProperties == null) {
            try {
                dataRetrievalProperties = new DataRetrievalProperties(dataRetrievalPropertiesFile);
            } catch (IOException ex) {
                dataRetrievalProperties = new DataRetrievalProperties();
            }
        }
        return dataRetrievalProperties;
    }
    
}
