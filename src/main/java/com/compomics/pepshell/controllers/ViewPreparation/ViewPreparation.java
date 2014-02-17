package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AccessionConverting;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AccessionMasking;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddDomains;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddPdbInfo;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.ProteinFiltering;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;

/**
 *
 * @author Davy
 */
public abstract class ViewPreparation<T extends Experiment, V extends Protein> {

    LinkedList<DataRetrievalStep> linkedSteps = new LinkedList<DataRetrievalStep>() {
        {
            //default order
            this.add(new AccessionMasking());
            this.add(new AccessionConverting());
            this.add(new ProteinFiltering());
            this.add(new AddDomains());
            this.add(new AddPdbInfo());
        }
    };

    protected ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    final ProgressMonitor progressMonitor = new ProgressMonitor(new JPanel(), "retrieving data", "retrieving data", 0, 101);

    public void start(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        retrieveData(referenceExperiment, ExperimentsToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
    }

    public abstract T retrieveData(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject);

    protected abstract void retrieveSecondaryData(T experiment);

    protected abstract boolean checkAndAddQuantToProteinsInExperiment(T anExperiment);

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

    public void setDataRetievalSteps(LinkedList<DataRetrievalStep> linkedSteps) {
        this.linkedSteps = linkedSteps;
    }
}
