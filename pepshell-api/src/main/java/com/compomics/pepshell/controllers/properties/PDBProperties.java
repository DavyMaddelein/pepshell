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
                //FaultBarrier.getInstance().handleException(ex);
                aPDBPropertiesInstance = new PDBProperties();
            }
        }
        return aPDBPropertiesInstance;
    }
}
