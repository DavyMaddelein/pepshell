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
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.controllers.dataimport.filehandlers.FileParserFactory;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.objectcontrollers.ProteinController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.FileBasedExperiment;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Observer;

/**
 *
 * @author Davy Maddelein
 * @param <T> the type of experiment
 */
public class DataRetrievalForFasta<T extends Experiment> extends AbstractDataRetrieval<T> implements Observer {

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
    public void addOnlyExperimentProteinsFromFasta(boolean addOnlyExperimentProteins) {
        addFastaAfterParsing = addOnlyExperimentProteins;
    }

    /**
     * @param experiment the experiment we need to retrieve data for
     * @return the passed experiment
     * @InheritDoc
     */
    @Override
    public T retrievePrimaryData(T experiment) {
        try {
            if (DataModeController.getInstance().getDataSource() == DataSourceEnum.DATABASE) {
                experiment.addProteins(DbDAO.fetchProteins(experiment));
                DbDAO.addPeptideGroupsToProteins(experiment.getProteins());
                checkAndAddQuantToProteinsInExperiment(experiment);
                
                
            } else if (DataModeController.getInstance().getDataSource() == DataSourceEnum.FILE) {
                if (!addFastaAfterParsing) {
                    FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, experiment);
                }
                FileParserFactory.getInstance().parseExperimentFile((FileBasedExperiment) experiment);
            }
            FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, experiment.getProteins());
            ProteinController.alignPeptidesOfProteinsInExperiment(experiment);
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
    protected boolean checkAndAddQuantToProteinsInExperiment(T anExperiment) {
        PreparedStatement stat = null;
        for (PepshellProtein aPepshellProtein : anExperiment.getProteins()) {
            try {
                stat = DbConnectionController.getExperimentDbConnection().prepareStatement(
                        DataModeController.getInstance()
                        .getDb()
                        .getDataMode()
                        .getExperimentDatabase()
                        .selectAllQuantedPeptideGroups());

                stat.setInt(1, anExperiment.getExperimentId());
                stat.setString(2, aPepshellProtein.getOriginalAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    while (rs.next()) {

                    }
                } catch (SQLException sqle) {
                    FaultBarrier.getInstance().handleException(sqle);
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        }
        return false;
    }

    @Override
    protected void retrieveSecondaryData(T experiment) {
        try {
            new runRetrievalSteps(experiment, getDataRetrievalSteps(), this).execute();
        } catch (Exception ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
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
                try {
                    double currentRatio = ((QuantedPeptide) aPeptide).getRatio();
                    //no else if just in case someone loads an experiment with just one peptide and for getting a max and a min on first pass

                    if (currentRatio > experiment.getMaxRatio()) {
                        experiment.setMaxRatio(currentRatio);
                    }

                    if (currentRatio < experiment.getMinRatio()) {
                        experiment.setMinRatio(currentRatio);
                    }
                } catch (CalculationException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            }
        })));
        }
    }
