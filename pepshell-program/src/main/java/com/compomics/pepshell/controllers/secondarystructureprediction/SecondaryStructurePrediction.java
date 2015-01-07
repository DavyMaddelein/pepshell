package com.compomics.pepshell.controllers.secondarystructureprediction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 */
abstract class SecondaryStructurePrediction {

    static final Map<String, String> secStructMap = new HashMap<String, String>() {
        {
            put("alpha_helix", "G");
            put("beta_strand", "E");
            put("turn", "T");
            put("coil", "C");
            put("helix", "H");
        }
    };

    public abstract List<String> getPrediction(String accession) throws IOException;

}
