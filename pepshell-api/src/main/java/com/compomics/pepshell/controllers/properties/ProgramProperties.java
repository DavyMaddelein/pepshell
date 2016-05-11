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

import com.compomics.pepshell.model.enums.ProgramPropertyEnum;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public class ProgramProperties extends AbstractProperties {

    private static ProgramProperties programProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/programpreferences");

    private ProgramProperties(File aPropertyFile, EnumSet aPropertyEnum) throws IOException {
        super(aPropertyFile, aPropertyEnum);
    }

    private ProgramProperties(EnumSet programPropertyEnumSet) {
        super(programPropertyEnumSet);
    }

    public static ProgramProperties getInstance() {
        if (programProperties == null) {
            try {
                programProperties = new ProgramProperties(propertyFile, EnumSet.allOf(ProgramPropertyEnum.class));
            } catch (IOException ex) {
                programProperties = new ProgramProperties(EnumSet.allOf(ProgramPropertyEnum.class));
            }
        }
        return programProperties;
    }
}
