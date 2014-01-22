package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.filters.FilterParent;
import com.compomics.pepshell.filters.NaiveFilter;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy
 */
public abstract class ViewPreparation<T extends Experiment> extends Observable {

    protected FilterParent<Protein> filter = new NaiveFilter<Protein>();
    protected List<Protein> filterList = new ArrayList<Protein>();

    public abstract T PrepareProteinsForJList(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject);

    protected abstract boolean checkAndAddQuantToProteinsInExperiment(T anExperiment);

    protected boolean checkOverlappingPeptides(T referenceProject, T projectToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        //perhaps change with list so it is easier to notify observers of progress
        boolean finished = false;
        Iterator<Protein> referenceProjectProteinIter = referenceProject.iterator();
        while (referenceProjectProteinIter.hasNext()) {
            Protein currentReferenceProtein = referenceProjectProteinIter.next();
            if (projectToCompareWith.contains(currentReferenceProtein)) {
                currentReferenceProtein.getProteinInfo().increaseNumberOfProjectOccurences();
            }
            if (removeNonOverlappingPeptidesFromReferenceProject) {
                referenceProjectProteinIter.remove();
            }
        }
        return finished;
    }

    public abstract void addProteinsToExperiment(AbstractDataMode dataMode);

    public void setFilter(FilterParent<Protein> aFilter) {
        this.filter = aFilter;
    }

    public FilterParent getFilter() {
        return filter;
    }

    public void setProteinsToFilter(List<Protein> aFilterList) {
        this.filterList = aFilterList;
    }

    public void clearFilter() {
        filterList.clear();
    }
}
