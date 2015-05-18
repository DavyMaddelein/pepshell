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

package com.compomics.pepshell.model.gradientMaps;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 */
public class HydrophobicityMaps {
    
    //make this an enum
    
    public static final Map<Character, Color> hydrophobicityMapPh7 = new HashMap<Character, Color>() {
        {           
            put('L', new Color((int)Math.ceil(0.01935*255),125,255));
            put('I', new Color((int)Math.ceil(0.00645*255),125,255));
            put('F', new Color((int)Math.ceil(0.00000*255),125,255));
            put('W', new Color((int)Math.ceil(0.01935*255),125,255));
            put('V', new Color((int)Math.ceil(0.15483*255),125,255));
            put('M', new Color((int)Math.ceil(0.16774*255),125,255));
            put('Y', new Color((int)Math.ceil(0.21935*255),125,255));
            put('C', new Color((int)Math.ceil(0.32903*255),125,255));
            put('A', new Color((int)Math.ceil(0.38065*255),125,255));
            put('T', new Color((int)Math.ceil(0.56129*255),125,255));
            put('H', new Color((int)Math.ceil(0.59354*255),125,255));
            put('G', new Color((int)Math.ceil(0.64516*255),125,255));
            put('S', new Color((int)Math.ceil(0.67741*255),125,255));
            put('Q', new Color((int)Math.ceil(0.70967*255),125,255));
            put('R', new Color((int)Math.ceil(0.73548*255),125,255));
            put('K', new Color((int)Math.ceil(0.79354*255),125,255));
            put('N', new Color((int)Math.ceil(0.82580*255),125,255));
            put('E', new Color((int)Math.ceil(0.84516*255),125,255));
            put('P', new Color((int)Math.ceil(0.94193*255),125,255));
            put('D', new Color((int)Math.ceil(1.00000*255),125,255));

        }
    };
    private static Map<Character, Color> currentHydrophobicityMap = hydrophobicityMapPh7;
    
        public static Map<Character, Color> getCurrentHydrophobicityMap(){
        return currentHydrophobicityMap;
    }
    
        public static void setCurrentHydrophobicityMap(Map<Character, Color> aHydrophobicityMap){
            currentHydrophobicityMap = aHydrophobicityMap;
        }
}
