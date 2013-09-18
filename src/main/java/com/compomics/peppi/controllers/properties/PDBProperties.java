package com.compomics.peppi.controllers.properties;

import com.compomics.peppi.FaultBarrier;
import com.compomics.peppi.model.enums.PDBPropertyEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class PDBProperties extends PropertiesController {

    private static final File PDBPropertiesFile = new File(System.getProperty("user.home")+"/.compomics/peppi/PDBpreferences");
    private static PDBProperties aPDBPropertiesInstance;

    private PDBProperties(File aPropertyFile) throws FileNotFoundException, IOException {
        super(aPropertyFile, PDBPropertyEnum.allEnumValues);
    }

    private PDBProperties() {
        super(PDBPropertyEnum.allEnumValues);
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
