package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import java.util.List;

/**
 *
 * @author Davy
 */
public class PreparationForOfflineData extends ViewPreparation {

    @Override
    public List<Protein> PrepareProteinsForJList(Experiment referenceProject, List<Experiment> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //add quant parsers later?
    protected boolean checkAndAddQuantToProteinsInProject(Experiment aProject) {
        return false;
    }

    @Override
    public void addProteinsToExperiment(AbstractDataMode dataMode) {
        
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(Experiment anExperiment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}