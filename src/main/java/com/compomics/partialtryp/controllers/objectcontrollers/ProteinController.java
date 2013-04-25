package com.compomics.partialtryp.controllers.objectcontrollers;

import com.compomics.partialtryp.model.MapperInfo;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.PeptideGroup;


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
