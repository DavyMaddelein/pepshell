package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.ViewPropertyEnum;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class ViewProperties extends PropertiesController {

    private static ViewProperties aViewProperties;
    private static final File viewPropertiesFile = new File(System.getProperty("user.home")+"/.compomics/pepshell/viewPreferences");
    
    public ViewProperties() {
        super(ViewPropertyEnum.allEnumValues);
    }
    
    

    public ViewProperties(File propertiesFile) throws IOException {
        super(propertiesFile, ViewPropertyEnum.allEnumValues);
    }
    
        public static ViewProperties getInstance() {
        if (aViewProperties == null) {
            try {
                aViewProperties = new ViewProperties(viewPropertiesFile);
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
                aViewProperties = new ViewProperties();
            }
        }
        return aViewProperties;
    }
}
