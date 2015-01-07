package com.compomics.pepshell.view;

import com.compomics.pepshell.view.componentmodels.IntegerDocumentFilter;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Davy Maddelein
 */
public enum PossibleMetaDataAnnotationsEnum {

    INTENSITY("intensity column",new IntegerDocumentFilter()),
    SIZEOFEXPERIMENTCOLUMS("number of columns per experiment",new IntegerDocumentFilter()),
    RATIO("ratio column",new IntegerDocumentFilter()),
    PEPTIDESEQUENCE("peptide sequence column",new IntegerDocumentFilter()),
    PROTEINACCESSION("protein identifier column",new IntegerDocumentFilter()),
    PROTEINSEQUENCE("protein sequence column",new IntegerDocumentFilter()), 
    VALUESEPARATOR("separator between values in a text file",null),
    HASHEADERS("file has headers",null);

    final String representation;
    final DocumentFilter filter;
    
    private PossibleMetaDataAnnotationsEnum(String representation,DocumentFilter allowedInput) {
    
        this.representation = representation;
        this.filter = allowedInput;
    
    }
    
    @Override
    public String toString(){
        return representation;
    }

    public DocumentFilter getFilter() {
        return filter;
    }
    
    
}
