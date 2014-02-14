package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.DataRetrievalPropertyEnum;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class DataRetrievalProperties extends PropertiesController {

    private static DataRetrievalProperties dataRetrievalProperties;
    private static final File dataRetrievalPropertiesFile = new File(System.getProperty("user.home") + "/.compomics/pepshell/dataRetrievalPreferences");

    public DataRetrievalProperties() {
        super(DataRetrievalPropertyEnum.allEnumValues);
    }

    public DataRetrievalProperties(File aPropertyFile) throws IOException {
        super(dataRetrievalPropertiesFile, DataRetrievalPropertyEnum.allEnumValues);
    }

            public static DataRetrievalProperties getInstance() {
        if (dataRetrievalProperties == null) {
            try {
                dataRetrievalProperties = new DataRetrievalProperties(dataRetrievalPropertiesFile);
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
                dataRetrievalProperties = new DataRetrievalProperties();
            }
        }
        return dataRetrievalProperties;
    }
    
}
