package com.compomics.peppi.controllers.ViewPreparation;

import com.compomics.peppi.FaultBarrier;
import com.compomics.peppi.controllers.DAO.DbDAO;
import com.compomics.peppi.controllers.DataModes.AbstractDataMode;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.QuantedProject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class PreparationForDbData extends ViewPreparation {

    FaultBarrier barrier = FaultBarrier.getInstance();

    @Override
    public Set<Protein> PrepareProteinsForJList(Project referenceProject, List<Project> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            DbDAO.fetchPeptidesAndProteins(referenceProject);
            checkAndAddQuantToProteinsInProject(referenceProject);
            for (Project aProjectToCompareWith : ProjectsToCompareWith) {
                DbDAO.fetchPeptidesAndProteins(aProjectToCompareWith);
                checkAndAddQuantToProteinsInProject(aProjectToCompareWith);
                checkOverlappingPeptides(referenceProject, aProjectToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
            }
        } catch (SQLException ex) {
            barrier.handleException(ex);
        } catch (IOException ex) {
            barrier.handleException(ex);
        }
        return referenceProject.getProteins();
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInProject(Project aProject) {
        boolean dataAdded = false;
        try {
            if (DbDAO.projectHasQuant(aProject)) {
                aProject = new QuantedProject(aProject);
            }
            if (DbDAO.fetchPeptidesAndProteins(aProject)) {
                dataAdded = true;
            }
        } catch (SQLException ex) {
            barrier.handleException(ex);
        } catch (IOException ex) {
            barrier.handleException(ex);
        }
        return dataAdded;
    }

    @Override
    public void addProteinsToProject(AbstractDataMode dataMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
