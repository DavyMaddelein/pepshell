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

package com.compomics.pepshell.controllers.secondarystructureprediction;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.UpdateMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Davy Maddelein
 */
abstract class SecondaryStructurePrediction {

    static final Map<String, String> secStructMap = new HashMap<String, String>() {
        {
            put("alpha_helix", "G");
            put("beta_strand", "E");
            put("coil", "C");
            put("strand", "E");
            put("helix", "G");
            put("turn", "I");
        }
    };

    public SecondaryStructurePrediction() {
        Properties secStructProps = new Properties();
        try {
            secStructProps.load(new FileInputStream(new File(ClassLoader.getSystemClassLoader().getResource("secondary_structures.properties").getPath())));
        } catch (NullPointerException | IOException e) {
            FaultBarrier.getInstance().handleException(e, new UpdateMessage(false, "could not load definitions for secondary structures", true));
        }
        if (secStructProps.isEmpty()) {
            for (Map.Entry<String, String> anEntry : secStructMap.entrySet()) {
                secStructProps.setProperty(anEntry.getKey(), anEntry.getValue());
            }
        }
    }

    public abstract List<String> getPrediction(String accession) throws IOException;

}
