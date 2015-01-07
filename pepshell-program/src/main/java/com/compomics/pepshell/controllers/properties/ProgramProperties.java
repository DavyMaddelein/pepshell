package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.ProgramPropertyEnum;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public class ProgramProperties extends AbstractProperties {

    private static ProgramProperties programProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/programpreferences");

    private ProgramProperties(File aPropertyFile, EnumSet aPropertyEnum) throws IOException {
        super(aPropertyFile, aPropertyEnum);
    }

    private ProgramProperties(EnumSet programPropertyEnumSet) {
        super(programPropertyEnumSet);
    }

    public static ProgramProperties getInstance() {
        if (programProperties == null) {
            try {
                programProperties = new ProgramProperties(propertyFile, EnumSet.allOf(ProgramPropertyEnum.class));
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
                programProperties = new ProgramProperties(EnumSet.allOf(ProgramPropertyEnum.class));
            }
        }
        return programProperties;
    }
}
