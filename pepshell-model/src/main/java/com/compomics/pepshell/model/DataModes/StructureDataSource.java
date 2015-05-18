/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.model.DataModes;


import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.protein.ProteinInterface;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public interface StructureDataSource<T extends ProteinInterface> {

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
    public List<FeatureWithLocation> getDomainData(T aProtein) throws DataRetrievalException;

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
    public List<InteractionPartner> getInteractionPartners(T aProtein);

    /**
     *
     * @param aProtein
     * @param start
     * @param stop
     * @return
     */
    public List<InteractionPartner> getInteractionPartnersForRange(T aProtein, int start, int stop);

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
    public Map<Integer, Double> getFreeEnergyForStructure(T protein, PdbInfo pdbAccession);

    /**
     * Get the relative solvent accessibility for a given PDB structure. Returns
     * a map (key: fasta residue location; value: rel. solvent acc. value).
     *
     * @param protein the given protein
     * @param psbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(T protein, String psbAccession);

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
    public Map<Integer, String> getSecondaryStructureForStructure(T protein, String pdbAccession);

    /**
     *
     */
    public List<InteractionPartner> getInteractionPartnersForPDBName(String string);

    /**
     *
     * @return
     */
    public boolean isAbleTogetSecondaryStructure();

    public Set<PdbInfo> getPdbInforForProtein(T protein);
}
