package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Protein;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class ExternalStructureDataSource implements StructureDataSource {

    public List<Domain> getDomainData(Protein aProtein) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPDBDataForPDBName(String pdbAccession) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isAbleToGetFreeEnergy() {
        return false;
    }

    public double getFreeEnergyForResidue(String proteinAccession, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isAbleToGetSolventAccessibility() {
        return false;
    }

    public double getRelativeSolventAccessibilityForResidue(String proteinAccession, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Integer, Double> getFreeEnergyForStructure(Protein protein, String psbAccession) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }       

    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(Protein protein, String structureName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getSecondaryStructureForResidue(Protein protein, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List getInteractionPartners(Protein aProtein) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List getInteractionPartnersForRange(Protein aProtein, int start, int stop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List getInteractionPartnersForPDBName(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public StructureDataSource getInstance() {
        return new ExternalStructureDataSource();
    }

}
