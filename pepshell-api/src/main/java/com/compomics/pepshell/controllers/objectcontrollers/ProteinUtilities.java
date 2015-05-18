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

package com.compomics.pepshell.controllers.objectcontrollers;

import com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager;
import com.compomics.pepshell.model.AminoAcidBiMap;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.ProteinInterface;
/**
 *
 * @author Davy Maddelein
 */
public class ProteinUtilities {

    /**
     * translate three letter notation of a peptide in one letter notation
     *
     * @param peptideSequence the three letter sequence of a peptide
     * @return the one letter sequence of a peptide
     */
    public static String fromThreeLetterToOneLetterAminoAcids(String peptideSequence) {
        StringBuilder translatedSequence = new StringBuilder();
        if (peptideSequence.length() % 3 != 0) {
            throw new StringIndexOutOfBoundsException("not a sequence consisting of three letter amino acid codes");
        }
        //change to a string array split on each 3 chars
        for (int i = 0; i < peptideSequence.length() - 3; i += 3) {
            translatedSequence.append(AminoAcidBiMap.getOneLetterToThreeLetter(peptideSequence.substring(i, i + 3)));
        }
        return translatedSequence.toString();
    }

    /**
     * retrieve the {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} identified by the given accession from the protein store
     *
     * @param accession the protein accession to look in the store for
     * @return the requested {@link ProteinInterface}
     */
    public static PepshellProtein getProtein(String accession) {
        return ProteinStoreManager.getInstance().retrieveFromStore(accession);
    }
}
