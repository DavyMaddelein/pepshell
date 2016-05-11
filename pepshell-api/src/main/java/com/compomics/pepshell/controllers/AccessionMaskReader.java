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

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 */
public class AccessionMaskReader {

    public static Map<PepshellProtein, String> parseAccessionFile(File selectedFile) throws IOException {
        Map<PepshellProtein, String> accessionMasks = new HashMap<>();
        if (selectedFile != null && selectedFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line = reader.readLine();
                while (line != null) {
                    String[] splitLines = line.split("=");
                    accessionMasks.put(new PepshellProtein(splitLines[0]), splitLines[1]);
                    line = reader.readLine();
                }
            }
        }
        return accessionMasks;
    }

}
