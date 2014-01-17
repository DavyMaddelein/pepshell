/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.model;


/**
 *
 * @author Davy
 */
public class QuantedPeptide extends Peptide {

    String quantitationType;
    Double ratio;

    public QuantedPeptide(String sequence, String quantitationType) {
        super(sequence);
        this.quantitationType = quantitationType;
    }

    public String getQuantitationType() {
        return quantitationType;
    }

    public double getRatio() {
        return this.ratio;
    }

    public void setRatio(double aRatio) {
        this.ratio = aRatio;
    }
}
