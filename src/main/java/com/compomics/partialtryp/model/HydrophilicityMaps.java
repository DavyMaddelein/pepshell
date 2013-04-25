/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class HydrophilicityMaps {

    private static final Map<String, Double> hydrophilicityMapPh7 = new HashMap<String, Double>() {
        {
            put("L", 0.01935);
            put("I", 0.00645);
            put("F", 0.00000);
            put("W", 0.01935);
            put("V", 0.15483);
            put("M", 0.16774);
            put("Y", 0.21935);
            put("C", 0.32903);
            put("A", 0.38065);
            put("T", 0.56129);
            put("H", 0.59354);
            put("G", 0.64516);
            put("S", 0.67741);
            put("Q", 0.70967);
            put("R", 0.73548);
            put("K", 0.79354);
            put("N", 0.82580);
            put("E", 0.84516);
            put("P", 0.94193);
            put("D", 1.00000);

        }
    };

    public static Map<String,Double> getHydrophilicityMapPh7(){
        return hydrophilicityMapPh7;
    }
}
