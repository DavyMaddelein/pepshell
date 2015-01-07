package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.PDBPropertyEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public class PDBProperties extends AbstractProperties {

    private static final File PDBPropertiesFile = new File(System.getProperty("user.home")+"/.compomics/pepshell/PDBpreferences");
    private static PDBProperties aPDBPropertiesInstance;

    private PDBProperties(File aPropertyFile) throws FileNotFoundException, IOException {
        super(aPropertyFile, EnumSet.allOf(PDBPropertyEnum.class));
    }

    private PDBProperties() {
        super(EnumSet.allOf(PDBPropertyEnum.class));
    }

    public static PDBProperties getInstance() {
        if (aPDBPropertiesInstance == null) {
            try {
                aPDBPropertiesInstance = new PDBProperties(PDBPropertiesFile);
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
                aPDBPropertiesInstance = new PDBProperties();
            }
        }
        return aPDBPropertiesInstance;
    }
}
