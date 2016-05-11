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

import com.compomics.pepshell.model.enums.StartupPropertyEnum;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by Davy Maddelein on 19/11/2014.
 */
public class StartupProperties extends AbstractProperties {

    private static StartupProperties startupProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/startupproperties");

    private StartupProperties(File aPropertyFile, EnumSet aPropertyEnum) throws IOException {
        super(aPropertyFile, aPropertyEnum);
    }

    private StartupProperties(EnumSet programPropertyEnumSet) {
        super(programPropertyEnumSet);
    }

    public static StartupProperties getInstance() {
        if (startupProperties == null) {
            try {
                startupProperties = new StartupProperties(propertyFile, EnumSet.allOf(StartupPropertyEnum.class));
            } catch (IOException ex) {
                //FaultBarrier.getInstance().handleException(ex);
                startupProperties = new StartupProperties(EnumSet.allOf(StartupPropertyEnum.class));
            }
        }
        return startupProperties;
    }
}
