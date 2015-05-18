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

package com.compomics.pepshell.model.enums;

import javax.swing.text.DocumentFilter;

/**
 *
 * @author Davy Maddelein
 */
public enum PossibleMetaDataAnnotationsEnum {

    INTENSITY("intensity column",Integer.class),
    SIZEOFEXPERIMENTCOLUMS("number of columns per experiment",Integer.class),
    RATIO("ratio column",Integer.class),
    PEPTIDESEQUENCE("peptide sequence column",Integer.class),
    PROTEINACCESSION("protein identifier column",Integer.class),
    PROTEINSEQUENCE("protein sequence column",Integer.class),
    VALUESEPARATOR("separator between values in a text file",String.class),
    //isbooleannotstring
    HASHEADERS("file has headers", Boolean.class),
    PEPTIDEENDLOCATION("end location of the peptide", String.class),
    PEPTIDESTARTLOCATION("start location of the peptide", String.class);
    

    final String representation;
    private final Class clazz;

    PossibleMetaDataAnnotationsEnum(String representation,Class allowedInput) {
        this.clazz = allowedInput;
        this.representation = representation;
    
    }
    
    @Override
    public String toString(){
        return representation;
    }

    public Class getAllowedInputClass(){
        return clazz;
    }

}
