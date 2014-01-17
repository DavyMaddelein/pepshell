package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.QuantedExperiment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author Davy
 */
public class PreparationForDbData<T extends Experiment> extends ViewPreparation<T> {

    FaultBarrier barrier = FaultBarrier.getInstance();

    @Override
    public T PrepareProteinsForJList(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            DbDAO.fetchPeptidesAndProteins(referenceExperiment);
            checkAndAddQuantToProteinsInExperiment(referenceExperiment);
            while (ExperimentsToCompareWith.hasNext()) {
                T anExperimentToCompareWith = ExperimentsToCompareWith.next();
                DbDAO.fetchPeptidesAndProteins(anExperimentToCompareWith);
                checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
                checkOverlappingPeptides(referenceExperiment, anExperimentToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
            }
        } catch (SQLException ex) {
            barrier.handleException(ex);
        } catch (IOException ex) {
            barrier.handleException(ex);
        }
        return referenceExperiment;
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(Experiment anExperiment) {
        boolean dataAdded = false;
        try {
            if (DbDAO.projectHasQuant(anExperiment)) {
                anExperiment = new QuantedExperiment(anExperiment);
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
