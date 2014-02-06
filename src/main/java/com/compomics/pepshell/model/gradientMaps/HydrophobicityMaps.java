package com.compomics.pepshell.model.gradientMaps;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class HydrophobicityMaps {

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
}
