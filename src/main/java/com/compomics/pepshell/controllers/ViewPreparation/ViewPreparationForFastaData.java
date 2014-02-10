package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davy
 * @param <T>
 */
public class ViewPreparationForFastaData<T extends Experiment> extends ViewPreparation<T> {

    //TODO: this entire thing needs cleaning up
    private File fastaFile;

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
    }

    @Override
    public T PrepareProteinsForJList(T referenceExperiment, Iterator<T> experimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                DbDAO.fetchProteins(referenceExperiment);
                if (!filterList.isEmpty()) {
                    // needs to change to a step in factory 
                    referenceExperiment.setProteins(filter.filter(referenceExperiment.getProteins(), filterList));
                }
                DbDAO.addPeptideGroupsToProteins(referenceExperiment.getProteins());
                FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceExperiment.getProteins());
            } else {
                FastaDAO.setProjectProteinsToFastaFileProteins(fastaFile, referenceExperiment);
            }
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                while (experimentsToCompareWith.hasNext()) {
                    T anExperimentToCompareWith = experimentsToCompareWith.next();
                    DbDAO.fetchPeptidesAndProteins(anExperimentToCompareWith);
                    FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, anExperimentToCompareWith.getProteins());
                    checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
                }
            } else {
                while (experimentsToCompareWith.hasNext()) {
                    //something something CP-DT files
                    T aProjectToCompareWith = experimentsToCompareWith.next();
                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (FastaCouldNotBeReadException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referenceExperiment;
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(T anExperiment) {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups());
            for (Protein protein : anExperiment.getProteins()) {
                stat.setInt(1, anExperiment.getExperimentId());
                stat.setString(1, protein.getProteinAccession());
                ResultSet rs = stat.executeQuery();
                try {
                    while (rs.next()) {

                    }
                } catch (SQLException sqle) {
                }
            }
        } catch (SQLException sqle) {

        }
        return true;
    }

    @Override
    public void addProteinsToExperiment(AbstractDataMode dataMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
