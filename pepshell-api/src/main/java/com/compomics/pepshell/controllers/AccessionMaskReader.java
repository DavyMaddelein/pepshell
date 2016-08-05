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

package com.compomics.pepshell.controllers;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * reads a file with accession masks
 * @author Davy Maddelein
 */
public class AccessionMaskReader {

    /**
     * parses an accession masking file, the layout is original accession= masking accession
     * @param selectedFile the file to read
     * @return map of accessions and masking accessions
     * @throws IOException
     */
    public static Map<String, String> parseAccessionFile(File selectedFile) throws IOException {
        Map<String, String> accessionMasks = new HashMap<>();
        //this is shorter than parsing the file myself, but properties is badly implemented -.-
        Properties props = new Properties();
        if (selectedFile != null && selectedFile.exists()) {
            try (BufferedReader propertyReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ClassLoader.getSystemClassLoader().getResource("secondary_structures.properties").getPath())), Charset.forName("UTF-8")))) {
                props.load(propertyReader);
                for (final String name: props.stringPropertyNames())
                    accessionMasks.put(name, props.getProperty(name));
            }
        }
        return accessionMasks;
    }

}
