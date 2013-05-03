package com.compomics.peppi.model;

/**
 *
 * @author Davy
 */
public class QuantedPeptideGroup extends PeptideGroup {
    
    int ratio;
    private int timesHeavyFound = 0;
    private int timesLightFound = 0;
    
    public int getRatio(){
        return ratio;
    }
    
        public int getTimesHeavyFound() {
        return timesHeavyFound;
    }

    public void setTimesHeavyFound(int timesHeavyFound) {
        this.timesHeavyFound = timesHeavyFound;
    }

    public int getTimesLightFound() {
        return timesLightFound;
    }

    public void setTimesLightFound(int timesLightFound) {
        this.timesLightFound = timesLightFound;
    }

    public void incrementTimesHeavyFound() {
        timesHeavyFound++;
    }

    public void incrementTimesLightFound() {
        timesLightFound++;
    }
    
}
