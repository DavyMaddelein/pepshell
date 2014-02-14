package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.filters.FilterParent;
import com.compomics.pepshell.filters.NaiveFilter;
import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class ProteinFiltering implements DataRetrievalStep {

    private FilterParent<Protein> filter = new NaiveFilter<Protein>();
    private List<Protein> filterList = new ArrayList<Protein>();

    public List<Protein> execute(List<Protein> proteinList) {
        List<Protein> returnList = proteinList;
        if (!filterList.isEmpty()) {
            returnList = filter.filter(proteinList, filterList);
        }
        return returnList;
    }

    public boolean isMultithreadAble() {
        return true;
    }

    public void setFilter(FilterParent<Protein> filter) {
        this.filter = filter;
    }

    public void setFilterList(List<Protein> filterList) {
        this.filterList = filterList;
    }

}
