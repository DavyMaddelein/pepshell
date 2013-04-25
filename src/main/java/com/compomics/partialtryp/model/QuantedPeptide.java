/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.model;

/**
 *
 * @author Davy
 */
public class QuantedPeptide extends Peptide {

    String quantitationType;

    public QuantedPeptide(String sequence, String quantitationType) {
        super(sequence);
        this.quantitationType = quantitationType;
    }

    public String getQuantitationType() {
        return quantitationType;
    }
}
