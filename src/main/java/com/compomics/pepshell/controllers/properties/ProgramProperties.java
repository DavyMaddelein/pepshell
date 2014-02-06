package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public class ProgramProperties extends PropertiesController {

    private static ProgramProperties programProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/programpreferences");

    public ProgramProperties(File aPropertyFile, EnumSet aPropertyEnum) throws IOException {
        super(aPropertyFile, aPropertyEnum);
    }

    private ProgramProperties(EnumSet programPropertyEnumSet) {
        super(programPropertyEnumSet);
    }

    public static ProgramProperties getInstance() {
        if (programProperties == null) {
            try {
                programProperties = new ProgramProperties(propertyFile, DataBasePropertyEnum.allEnumValues);
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
                programProperties = new ProgramProperties(DataBasePropertyEnum.allEnumValues);
            }
        }
        return programProperties;
    }
}
