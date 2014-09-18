package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.UpdateMessage;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy
 */
public class AddDomains extends DataRetrievalStep {

    private AddDomains(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AddDomains() {
    }

    @Override
    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        return new AddDomains(aProteinList);
    }

    @Override
    public List<Protein> call() throws Exception {
        for (Protein protein : proteinList) {
            try {
                protein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein));
                this.setChanged();
                this.notifyObservers(new UpdateMessage(true, "added domain info to " + protein.getProteinAccession()));
            } catch (DataRetrievalException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
        return Collections.unmodifiableList(proteinList);
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Add Domains";
    }
}
