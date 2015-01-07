package com.compomics.pepshell.controllers.objectcontrollers;

import com.compomics.pepshell.model.AminoAcidBiMap;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.MappedPosition;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.PeptideGroup;

/**
 *
 * @author Davy Maddelein
 */
public class ProteinController {

    /**
     * convenience method to run alignPeptidesOfProteins on all proteins in an
     * experiment
     *
     * @param experiment
     */
    public static void alignPeptidesOfProteinsInExperiment(Experiment experiment) {
        for (Protein aProtein : experiment.getProteins()) {
            PeptideGroupController.mapPeptideGroupsToProtein(aProtein);
        }
    }

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
            translatedSequence.append(AminoAcidBiMap.AminoAcidLetters.inverse().get(peptideSequence.substring(i, i + 3)));
        }
        return translatedSequence.toString();
    }
}
