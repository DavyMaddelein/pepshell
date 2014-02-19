package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.filters.FilterParent;
import com.compomics.pepshell.filters.NaiveFilter;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;

/**
 *
 * @author Davy
 */
public abstract class ViewPreparation<T extends Experiment, V extends Protein> extends Observable {

    protected FilterParent<Protein> filter = new NaiveFilter<>();
    protected List<Protein> filterList = new ArrayList<>();
    boolean hasToFilter = false;
    boolean hasToMask = false;
    Set<V> maskingSet = new HashSet<>();
    boolean hasToFetchDomainData = false;
    boolean hasToAddQuantData = false;
    boolean hasToTranslateAccessions = false;
    boolean hasToRetrievePDBData = false;

    final ProgressMonitor progressMonitor = new ProgressMonitor(new JPanel(), "retrieving data", "retrieving data", 0, 100);

    public void start(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        retrieveData(referenceExperiment, ExperimentsToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
    }

    public abstract T retrieveData(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject);

    protected abstract void retrieveSecondaryData(T experiment);

    protected abstract boolean checkAndAddQuantToProteinsInExperiment(T anExperiment);

    // this doesn't do what is advertised, review this, and rename this to checkReferenceProteinOccurencesInExperiments or something
    protected boolean removeProteinsNotInReferenceExperiment(T referenceProject, T projectToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        //perhaps change with list so it is easier to notify observers of progress
        boolean finished = false;
        for (Protein aProtein : referenceProject.getProteins()) {
            if (projectToCompareWith.getProteins().contains(aProtein)) {
                aProtein.getProteinInfo().increaseNumberOfProjectOccurences();
                projectToCompareWith.getProteins().get(projectToCompareWith.getProteins().indexOf(aProtein)).getProteinInfo().increaseNumberOfProjectOccurences();
            }
        }
        if (removeNonOverlappingPeptidesFromReferenceProject) {
            //replace with iterator and remove
            for (Protein aProtein : projectToCompareWith.getProteins()) {
                if (aProtein.getProteinInfo().getNumberOfProjectOccurences() == 0) {

                }
            }
        }

        return finished;
    }

    public abstract void addProteinsToExperiment(AbstractDataMode dataMode);

    public void setFilter(FilterParent<Protein> aFilter) {
        this.filter = aFilter;
    }

    /**
     * public FilterParent<V> getFilter() { return filter; }
     */
    public void setProteinsToFilter(List<Protein> aFilterList) {
        this.filterList.clear();
        this.filterList.addAll(aFilterList);
    }

    public void hasToFilter(boolean decision) {
        this.hasToFilter = decision;
    }

    public void hasToMask(boolean decision) {
        this.hasToMask = decision;
    }

    public void setProteinMasks(Set<V> masks) {
        
        this.maskingSet.addAll(masks);
    }

    public void hasToFetchDomainData(boolean decision) {
        this.hasToFetchDomainData = decision;
    }

    public void hasToAddQuantDataToExperiments(boolean decision) {
        this.hasToAddQuantData = decision;
    }

    public void hasToTranslateAccessions(boolean decision) {
        this.hasToTranslateAccessions = decision;
    }

    public void hasToRetrievePDBData(boolean decision) {
        this.hasToRetrievePDBData = decision;
    }
}
