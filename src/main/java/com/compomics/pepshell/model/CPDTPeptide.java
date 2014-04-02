package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class CPDTPeptide extends Peptide {

    private final double probability;

    public CPDTPeptide(String sequence, double probability) {
        super(sequence);
        this.probability = probability;
    }

    public CPDTPeptide(String sequence, int startLocation, double probability) {
        super(sequence);
        this.setBeginningProteinMatch(startLocation);
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
