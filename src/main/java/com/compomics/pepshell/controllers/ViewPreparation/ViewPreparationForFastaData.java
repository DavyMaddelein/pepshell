package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davy
 */
public class ViewPreparationForFastaData extends PreparationForOfflineData {

    //TODO: this entire thing needs cleaning up
    private File fastaFile;

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
    }

    @Override
    public List<Protein> PrepareProteinsForJList(Experiment referenceProject, List<Experiment> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        List<Protein> returnset = new ArrayList<Protein>();
        try {
            returnset = PrepareProteinsForJList(referenceProject, ProjectsToCompareWith, removeNonOverlappingPeptidesFromReferenceProject, fastaFile);
        } catch (FastaCouldNotBeReadException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ViewPreparationForFastaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnset;
    }

    public List<Protein> PrepareProteinsForJList(Experiment referenceExperiment, List<Experiment> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject, File fastaFile) throws FastaCouldNotBeReadException, FileNotFoundException, IOException, SQLException, URISyntaxException {
        if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
            DbDAO.fetchPeptidesAndProteins(referenceExperiment);
            FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceExperiment);
        } else {
            FastaDAO.setProjectProteinsToFastaFileProteins(fastaFile, referenceExperiment);
        }
        if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
            for (Experiment anExperimentToCompareWith : ExperimentsToCompareWith) {
                DbDAO.fetchPeptidesAndProteins(anExperimentToCompareWith);
                FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, anExperimentToCompareWith);
                checkAndAddQuantToProteinsInProject(anExperimentToCompareWith);
                //(referenceExperiment, anExperimentToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
            }
        } else {
            for (Experiment aProjectToCompareWith : ExperimentsToCompareWith) {
            }
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return referenceExperiment.getProteins();
    }
}
