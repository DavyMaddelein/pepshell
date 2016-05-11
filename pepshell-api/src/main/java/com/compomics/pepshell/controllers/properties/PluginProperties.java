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

import com.compomics.pepshell.model.enums.PluginPropertiesEnum;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by pitro11a on 3/10/2016.
 */
public class PluginProperties extends AbstractProperties {

    private static final File pluginPropertiesFile = new File(System.getProperty("user.home")+"/.compomics/pepshell/pluginpreferences");
    static PluginProperties pluginPropertiesInstance;

    private PluginProperties(File aPropertyFile) throws IOException {
        super(aPropertyFile, EnumSet.allOf(PluginPropertiesEnum.class));
    }

    private PluginProperties() {
        super(EnumSet.allOf(PluginPropertiesEnum.class));
    }

    public static PluginProperties getInstance() throws IOException{
        if (pluginPropertiesInstance == null) {
            try {
                pluginPropertiesInstance = new PluginProperties(pluginPropertiesFile);
            } catch (IOException ex) {
                pluginPropertiesInstance = new PluginProperties();
                IOException exception = new IOException("could not load plugins settings");
                exception.addSuppressed(ex);
                throw exception;
            }
        }
        return pluginPropertiesInstance;
    }
}
