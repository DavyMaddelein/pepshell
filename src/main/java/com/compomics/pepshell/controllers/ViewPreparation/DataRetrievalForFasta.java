package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.ProgressMessage;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Davy
 * @param <T>
 * @param <V>
 */
public class DataRetrievalForFasta<T extends Experiment, V extends Protein> extends ViewPreparation<T, V> implements Observer {

    //TODO: this entire thing needs cleaning up
    private File fastaFile;

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
    }

    @Override
    public T retrieveData(T referenceExperiment, Iterator<T> experimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                referenceExperiment.addProteins(DbDAO.fetchProteins(referenceExperiment));
                retrieveSecondaryData(referenceExperiment);
                DbDAO.addPeptideGroupsToProteins(referenceExperiment.getProteins());
                FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceExperiment.getProteins());
                checkAndAddQuantToProteinsInExperiment(referenceExperiment);
                //take a look at this, this might be funky
                FastaDAO.setProjectProteinsToFastaFileProteins(fastaFile, referenceExperiment);
            }
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                while (experimentsToCompareWith.hasNext()) {
                    T anExperimentToCompareWith = experimentsToCompareWith.next();
                    DbDAO.fetchProteins(anExperimentToCompareWith);
                    retrieveSecondaryData(anExperimentToCompareWith);
                    DbDAO.addPeptideGroupsToProteins(anExperimentToCompareWith.getProteins());
                    FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, anExperimentToCompareWith.getProteins());
                    checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);

                }
            } else {
                while (experimentsToCompareWith.hasNext()) {
                    //something something CP-DT files
                    T aProjectToCompareWith = experimentsToCompareWith.next();
                    retrieveSecondaryData(referenceExperiment);
                }
            }
        } catch (FastaCouldNotBeReadException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (FileNotFoundException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (SQLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return referenceExperiment;
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(T anExperiment) {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups());
            stat.setInt(1, anExperiment.getExperimentId());
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    Protein tempProtein = new Protein(rs.getString("accession"));
                    if (anExperiment.getProteins().contains(tempProtein)){
                        anExperiment.getProteins().get(anExperiment.getProteins().indexOf(tempProtein));
                    }
                }
            } catch (SQLException sqle) {
            }

        } catch (SQLException sqle) {

        }
        return true;
    }

    @Override
    public void addProteinsToExperiment(AbstractDataMode dataMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void retrieveSecondaryData(T experiment) {
//TODO add preliminary checks to see if the server is accessible, if not, skip and warn user       
        List<List<Protein>> partitionedProteinList = Lists.partition(experiment.getProteins(), Runtime.getRuntime().availableProcessors());
        for (DataRetrievalStep aStep : linkedSteps) {
            for (List<Protein> aProteinList : partitionedProteinList) {
                DataRetrievalStep toExecute = aStep.getInstance(aProteinList);
                toExecute.getNotifier().addObserver(this);
                executor.submit(toExecute);
            }
        }
        progressMonitor.setNote("cleaning up");
        progressMonitor.setProgress(progressMonitor.getMaximum());
    }

    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof ProgressMessage) {
                progressMonitor.setNote(((ProgressMessage) arg).getTaskName());
                progressMonitor.setProgress((int) Math.floor(((ProgressMessage) arg).getProgressPercentage() / Runtime.getRuntime().availableProcessors()));
            }
        }
    }
}
