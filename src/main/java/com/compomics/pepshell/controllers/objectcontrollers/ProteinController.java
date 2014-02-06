package com.compomics.pepshell.controllers.objectcontrollers;

import com.compomics.pepshell.model.AminoAcidBiMap;
import com.compomics.pepshell.model.MapperInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.PeptideGroup;
import java.util.Iterator;

/**
 *
 * @author Davy
 */
public class ProteinController {

    /**
     * maps the peptides to the protein and adds the info to the grouped
     * peptides
     *
     * @param protein protein to align it's assigned peptide groups with
     */
    public static void alignPeptidesOfsProtein(Protein protein) {
        MapperInfo info;
        Iterator<PeptideGroup> proteinPeptideGroups = protein.getPeptideGroupsForProtein().iterator();
        while (proteinPeptideGroups.hasNext()) {
            PeptideGroup aPeptideGroup = proteinPeptideGroups.next();
            info = PeptideGroupController.mapPeptideGroupToProtein(protein, aPeptideGroup);
            aPeptideGroup.setAlignmentPositions(info.getStartingAlignmentPosition(), info.getLastAlignmentPosition());
        }
    }

    public static String fromThreeLetterToOneLetterAminoAcids(String threeLetterSequence) {
        StringBuilder translatedSequence = new StringBuilder();
        if (threeLetterSequence.length() % 3 != 0) {
            throw new StringIndexOutOfBoundsException("not a sequence consisting of three letter amino acid codes");
        }
        //change to a string array split on each 3 chars
        for (int i = 0; i < threeLetterSequence.length() - 3; i += 3) {
            translatedSequence.append(AminoAcidBiMap.AminoAcidLetters.inverse().get(threeLetterSequence.substring(i, i + 3)));
        }
        return translatedSequence.toString();
    }
}
