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
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * if the experiment should only contain the proteins from the experiments or every protein in the fasta
     * @param addOnlyExperimentProteins boolean indicating if we only want proteins of interest
     */
    public void addOnlyExperimentProteinsFromFasta(boolean addOnlyExperimentProteins){
        addFastaAfterParsing = addOnlyExperimentProteins;
    }

    /**
     * @InheritDoc 
     */
    @Override
    public T retrievePrimaryData(T referenceExperiment) {
        try {
            if (DataModeController.getInstance().getDataSource() == DataSourceEnum.DATABASE) {
                referenceExperiment.addProteins(DbDAO.fetchProteins(referenceExperiment));
                DbDAO.addPeptideGroupsToProteins(referenceExperiment.getProteins());
                setIntensityValuesForExperiment(referenceExperiment);
                checkAndAddQuantToProteinsInExperiment(referenceExperiment);
            } else if (DataModeController.getInstance().getDataSource() == DataSourceEnum.FILE) {
                if (!addFastaAfterParsing) {
                    FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, referenceExperiment);
                }
                FileParserFactory.getInstance().parseExperimentFile((FileBasedExperiment) referenceExperiment);
            }
            FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceExperiment.getProteins());
            ProteinController.alignPeptidesOfProteinsInExperiment(referenceExperiment);
            retrieveSecondaryData(referenceExperiment);

//            while (experimentsToCompareWith.hasNext()) {
//                T anExperimentToCompareWith = experimentsToCompareWith.next();
//                if (DataModeController.getInstance().getDataSource() == DataSourceEnum.DATABASE) {
//                    anExperimentToCompareWith.addProteins(DbDAO.fetchProteins(anExperimentToCompareWith));
//                    FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, anExperimentToCompareWith.getProteins());
//                    DbDAO.addPeptideGroupsToProteins(anExperimentToCompareWith.getProteins());
//                    setIntensityValuesForExperiment(anExperimentToCompareWith);
//                } else {
//                    //something something peptide protein file
//                }
//                try {
//                    retrieveSecondaryData(anExperimentToCompareWith);
//                    // error handling has to be better
//                } catch (Exception e) {
//                    FaultBarrier.getInstance().handleException(e);
//                }
//                checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
//            }
        } catch (FastaCouldNotBeReadException ex) {
            FaultBarrier.getInstance().handleException(ex, false);
        } catch (IOException | SQLException | CalculationException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (CouldNotParseException ex) {
            Logger.getLogger(DataRetrievalForFasta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referenceExperiment;
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(T anExperiment) {
        PreparedStatement stat = null;
        for (Protein aProtein : anExperiment.getProteins()) {
            try {
                stat = DbConnectionController.getExperimentDbConnection().prepareStatement(
                        DataModeController.getInstance()
                        .getDb()
                        .getDataMode()
                        .getExperimentDatabase()
                        .selectAllQuantedPeptideGroups());

                stat.setInt(1, anExperiment.getExperimentId());
                stat.setString(2, aProtein.getOriginalAccession());
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
    private void setIntensityValuesForExperiment(T experiment) throws CalculationException {
        for (Protein aProtein : experiment.getProteins()) {
            for (PeptideGroup aPeptideGroup : aProtein.getPeptideGroups()) {
                for (Peptide aPeptide : aPeptideGroup.getPeptideList()) {
                    double currentIntensity = aPeptide.getTotalSpectrumIntensity();
                    //intensities
                    if (currentIntensity < experiment.getMinIntensity() || experiment.getMinIntensity() == 0.0) {
                        experiment.setMinIntensity(currentIntensity);
                    }
                    //no else if just in case someone loads an experiment with just one peptide and for getting a max and a min on first pass
                    if (currentIntensity > experiment.getMaxIntensity() || experiment.getMaxIntensity() == 0.0) {
                        experiment.setMaxIntensity(currentIntensity);
                    }
                    //ratios
                    if (aPeptide instanceof QuantedPeptide) {
                        Double currentRatio = ((QuantedPeptide) aPeptide).getRatio();
                        //no else if just in case someone loads an experiment with just one peptide and for getting a max and a min on first pass
                        if (currentRatio != null) {
                            if (experiment.getMaxRatio() == null || currentRatio > experiment.getMaxRatio()) {
                                experiment.setMaxRatio(currentRatio);
                            }
                        }
                    }
                }
            }
        }
    }
}
