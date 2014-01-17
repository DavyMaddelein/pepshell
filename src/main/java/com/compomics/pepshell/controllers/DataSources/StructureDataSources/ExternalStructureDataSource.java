package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Protein;
import java.util.List;

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

    public boolean isAbleToGetSolventAccessability() {
        return false;
    }

    public double getRelativeSolventAccessabilityForResidue(String proteinAccession, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getFreeEnergyForResidue(Protein protein, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getRelativeSolventAccessabilityForResidue(Protein protein, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getSecondaryStructureForResidue(Protein protein, int location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
