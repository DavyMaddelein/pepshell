package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
 */
public class ProjectInfo {
    private int maxRatio = 1;
    private int minRatio = -1;

    public int getMaxRatio() {
        return maxRatio;
    }

    public void setMaxRatio(int maxRatio) {
        this.maxRatio = maxRatio;
    }

    public int getMinRatio() {
        return minRatio;
    }

    public void setMinRatio(int minRatio) {
        this.minRatio = minRatio;
    }
    
}
