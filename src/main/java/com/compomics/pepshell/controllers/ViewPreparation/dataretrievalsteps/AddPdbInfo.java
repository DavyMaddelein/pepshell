package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.UpdateMessage;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class AddPdbInfo extends DataRetrievalStep {

    private AddPdbInfo(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AddPdbInfo() {
    }

    @Override
    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        return new AddPdbInfo(aProteinList);
    }

    @Override
    public List<Protein> call() throws Exception {

        for (Protein aProtein : proteinList) {
            aProtein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(aProtein, null));
            this.setChanged();
            this.notifyObservers(new UpdateMessage(false,"added PDB info to" + aProtein.getProteinAccession(),false));
        }
        return Collections.unmodifiableList(proteinList);
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Add PDB info";
    }
}
