package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedExperiment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author Davy Maddelein
 */
public class PreparationForDbData<T extends Experiment> extends AbstractDataRetrieval<T> {

    @Override
    protected T retrievePrimaryData(T referenceExperiment) {
        try {
            DbDAO.fetchPeptidesAndProteins(referenceExperiment);
            checkAndAddQuantToProteinsInExperiment(referenceExperiment);
//            while (ExperimentsToCompareWith.hasNext()) {
//                T anExperimentToCompareWith = ExperimentsToCompareWith.next();
//                DbDAO.fetchPeptidesAndProteins(anExperimentToCompareWith);
//                checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
//                removeProteinsNotInReferenceExperiment(referenceExperiment);
//            }
        } catch (SQLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
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
            FaultBarrier.getInstance().handleException(ex);
        } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return dataAdded;
    }

    @Override
    protected void retrieveSecondaryData(T experiment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
