package com.compomics.pepshell.controllers.DataSources;

import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Davy
 */
public interface StructureDataSource extends AbstractDataSource {

    public StructureDataSource getInstance();

    public List<Domain> getDomainData(Protein aProtein) throws DataRetrievalException;

    public String getPDBDataForPDBName(String pdbAccession);

    public List<InteractionPartner> getInteractionPartners(Protein aProtein);

    public List<InteractionPartner> getInteractionPartnersForRange(Protein aProtein, int start, int stop);

    //TODO think of a cleaner way to handle below
    public boolean isAbleToGetFreeEnergy();

    /**
     * Get the free energy for a given PDB structure. Returns a map (key: fasta
     * residu location; value: free energy value).
     *
     * @param protein the given protein
     * @param psbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    public Map<Integer, Double> getFreeEnergyForStructure(Protein protein, String psbAccession);

    /**
     * Get the relative solvent accessibility for a given PDB structure. Returns
     * a map (key: fasta residu location; value: rel. solvent acc. value).
     *
     * @param protein the given protein
     * @param psbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(Protein protein, String psbAccession);

    public boolean isAbleToGetSolventAccessibility();

    public Map<Integer, String> getSecondaryStructureForStructure(Protein protein, String pdbAccession);

    public List<InteractionPartner> getInteractionPartnersForPDBName(String string);

    public Set<PdbInfo> getPdbInforForProtein(Protein protein, Comparator<PdbInfo> sortingComparator);

    public boolean isAbleTogetSecondaryStructure();

}
