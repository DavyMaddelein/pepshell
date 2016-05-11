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

package com.compomics.pepshellstandalone;

import com.compomics.pepshell.controllers.properties.AbstractProperties;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by Davy Maddelein on 4/1/2016.
 */
public class ViewProperties extends AbstractProperties {


    private static ViewProperties viewProperties;
    private static final File propertyFile = new File(System.getProperty("user.home"), ".compomics/pepshell/viewpreferences");


    ViewProperties(File aPropertyFile, EnumSet aPropertyEnum) throws IOException {
        super(aPropertyFile, aPropertyEnum);
    }

    ViewProperties(EnumSet aProperyEnum) {
        super(aProperyEnum);
    }

    public static ViewProperties getInstance(){
        if (viewProperties == null) {
            try {
                viewProperties = new ViewProperties(propertyFile, EnumSet.allOf(ViewPropertyEnum.class));
            } catch (IOException ex) {
                viewProperties = new ViewProperties(EnumSet.allOf(ViewPropertyEnum.class));
            }
        }
        return viewProperties;
    }
}
