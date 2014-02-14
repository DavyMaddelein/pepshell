package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class AccessionMasking implements DataRetrievalStep {

    Set<Protein> maskingSet = new HashSet<Protein>();

    public List<Protein> execute(List<Protein> proteinList) {
        for (Protein maskingProtein : maskingSet) {
            if (proteinList.contains(maskingProtein)) {
                proteinList.get(proteinList.indexOf(maskingProtein)).setVisibleAccession(maskingProtein.getVisibleAccession());
            }
        }
        return proteinList;
    }

    public boolean isMultithreadAble() {
        return true;
    }
}
