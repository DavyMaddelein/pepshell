package com.compomics.pepshell.model;

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
            put('L', new Color((int)Math.ceil(0.01935*(double)255),255,255));
            put('I', new Color((int)Math.ceil(0.00645*(double)255),255,255));
            put('F', new Color((int)Math.ceil(0.00000*(double)255),255,255));
            put('W', new Color((int)Math.ceil(0.01935*(double)255),255,255));
            put('V', new Color((int)Math.ceil(0.15483*(double)255),255,255));
            put('M', new Color((int)Math.ceil(0.16774*(double)255),255,255));
            put('Y', new Color((int)Math.ceil(0.21935*(double)255),255,255));
            put('C', new Color((int)Math.ceil(0.32903*(double)255),255,255));
            put('A', new Color((int)Math.ceil(0.38065*(double)255),255,255));
            put('T', new Color((int)Math.ceil(0.56129*(double)255),255,255));
            put('H', new Color((int)Math.ceil(0.59354*(double)255),255,255));
            put('G', new Color((int)Math.ceil(0.64516*(double)255),255,255));
            put('S', new Color((int)Math.ceil(0.67741*(double)255),255,255));
            put('Q', new Color((int)Math.ceil(0.70967*(double)255),255,255));
            put('R', new Color((int)Math.ceil(0.73548*(double)255),255,255));
            put('K', new Color((int)Math.ceil(0.79354*(double)255),255,255));
            put('N', new Color((int)Math.ceil(0.82580*(double)255),255,255));
            put('E', new Color((int)Math.ceil(0.84516*(double)255),255,255));
            put('P', new Color((int)Math.ceil(0.94193*(double)255),255,255));
            put('D', new Color((int)Math.ceil(1.00000*(double)255),255,255));

        }
    };
}
