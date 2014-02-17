package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy
 */
public class AddPdbInfo implements DataRetrievalStep {

    private Observable notifier = new Observable();

    private List<Protein> proteinList = new ArrayList<Protein>();

    private AddPdbInfo(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AddPdbInfo() {
    }

    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        return new AddPdbInfo(aProteinList);
    }

    public List<Protein> call() throws Exception {

        for (Protein aProtein : proteinList) {
            aProtein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(aProtein, null));
        }
        return Collections.unmodifiableList(proteinList);
    }

    public Observable getNotifier() {
        return this.notifier;
    }
}
