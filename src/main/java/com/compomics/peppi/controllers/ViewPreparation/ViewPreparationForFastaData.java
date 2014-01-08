package com.compomics.peppi.controllers.ViewPreparation;

import com.compomics.peppi.DataModeController;
import com.compomics.peppi.controllers.DAO.DbDAO;
import com.compomics.peppi.controllers.DAO.FastaDAO;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public Set<Protein> PrepareProteinsForJList(Project referenceProject, List<Project> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        Set<Protein> returnset = new HashSet<Protein>();
        try {
            returnset = PrepareProteinsForJList(referenceProject, ProjectsToCompareWith, removeNonOverlappingPeptidesFromReferenceProject, fastaFile); //To change body of generated methods, choose Tools | Templates.
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

    public Set<Protein> PrepareProteinsForJList(Project referenceProject, List<Project> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject, File fastaFile) throws FastaCouldNotBeReadException, FileNotFoundException, IOException, SQLException, URISyntaxException {
        if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
            DbDAO.fetchPeptidesAndProteins(referenceProject);
            FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceProject);
        } else {
            FastaDAO.setProjectProteinsToFastaFileProteins(fastaFile, referenceProject);
        }
        if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
            for (Project aProjectToCompareWith : ProjectsToCompareWith) {
                DbDAO.fetchPeptidesAndProteins(aProjectToCompareWith);
                checkAndAddQuantToProteinsInProject(aProjectToCompareWith);
                //(referenceProject, aProjectToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
            }
        } else {
            for (Project aProjectToCompareWith : ProjectsToCompareWith) {
            }
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return referenceProject.getProteins();
    }
}
