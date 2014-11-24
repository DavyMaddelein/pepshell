package com.compomics.pepshell.controllers.DataSources;

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
public interface StructureDataSource extends AbstractDataSource {

    /**
     *
     * @return
     */
    public StructureDataSource getInstance();

    /**
     *
     * @param aProtein
     * @return
     * @throws com.compomics.pepshell.model.exceptions.DataRetrievalException
     */
    public List<Domain> getDomainData(ProteinInterface aProtein) throws DataRetrievalException;

    /**
     *
     * @param pdbAccession
     * @return
     */
    public String getPDBDataForPDBName(String pdbAccession);

    /**
     *
     * @param aProtein
     * @return
     */
    public List<InteractionPartner> getInteractionPartners(ProteinInterface aProtein);

    /**
     *
     * @param aProtein
     * @param start
     * @param stop
     * @return
     */
    public List<InteractionPartner> getInteractionPartnersForRange(ProteinInterface aProtein, int start, int stop);

    //TODO think of a cleaner way to handle below
    public boolean isAbleToGetFreeEnergy();

    /**
     * Get the free energy for a given PDB structure. Returns a map (key: fasta
     * residue location; value: free energy value).
     *
     * @param protein the given protein
     * @param pdbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    public Map<Integer, Double> getFreeEnergyForStructure(ProteinInterface protein, PdbInfo pdbAccession);

    /**
     * Get the relative solvent accessibility for a given PDB structure. Returns
     * a map (key: fasta residue location; value: rel. solvent acc. value).
     *
     * @param protein the given protein
     * @param psbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(ProteinInterface protein, String psbAccession);

    /**
     *
     * @return 
     */
    public boolean isAbleToGetSolventAccessibility();

    /**
     *
     * @param protein
     * @param pdbAccession
     * @return
     */
    public Map<Integer, String> getSecondaryStructureForStructure(ProteinInterface protein, String pdbAccession);

    /**
     *
     */
    public List<InteractionPartner> getInteractionPartnersForPDBName(String string);

    /**
     *
     * @param protein
     * @param sortingComparator
     * @return
     */
    public Set<PdbInfo> getPdbInforForProtein(ProteinInterface protein, Comparator<PdbInfo> sortingComparator);

    /**
     *
     * @return
     */
    public boolean isAbleTogetSecondaryStructure();

}
