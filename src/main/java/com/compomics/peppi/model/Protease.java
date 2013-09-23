package com.compomics.peppi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Protease extends Protein {

    private StringBuilder startRegex = new StringBuilder();
    private StringBuilder stopRegex = new StringBuilder();
    private boolean semiSpecific = false;
    private int maxPeptideLength = 30;
    private int minPeptideLength = 3;

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids) {
        super(accession);
        createCleaveRegex(startRegex, startSpliceAminoAcids, Collections.EMPTY_LIST, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, Collections.EMPTY_LIST);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, boolean semispecific) {
        super(accession);
        this.semiSpecific = semispecific;
        createCleaveRegex(startRegex, startSpliceAminoAcids, Collections.EMPTY_LIST, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, Collections.EMPTY_LIST);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, boolean semispecific, int maxPeptideLength) {
        super(accession);
        this.maxPeptideLength = maxPeptideLength;
        this.semiSpecific = semispecific;
        createCleaveRegex(startRegex, startSpliceAminoAcids, Collections.EMPTY_LIST, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, Collections.EMPTY_LIST);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, List<String> startInhibitorAminoAcids, List<String> stopInhibitorAminoAcids) {
        super(accession);
        createCleaveRegex(startRegex, startSpliceAminoAcids, startInhibitorAminoAcids);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, stopInhibitorAminoAcids);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, List<String> startInhibitorAminoAcids, List<String> stopInhibitorAminoAcids, boolean semispecific, int maxPeptideLength) {
        super(accession);
        this.maxPeptideLength = maxPeptideLength;
        this.semiSpecific = semispecific;
        createCleaveRegex(startRegex, startSpliceAminoAcids, startInhibitorAminoAcids, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, stopInhibitorAminoAcids);
    }

    public boolean getSemiSpecific() {
        return semiSpecific;
    }

    public void setSemiSpecific(boolean semiSpecific) {
        this.semiSpecific = semiSpecific;
    }

    public List<String> digest(String proteinSequence) {
        List<String> peptideList = new ArrayList<String>();
        //peptideList = proteinSequence. split with regex arrays.aslist();
        return peptideList;
    }

    private void createCleaveRegex(StringBuilder regexContainer, List<String> cleavageAminoAcids, List<String> restrictionAminoAcids) {
        for (String aminoAcid : cleavageAminoAcids) {
            regexContainer.append("|").append(aminoAcid);
        }
        if (regexContainer.length() != 0) {
            regexContainer.deleteCharAt(0);
            regexContainer.insert(0, "[").append("]");
        }
        if (!restrictionAminoAcids.isEmpty()) {
            regexContainer.append("(?=!");
            for (String restrictionAminoAcid : restrictionAminoAcids) {
                regexContainer.append("|").append(restrictionAminoAcid);
            }
            regexContainer.append(")");
        }
    }

    private void createCleaveRegex(StringBuilder regexContainer, List<String> cleavageAminoAcids, List<String> restrictionAminoAcids, int maxPeptideLength) {
        createCleaveRegex(regexContainer, cleavageAminoAcids, restrictionAminoAcids);
        regexContainer.append("{").append(minPeptideLength).append(",").append(maxPeptideLength).append("}");
    }
}
