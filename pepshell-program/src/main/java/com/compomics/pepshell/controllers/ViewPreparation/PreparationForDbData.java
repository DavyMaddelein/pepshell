/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.QuantedExperiment;
import java.io.IOException;
import java.sql.SQLException;

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
