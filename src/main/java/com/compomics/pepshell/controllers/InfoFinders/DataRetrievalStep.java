package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.model.Protein;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

/**
 *
 * @author Davy
 */
public interface DataRetrievalStep extends Callable<List<Protein>> {

    public DataRetrievalStep getInstance(List<Protein> aProteinList);

    public Observable getNotifier();

}
