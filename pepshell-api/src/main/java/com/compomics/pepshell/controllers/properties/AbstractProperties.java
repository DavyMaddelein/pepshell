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
import com.compomics.pepshell.model.Property;
import com.compomics.pepshell.model.enums.PropertyEnum;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 *
 * @author Davy Maddelein
 */
abstract class AbstractProperties implements Observer {

    private Properties properties = new Properties();
    private EnumSet propertyEnum;
    
    AbstractProperties(final File aPropertyFile, final EnumSet aPropertyEnum) throws IOException {
        FileReader propertiesFileReader = null;
        try {
            if (aPropertyFile.exists()) {
                propertiesFileReader = new FileReader(aPropertyFile);
                properties.load(propertiesFileReader);
                this.propertyEnum = aPropertyEnum;
               } else {
                if (!aPropertyFile.getParentFile().exists()) {
                    aPropertyFile.getParentFile().mkdirs();
                }
                if (!aPropertyFile.createNewFile()) {
                    setPropertiesFromEnumSet(aPropertyEnum);
                    throw new IOException("property file could not be created");
                } else {
                    this.propertyEnum = aPropertyEnum;
                }
            }
        } finally {
            if (propertiesFileReader != null) {
                propertiesFileReader.close();
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (aPropertyFile != null && aPropertyFile.exists()) {
                        try {
                            properties.store(new FileWriter(aPropertyFile), null);
                        } catch (IOException ex) {
                            FaultBarrier.getInstance().handleException(ex);
                        }
                    }

                }
            });
            setPropertiesFromEnumSet(aPropertyEnum);
        }
    }

    AbstractProperties(EnumSet aProperyEnum) {
        this.propertyEnum = aProperyEnum;
        setPropertiesFromEnumSet(aProperyEnum);
    }

    private void setPropertiesFromEnumSet(EnumSet aPropertyEnum) {
        Iterator<PropertyEnum> propIter = aPropertyEnum.iterator();
        PropertyEnum aProperty;
        while (propIter.hasNext()) {
            aProperty = propIter.next();
            if (!properties.containsKey(aProperty.getKey())) {
                properties.put(aProperty.getKey(), aProperty.getDefaultValue());
            }
        }
    }

    public void update(Observable o, Object property) throws IllegalArgumentException {
        if (property != null && property instanceof Property) {
            properties.setProperty(((Property) property).getName().getKey(), ((Property) property).getValue());
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String propertyKey) throws IllegalArgumentException {
        String property = "";
            if (propertyKey != null && properties.containsKey(propertyKey)) {
                property = properties.getProperty(propertyKey);
            }
           return property;
        }

    public boolean setProperty(Property aProperty) throws IllegalArgumentException {
        boolean stored = false;
        if (propertyEnum.contains(aProperty.getName())) {
            properties.setProperty(aProperty.getName().getKey(), aProperty.getValue());
            stored = true;
        }
        return stored;
    }

    public boolean setProperties(List<Property> propertiesList) throws IllegalArgumentException {
        for (Property aPropertyEntry : propertiesList) {
            if (propertyEnum.contains(aPropertyEntry.getName())) {
                properties.setProperty(aPropertyEntry.getName().getKey(), aPropertyEntry.getValue());
            }
        }
        return true;
    }
}
