package com.compomics.peppi.controllers.ViewPreparation;

import com.compomics.peppi.controllers.DataModes.AbstractDataMode;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author Davy
 */
public abstract class ViewPreparation extends Observable {

    public abstract Set<Protein> PrepareProteinsForJList(Project referenceProject, List<Project> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject);

    protected abstract boolean checkAndAddQuantToProteinsInProject(Project aProject);

    protected boolean checkOverlappingPeptides(Project referenceProject, Project projectToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        //perhaps change with list so it is easier to notify observers of progress
        boolean finished = false;
        Iterator<Protein> referenceProjectProteinIter = referenceProject.getProteins().iterator();
        while (referenceProjectProteinIter.hasNext()) {
            Protein currentReferenceProtein = referenceProjectProteinIter.next();
            if (projectToCompareWith.getProteins().contains(currentReferenceProtein)) {
                currentReferenceProtein.getProteinInfo().increaseNumberOfProjectOccurences();
            } else if (removeNonOverlappingPeptidesFromReferenceProject) {
                referenceProjectProteinIter.remove();
            }
        }
        return finished;
    }

    public abstract void addProteinsToProject(AbstractDataMode dataMode);
}