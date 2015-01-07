package com.compomics.pepshell.controllers.properties;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.enums.ViewPropertyEnum;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public class ViewProperties extends AbstractProperties {

    private static ViewProperties aViewProperties;
    private static final File viewPropertiesFile = new File(System.getProperty("user.home")+"/.compomics/pepshell/viewPreferences");
    
    private ViewProperties() {
        super(EnumSet.allOf(ViewPropertyEnum.class));
    }
    
    

    private ViewProperties(File propertiesFile) throws IOException {
        super(propertiesFile, EnumSet.allOf(ViewPropertyEnum.class));
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
