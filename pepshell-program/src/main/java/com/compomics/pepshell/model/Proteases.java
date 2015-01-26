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

package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 */
public class Proteases {

    private static final Map<String, Protease> proteaseMap = new HashMap<>();

    public Proteases() {

        proteaseMap.put("trypsin", new Protease("P07477", new ArrayList<String>() {
            {
                add("R");
                add("K");
            }
        }, new ArrayList<String>() {
            {
                add("R");
                add("K");
            }
        }, new ArrayList<String>() {
            {
                add("P");
            }
        }, new ArrayList<String>() {
            {
                add("P");
            }
        }));

    }

    public static Map<String, Protease> getProteaseMap() {
        return Collections.unmodifiableMap(proteaseMap);
    }
}
