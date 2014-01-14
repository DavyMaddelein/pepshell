package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy
 */
public final class DatabaseProperties extends PropertiesController {

    private static DatabaseProperties databaseProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/dbpreferences");

    private DatabaseProperties(File aPropertyFile, EnumSet DataBasePropertyEnumSet) throws FileNotFoundException, IOException {
        super(aPropertyFile, DataBasePropertyEnumSet);
    }
    
    private DatabaseProperties(EnumSet DataBasePropertyEnumSet){
        super(DataBasePropertyEnumSet);
    }

    public static DatabaseProperties getInstance() {
        if (databaseProperties == null) {
            try {
                databaseProperties = new DatabaseProperties(propertyFile, DataBasePropertyEnum.allEnumValues);
            } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
            databaseProperties = new DatabaseProperties(DataBasePropertyEnum.allEnumValues);
            }
        }
        return databaseProperties;
    }
}