/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
