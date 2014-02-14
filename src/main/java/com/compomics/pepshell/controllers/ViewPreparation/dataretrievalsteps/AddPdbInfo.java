package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import java.util.List;

/**
 *
 * @author Davy
 */
public class AddPdbInfo implements DataRetrievalStep {

    public List<Protein> execute(List<Protein> proteinList) {
        for (Protein aProtein : proteinList) {
            aProtein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(aProtein, null));
        }
        return proteinList;
    }

    public boolean isMultithreadAble() {
        return true;
    }

}
