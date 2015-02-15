/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    HASHEADERS("file has headers", null),
    PEPTIDEENDLOCATION("end location of the peptide", null),
    PEPTIDELOCATIONSTART("start location of the peptide", null);
    

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
