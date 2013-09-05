/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.controllers.ViewPreparation;

import com.compomics.peppi.controllers.DataModes.AbstractDataMode;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class PreparationForOfflineData extends ViewPreparation {

    @Override
    public Set<Protein> PrepareProteinsForJList(Project referenceProject, Set<Project> ProjectsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //add quant parsers later?
    protected boolean checkAndAddQuantToProteinsInProject(Project aProject) {
        return false;
    }

    @Override
    public void addProteinsToProject(AbstractDataMode dataMode) {
    }
}
