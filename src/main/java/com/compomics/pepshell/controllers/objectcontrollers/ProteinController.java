package com.compomics.pepshell.controllers.objectcontrollers;

import com.compomics.pepshell.model.MapperInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.PeptideGroup;


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
        for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
            info = PeptideGroupController.mapPeptideGroupToProtein(protein, peptideGroup);
            peptideGroup.setAlignmentPositions(info.getStartingAlignmentPosition(), info.getLastAlignmentPosition());
        }
    }
}
