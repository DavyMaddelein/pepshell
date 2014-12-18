package com.compomics.pepshell.model.gradientMaps;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 */
public class SecondaryStructureMaps {

        private static final Map<String, Color> secStructMap = new HashMap<String, Color>() {
        {
            put("G", new Color(184, 78, 255));
            put("E", new Color(78, 255, 255));
            put("T", new Color(78, 255, 131));
            put("C", new Color(255, 143, 78));
            put("H", new Color(255, 78, 143));
        }
    };
    
    public static Map<String,Color> getColorMap() {
        return secStructMap;
    }
    
    
}
