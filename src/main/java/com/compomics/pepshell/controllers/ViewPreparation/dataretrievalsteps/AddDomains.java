package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy
 */
public class AddDomains implements DataRetrievalStep {

    private List<Protein> proteinList = new ArrayList<Protein>();

    private Observable notifier = new Observable();

    
    private AddDomains(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AddDomains() {
    }

    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        return new AddDomains(aProteinList);
    }

    public List<Protein> call() throws Exception {
        for (Protein protein : proteinList) {
            try {
                protein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein));
            } catch (DataRetrievalException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
        return Collections.unmodifiableList(proteinList);
    }

    public Observable getNotifier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
