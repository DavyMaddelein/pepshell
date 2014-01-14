package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy
 */
public abstract class ViewPreparation extends Observable {

    public abstract List<Protein> PrepareProteinsForJList(Experiment referenceProject, List<Experiment> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject);

    protected abstract boolean checkAndAddQuantToProteinsInExperiment(Experiment anExperiment);

    protected boolean checkOverlappingPeptides(Experiment referenceProject, Experiment projectToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        //perhaps change with list so it is easier to notify observers of progress
        boolean finished = false;
        Iterator<Protein> referenceProjectProteinIter = referenceProject.getProteins().iterator();
        while (referenceProjectProteinIter.hasNext()) {
            Protein currentReferenceProtein = referenceProjectProteinIter.next();
            if (projectToCompareWith.getProteins().contains(currentReferenceProtein)) {
                currentReferenceProtein.getProteinInfo().increaseNumberOfProjectOccurences();
            } if (removeNonOverlappingPeptidesFromReferenceProject) {
                referenceProjectProteinIter.remove();
            }
        }
        return finished;
    }

    public abstract void addProteinsToExperiment(AbstractDataMode dataMode);
}