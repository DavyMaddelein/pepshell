/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Protease extends Protein {

    private List<String> startSpliceAminoAcids = new ArrayList<String>();
    private List<String> stopSpliceAminoAcids = new ArrayList<String>();

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids) {
        super(accession);
        this.startSpliceAminoAcids = startSpliceAminoAcids;
        this.stopSpliceAminoAcids = stopSpliceAminoAcids;

    }

    public List<String> getStartSpliceAminoAcids() {
        return Collections.unmodifiableList(startSpliceAminoAcids);
    }

    public List<String> getStopSpliceAminoAcids() {
        return Collections.unmodifiableList(stopSpliceAminoAcids);
    }
}
