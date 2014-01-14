package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class Proteases {

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
    };

    public static Map<String, Protease> getProteaseMap() {
        return Collections.unmodifiableMap(proteaseMap);
    }
}
