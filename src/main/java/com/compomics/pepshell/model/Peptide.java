package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class Peptide {

    private final String sequence;
    private int beginningProteinMatch = -1;
    private int endProteinMatch = -1;
    private int timesFound = 0;
    private boolean isMiscleaved;
    private double totalSpectrumIntensity = 0.0;

    /**
     * create a peptide object instance
     *
     * @param sequence the peptide sequence
     */
    public Peptide(String sequence) {
        this.sequence = sequence;
    }

    public Peptide(String sequence, double totalSpectrumIntensity) {
        this.sequence = sequence;
        this.totalSpectrumIntensity = totalSpectrumIntensity;
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

    public double getTotalSpectrumIntensity() {
        return totalSpectrumIntensity;
    }

    public void setTotalSpectrumIntensity(double totalSpectrumIntensity) {
        this.totalSpectrumIntensity = totalSpectrumIntensity;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        hash = 71 * hash + this.beginningProteinMatch;
        hash = 71 * hash + this.endProteinMatch;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Peptide other = (Peptide) obj;
        if ((this.sequence == null) ? (other.getSequence() != null) : !this.sequence.equals(other.getSequence())) {
            return false;
        }
        if (this.beginningProteinMatch != other.getBeginningProteinMatch()) {
            return false;
        }
        if (this.endProteinMatch != other.getEndProteinMatch()) {
            return false;
        }
        return true;
    }
}
