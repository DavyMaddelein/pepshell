/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.model.enums;

/**
 *
 * @author Davy Maddelein
 */
public enum PossiblePeptideMetaDataAnnotationsEnum {
    
    intensity("intensity column"),
    sizeOfExperimentColumns("number of columns per experiment"),
    ratio("ratio column"),
    peptide("peptide sequence column"),
    protein("protein identifier column"),
    proteinSequence("protein sequence column")
    ;

    String explanation;
    
    private PossiblePeptideMetaDataAnnotationsEnum(String stringRepresentation) {
    
        explanation = stringRepresentation;
    
    }
    
    @Override
    public String toString(){
        return explanation;
    }
    
    
}
