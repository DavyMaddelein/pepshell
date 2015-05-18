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

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.controllers.objectcontrollers.PeptideGroupUtilities;
import com.compomics.pepshell.model.DataModes.DataRetrievalStep;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.UpdateMessage;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.util.*;

/**
 *
 * @author Davy Maddelein
 * @param <T> the type of experiment
 */
public class DataRetrievalForFasta<T extends Experiment> extends Observable implements AbstractDataRetrieval<T> {

    private File fastaFile;
    private boolean addFastaAfterParsing = true;

    public DataRetrievalForFasta<T> setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
        return this;
    }

    /**
     * if the experiment should only contain the proteins from the experiments
     * or every protein in the fasta
     *
     * @param addOnlyExperimentProteins boolean indicating if we only want
     * proteins of interest
     */
    public DataRetrievalForFasta<T> addOnlyExperimentProteinsFromFasta(boolean addOnlyExperimentProteins) {
        addFastaAfterParsing = addOnlyExperimentProteins;
        return this;
    }

    @Override
    public T retrieveData(T referenceExperiment) {
        return AbstractDataRetrieval.super.retrieveData(referenceExperiment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T retrievePrimaryData(T experiment) {
        try {
            DataModeController.fetchDataToVisualize(experiment);
            if (!addFastaAfterParsing) {
                FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, experiment);
            }
            FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, experiment.getProteins());
            experiment.getProteins().forEach(PeptideGroupUtilities::mapPeptideGroupsToProtein);
            setIntensityValuesForExperiment(experiment);
            retrieveSecondaryData(experiment);
        } catch (FastaCouldNotBeReadException ex) {
            FaultBarrier.getInstance().handleException(ex, false);
        } catch (Exception ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return experiment;
    }

    @Override
    public void retrieveSecondaryData(T experiment) {
        AbstractDataRetrieval.super.retrieveSecondaryData(experiment);

    }

    @Override
    public void retrieveSecondaryData(T experiment, Observer observerToNotify) {
        this.addObserver(observerToNotify);
        this.retrieveSecondaryData(experiment);
    }

    @Override
    public void setDataRetrievalSteps(LinkedList<DataRetrievalStep> retrievalStepsToSet) {
        AbstractDataRetrieval.super.setDataRetrievalSteps(retrievalStepsToSet);
    }

    @Override
    public Collection<DataRetrievalStep> getDataRetrievalSteps() {
        return AbstractDataRetrieval.super.getDataRetrievalSteps();
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
        if (arg != null) {
            if (arg instanceof UpdateMessage) {
                //comes from lower, throw higher in the chain, update notification to user
                this.setChanged();
                progressMonitor.setNote(((UpdateMessage) arg).getMessage());
                this.notifyObservers(arg);
            }
        }
    }
}
