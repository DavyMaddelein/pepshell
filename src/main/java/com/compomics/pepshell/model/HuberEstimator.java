package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class HuberEstimator {

    private double mean;
    private double stdev;
    private int iteration;
    
    public HuberEstimator(double mean, double stdev, int iteration) {
        this.mean = mean;
        this.stdev = stdev;
        this.iteration = iteration;
    }

    public double getMean() {
        return mean;
    }

    public double getStdev() {
        return stdev;
    }

    public int getIteration() {
        return iteration;
    }    
}
