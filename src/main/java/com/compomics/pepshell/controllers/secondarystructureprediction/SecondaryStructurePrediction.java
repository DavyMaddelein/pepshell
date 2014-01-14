package com.compomics.pepshell.controllers.secondarystructureprediction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy
 */
public abstract class SecondaryStructurePrediction {

    protected static final Map<String, String> secStructMap = new HashMap<String, String>() {
        {
            put("alpha_helix", "H");
            put("beta_strand", "E");
            put("turn", "T");
            put("coil", "-");
            put("helix", "H");
        }
    };

    public abstract String getPrediction(String accession) throws IOException;

}
