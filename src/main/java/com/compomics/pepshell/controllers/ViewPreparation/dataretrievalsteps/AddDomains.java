package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davy
 */
public class AddDomains implements DataRetrievalStep {

    public List<Protein> execute(List<Protein> proteinList) {
        for (Protein protein : proteinList) {
            try {
                protein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein));
            } catch (DataRetrievalException ex) {
                Logger.getLogger(AddDomains.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proteinList;
    }

    public boolean isMultithreadAble() {
        return true;
    }

}
