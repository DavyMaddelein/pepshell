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

package com.compomics.pepshell.model.protein;

/**
 * the interface which defines the most basic functions of proteins in the Pepshell program
 * @author Davy Maddelein
 */
public interface ProteinInterface {

    /**
     * gets the original accession as parsed from the original source
     *
     * @return the original accession id
     */
    String getOriginalAccession();

    /**
     * returns the extra identifier, if an extra identifier (such as a database id, or key in key value pair for quick lookups) is defined
     *
     * @return the extra identifier
     */
    String getExtraIdentifier();

    /**
     * get the at this point defined accession which is meant for end user viewing, this can be the same as the original accession.
     * @return the GUI ready accession
     */
    String getVisibleAccession();

    /**
     * sets the original accession of a protein
     *
     * @param anAccession the accession to set
     */
    void setOriginalAccession(String anAccession);

    /**
     * sets the extra identifier, for when there are non accession based look ups
     *
     * @param anExtraIdentifier the extra identifier to set
     */
    void setExtraIdentifier(String anExtraIdentifier);

    /**
     * sets the accession meant for end user viewing
     * @param visibleAccession the accession to set
     */
    void setVisibleAccession(String visibleAccession);
    
}
