/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class ProteaseSpliceLocationMap {

    private static final Map<String, Protease> proteaseMap = new HashMap<String, Protease>() {
        {
            put("trypsin", new Protease("P07477", new ArrayList<String>() {
                {
                    add("R");
                    add("K");
                }
            }, new ArrayList<String>() {
                {
                    add("R");
                    add("K");
                }
            }));
        }
    };
    
    public Map<String,Protease> getProteaseMap(){
        return Collections.unmodifiableMap(proteaseMap);
    }
}
