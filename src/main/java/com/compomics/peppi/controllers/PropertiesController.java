package com.compomics.peppi.controllers;

import com.compomics.peppi.model.Property;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 *
 * @author Davy
 */
public final class PropertiesController implements Observer {

    static final File propertiesFile = new File(System.getProperty("user_home"), ".compomics/peppi/preferences");
    static Properties programProperties = new Properties();

    PropertiesController() throws FileNotFoundException, IOException {
        FileReader propertiesFileReader = null;
        try {
            propertiesFileReader = new FileReader(propertiesFile);
            if (propertiesFile.exists()) {
                programProperties.load(propertiesFileReader);
            }
        } finally {
            if (propertiesFileReader != null) {
                propertiesFileReader.close();
            }
        }
//TODO add properties store shutdownhook
    }

    public void update(Observable o, Object property) {
        if (property != null && property instanceof Property) {
            programProperties.setProperty(((Property)property).getName(), ((Property) property).getValue());
        }
    }

    public static Properties getProgramProperties() {
        return programProperties;
    }
}
