package com.compomics.pepshell.controllers.objectcontrollers;

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
        Iterator<PeptideGroup> proteinPeptideGroups = protein.iterator();
        while (proteinPeptideGroups.hasNext()) {
            PeptideGroup aPeptideGroup = proteinPeptideGroups.next();
            info = PeptideGroupController.mapPeptideGroupToProtein(protein, aPeptideGroup);
            aPeptideGroup.setAlignmentPositions(info.getStartingAlignmentPosition(), info.getLastAlignmentPosition());
        }
    }
}
