package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

/**
 *
 * @author Davy
 */
public abstract class DataRetrievalStep extends Observable implements Callable<List<Protein>> {

    protected List<Protein> proteinList = new ArrayList<>();
    private boolean execute;

    public DataRetrievalStep() {
    }

    public DataRetrievalStep(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public abstract DataRetrievalStep getInstance(List<Protein> aProteinList);

    public boolean executeStep() {
        return execute;
    }

    public void setExecute(boolean toExecute) {
        this.execute = toExecute;
    }

    public abstract String getRetrievalStepDescription();
}
