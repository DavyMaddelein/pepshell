package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.StartupPropertyEnum;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by Davy Maddelein on 19/11/2014.
 */
public class StartupProperties extends AbstractProperties {

    private static StartupProperties startupProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/startupproperties");

    private StartupProperties(File aPropertyFile, EnumSet aPropertyEnum) throws IOException {
        super(aPropertyFile, aPropertyEnum);
    }

    private StartupProperties(EnumSet programPropertyEnumSet) {
        super(programPropertyEnumSet);
    }

    public static StartupProperties getInstance() {
        if (startupProperties == null) {
            try {
                startupProperties = new StartupProperties(propertyFile, EnumSet.allOf(StartupPropertyEnum.class));
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
                startupProperties = new StartupProperties(EnumSet.allOf(StartupPropertyEnum.class));
            }
        }
        return startupProperties;
    }
}
