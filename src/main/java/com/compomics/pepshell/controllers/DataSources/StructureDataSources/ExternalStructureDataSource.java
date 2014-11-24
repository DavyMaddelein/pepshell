package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public class ExternalStructureDataSource implements StructureDataSource {

    @Override
    public StructureDataSource getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Domain> getDomainData(ProteinInterface aProtein) throws DataRetrievalException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPDBDataForPDBName(String pdbAccession) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InteractionPartner> getInteractionPartners(ProteinInterface aProtein) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InteractionPartner> getInteractionPartnersForRange(ProteinInterface aProtein, int start, int stop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAbleToGetFreeEnergy() {
        return false;
    }

    @Override
    public Map<Integer, Double> getFreeEnergyForStructure(ProteinInterface protein, PdbInfo pdbAccession) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(ProteinInterface protein, String psbAccession) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAbleToGetSolventAccessibility() {
        return false;
    }

    @Override
    public Map<Integer, String> getSecondaryStructureForStructure(ProteinInterface protein, String pdbAccession) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InteractionPartner> getInteractionPartnersForPDBName(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<PdbInfo> getPdbInforForProtein(ProteinInterface protein, Comparator<PdbInfo> sortingComparator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAbleTogetSecondaryStructure() {
        return false;
    }

  
}
