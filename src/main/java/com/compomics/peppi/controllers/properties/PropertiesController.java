package com.compomics.peppi.controllers.properties;

import com.compomics.peppi.model.Property;
import com.compomics.peppi.model.enums.PropertyEnum;
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
 * @author Davy
 */
abstract class PropertiesController implements Observer {

    protected Properties properties = new Properties();
    protected EnumSet propertyEnum;
    protected File propertyFile;

    protected PropertiesController(File aPropertyFile, final EnumSet aPropertyEnum) throws IOException {
        FileReader propertiesFileReader = null;
        try {
            if (aPropertyFile.exists()) {
                propertiesFileReader = new FileReader(aPropertyFile);
                properties.load(propertiesFileReader);
                this.propertyEnum = aPropertyEnum;
                propertyFile = aPropertyFile;
            } else {
                if (!aPropertyFile.getParentFile().exists()) {
                    aPropertyFile.getParentFile().mkdirs();
                }
                if (!aPropertyFile.createNewFile()) {
                    setPropertiesFromEnumSet(aPropertyEnum);
                    throw new IOException("property file could not be created");
                } else {
                    this.propertyEnum = aPropertyEnum;
                    propertyFile = aPropertyFile;
                    setPropertiesFromEnumSet(aPropertyEnum);
                }
            }
        } finally {
            if (propertiesFileReader != null) {
                propertiesFileReader.close();
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (propertyFile.exists()) {
                        try {
                            properties.store(new FileWriter(propertyFile), null);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            });

        }
    }

    protected PropertiesController(EnumSet aProperyEnum) {
        this.propertyEnum = aProperyEnum;
        setPropertiesFromEnumSet(aProperyEnum);
    }

    private void setPropertiesFromEnumSet(EnumSet aPropertyEnum) {
        Iterator<PropertyEnum> propIter = aPropertyEnum.iterator();
        PropertyEnum aProperty;
        while (propIter.hasNext()) {
            aProperty = propIter.next();
            properties.put(aProperty.getKey(), aProperty.getDefaultValue());
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
        try {
            if (propertyKey != null && properties.containsKey(propertyKey)) {
                property = properties.getProperty(propertyKey);
            }
        } finally {
            return property;
        }
    }

    public boolean setProperty(Property aProperty) throws IllegalArgumentException {
        boolean stored = false;
        if (propertyEnum.contains(aProperty.getName())) {
            properties.setProperty(aProperty.getName().getKey(), aProperty.getValue());
        }
        stored = true;

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
