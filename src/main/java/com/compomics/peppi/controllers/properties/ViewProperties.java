package com.compomics.peppi.controllers.properties;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public class ViewProperties extends PropertiesController {

    public ViewProperties(EnumSet propertiesEnum) {
        super(propertiesEnum);
    }

    public ViewProperties(File propertiesFile, EnumSet propertiesEnum) throws IOException {
        super(propertiesFile, propertiesEnum);
    }
}
