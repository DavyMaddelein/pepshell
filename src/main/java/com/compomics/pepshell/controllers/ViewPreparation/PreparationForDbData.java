package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedProject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Davy
 */
public class PreparationForDbData extends ViewPreparation {

    FaultBarrier barrier = FaultBarrier.getInstance();

    @Override
    public List<Protein> PrepareProteinsForJList(Experiment referenceProject, List<Experiment> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            DbDAO.fetchPeptidesAndProteins(referenceProject);
            checkAndAddQuantToProteinsInExperiment(referenceProject);
            for (Experiment anExperimentToCompareWith : ProjectsToCompareWith) {
                DbDAO.fetchPeptidesAndProteins(anExperimentToCompareWith);
                checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
                checkOverlappingPeptides(referenceProject, anExperimentToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
            }
        } catch (SQLException ex) {
            barrier.handleException(ex);
        } catch (IOException ex) {
            barrier.handleException(ex);
        }
        return referenceProject.getProteins();
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(Experiment anExperiment) {
        boolean dataAdded = false;
        try {
            if (DbDAO.projectHasQuant(anExperiment)) {
                anExperiment = new QuantedProject(anExperiment);
            }
            if (DbDAO.fetchPeptidesAndProteins(anExperiment)) {
                dataAdded = true;
            }
        } catch (SQLException ex) {
            barrier.handleException(ex);
        } catch (IOException ex) {
            barrier.handleException(ex);
        }
        return dataAdded;
    }

    @Override
    public void addProteinsToExperiment(AbstractDataMode dataMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
