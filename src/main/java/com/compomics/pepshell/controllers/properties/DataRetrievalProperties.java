package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.DataRetrievalPropertyEnum;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public class DataRetrievalProperties extends AbstractProperties {

    private static DataRetrievalProperties dataRetrievalProperties;
    private static final File dataRetrievalPropertiesFile = new File(System.getProperty("user.home") + "/.compomics/pepshell/dataRetrievalPreferences");

    private DataRetrievalProperties() {
        super(EnumSet.allOf(DataRetrievalPropertyEnum.class));
    }

    private DataRetrievalProperties(File aPropertyFile) throws IOException {
        super(dataRetrievalPropertiesFile, EnumSet.allOf(DataRetrievalPropertyEnum.class));
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
