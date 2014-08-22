package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.CPDTAnalysis;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davy
 * @param <T>
 * @param <V>
 */
public class DataRetrievalForFasta<T extends Experiment, V extends Protein> extends ViewPreparation<T, V> implements Observer {

    private File fastaFile;

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
    }

    @Override
    public T retrieveData(T referenceExperiment, Iterator<T> experimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                referenceExperiment.addProteins(DbDAO.fetchProteins(referenceExperiment));
                DbDAO.addPeptideGroupsToProteins(referenceExperiment.getProteins());
                setIntensityValuesForExperiment(referenceExperiment);
            } else {
                if (linkedSteps.indexOf(new CPDTAnalysis()) < 0) {
                    linkedSteps.add(new CPDTAnalysis());
                }
                FastaDAO.setProjectProteinsToFastaFileProteins(fastaFile, referenceExperiment);
            }
            FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceExperiment.getProteins());
            retrieveSecondaryData(referenceExperiment);
            checkAndAddQuantToProteinsInExperiment(referenceExperiment);

            while (experimentsToCompareWith.hasNext()) {
                T anExperimentToCompareWith = experimentsToCompareWith.next();
                if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                    anExperimentToCompareWith.addProteins(DbDAO.fetchProteins(anExperimentToCompareWith));
                    FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, anExperimentToCompareWith.getProteins());
                    DbDAO.addPeptideGroupsToProteins(anExperimentToCompareWith.getProteins());
                    setIntensityValuesForExperiment(anExperimentToCompareWith);
                } else {
                    //something something peptide protein file
                }
                try {
                    retrieveSecondaryData(anExperimentToCompareWith);
                    // error handling has to be better
                } catch (Exception e) {
                    FaultBarrier.getInstance().handleException(e);
                }
                checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
            }
        } catch (FastaCouldNotBeReadException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (FileNotFoundException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (IOException | SQLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (CalculationException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return referenceExperiment;
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(T anExperiment) {
        PreparedStatement stat = null;
        for (Protein aProtein : anExperiment.getProteins()) {
            try {
                stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups());
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
    public void addProteinsToExperiment(AbstractDataMode dataMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void retrieveSecondaryData(T experiment) {
        try {
            new WaitForFutures(experiment, this).execute();
        } catch (Exception ex) {
            Logger.getLogger(DataRetrievalForFasta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //clear sign that the db dao needs to be redone
    private void setIntensityValuesForExperiment(T experiment) throws CalculationException {
        for (Protein aProtein : experiment.getProteins()) {
            for (PeptideGroup aPeptideGroup : aProtein.getPeptideGroupsForProtein()) {
                for (Peptide aPeptide : aPeptideGroup.getPeptideList()) {
                    double currentIntensity = aPeptide.getTotalSpectrumIntensity();
                    if (currentIntensity < experiment.getMinIntensity() || experiment.getMinIntensity() == 0.0) {
                        experiment.setMinIntensity(currentIntensity);
                    }
                    //no else if just in case someone loads an experiment with just one peptide and for getting a max and a min on first pass
                    if (currentIntensity > experiment.getMaxIntensity() || experiment.getMaxIntensity() == 0.0) {
                        experiment.setMaxIntensity(currentIntensity);
                    }
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
