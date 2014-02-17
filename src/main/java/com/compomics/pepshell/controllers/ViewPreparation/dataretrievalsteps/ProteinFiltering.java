package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.filters.FilterParent;
import com.compomics.pepshell.filters.NaiveFilter;
import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy
 */
public class ProteinFiltering implements DataRetrievalStep {

    private FilterParent<Protein> filter = new NaiveFilter<Protein>();
    private List<Protein> filterList = new ArrayList<Protein>();

    private List<Protein> proteinList = new ArrayList<Protein>();

    private Observable notifier = new Observable();

    private ProteinFiltering(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public ProteinFiltering() {
    }

    public void setFilter(FilterParent<Protein> filter) {
        this.filter = filter;
    }

    public void setFilterList(List<Protein> filterList) {
        this.filterList = filterList;
    }

    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        ProteinFiltering toReturn = new ProteinFiltering(aProteinList);
        toReturn.setFilter(filter);
        toReturn.setFilterList(filterList);
        return toReturn;
    }

    public List<Protein> call() throws Exception {

        List<Protein> returnList = proteinList;
        if (!filterList.isEmpty()) {
            returnList = filter.filter(proteinList, filterList);
        }
        return returnList;

    }

    public Observable getNotifier() {
        return this.notifier;
    }
}
