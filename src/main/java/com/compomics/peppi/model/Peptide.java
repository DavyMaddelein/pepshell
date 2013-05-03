package com.compomics.peppi.model;

/**
 *
 * @author Davy
 */
public class Peptide {

    private String sequence;
    private int beginningProteinMatch = -1;
    private int endProteinMatch = -1;
    private int timesFound = 0;
    private boolean isMiscleaved;

    public Peptide(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return this.sequence;
    }

    public int getBeginningProteinMatch() {
        return beginningProteinMatch;
    }

    public void setBeginningProteinMatch(int beginningProteinMatch) {
        this.beginningProteinMatch = beginningProteinMatch;
    }

    public int getEndProteinMatch() {
        return endProteinMatch;
    }

    public void setEndProteinMatch(int endProteinMatch) {
        this.endProteinMatch = endProteinMatch;
    }

    public int getTimesFound() {
        return timesFound;
    }

    public void setTimesFound(int timesFound) {
        this.timesFound = timesFound;
    }

    public void incrementTimesFound() {
        timesFound++;
    }

    public boolean isIsMiscleaved() {
        return isMiscleaved;
    }

    public void setIsMiscleaved(boolean isMiscleaved) {
        this.isMiscleaved = isMiscleaved;
    }
}
