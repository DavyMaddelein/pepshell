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
import com.compomics.pepshell.controllers.dataimport.DbConnectionController;
import com.compomics.pepshell.model.DataModes.DataRetrievalStep;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.QuantedExperiment;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.databases.DbDAOInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Davy Maddelein
 */
public class PreparationForDbData<T extends Experiment> implements AbstractDataRetrieval<T> {

    DbDAOInterface dbDAO;

    public PreparationForDbData(DbDAOInterface aDbDAO){
        dbDAO = aDbDAO;
    }

    @Override
    public T retrieveData(T referenceExperiment) {
        retrievePrimaryData(referenceExperiment);
        retrieveSecondaryData(referenceExperiment);
        return referenceExperiment;
    }

    @Override
    public T retrievePrimaryData(T experiment) {
        try {
            if (dbDAO.projectHasQuant(experiment, DbConnectionController.getExperimentDbConnection())){
                experiment = (T) dbDAO.fetchPeptidesAndProteins(new QuantedExperiment(experiment),DbConnectionController.getExperimentDbConnection());
            } else {
                dbDAO.fetchPeptidesAndProteins(experiment,DbConnectionController.getExperimentDbConnection());
            }
            setIntensityValuesForExperiment(experiment);
        } catch (SQLException | IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return experiment;
    }

    @Override
    public void retrieveSecondaryData(T experiment) {

    }

    @Override
    public void retrieveSecondaryData(T experiment, Observer observerToNotify) {

    }

    @Override
    public void setDataRetrievalSteps(LinkedList<DataRetrievalStep> retrievalStepsToSet) {

    }

    @Override
    public Collection<DataRetrievalStep> getDataRetrievalSteps() {
        return null;
    }


    //clear sign that the db dao needs to be redone
    private void setIntensityValuesForExperiment(T experiment) {
        experiment.getProteins().stream().forEach((aProtein) -> aProtein.getPeptideGroups().stream().forEach((aPeptideGroup) -> aPeptideGroup.getPeptideList().stream().forEach(aPeptide -> {
                    List<Double> currentIntensities = aPeptide.getTotalSpectrumIntensities();
                    //intensities
                    currentIntensities.stream().forEach(currentIntensity -> {
                        if (currentIntensity < experiment.getMinIntensity() || experiment.getMinIntensity() == 0.0) {
                            experiment.setMinIntensity(currentIntensity);
                        }
                        //no else if just in case someone loads an experiment with just one peptide and for getting a max and a min on first pass
                        if (currentIntensity > experiment.getMaxIntensity() || experiment.getMaxIntensity() == 0.0) {
                            experiment.setMaxIntensity(currentIntensity);
                        }
                    });

                    //ratios
                    if (aPeptide instanceof QuantedPeptide) {

                        double currentRatio = ((QuantedPeptide) aPeptide).getRatio();
                        //no else if just in case someone loads an experiment with just one peptide and for getting a max and a min on first pass

                        if (currentRatio > experiment.getMaxRatio()) {
                            experiment.setMaxRatio(currentRatio);
                        }

                        if (currentRatio < experiment.getMinRatio()) {
                            experiment.setMinRatio(currentRatio);
                        }
                    }
                }
        )));
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
