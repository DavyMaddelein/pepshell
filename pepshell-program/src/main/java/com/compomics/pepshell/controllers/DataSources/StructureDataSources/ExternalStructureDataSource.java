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

package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.model.*;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public class ExternalStructureDataSource<T extends Protein> implements StructureDataSource<T> {

    /**
     * @return
     */
    @Override
    public StructureDataSource getInstance() {
        return null;
    }

    /**
     * @param aProtein
     * @return
     * @throws com.compomics.pepshell.model.exceptions.DataRetrievalException
     */
    @Override
    public List<Domain> getDomainData(T aProtein) throws DataRetrievalException {
        return null;
    }

    /**
     * @param pdbAccession
     * @return
     */
    @Override
    public String getPDBDataForPDBName(String pdbAccession) {
        return null;
    }

    /**
     * @param aProtein
     * @return
     */
    @Override
    public List<InteractionPartner> getInteractionPartners(T aProtein) {
        return null;
    }

    /**
     * @param aProtein
     * @param start
     * @param stop
     * @return
     */
    @Override
    public List<InteractionPartner> getInteractionPartnersForRange(T aProtein, int start, int stop) {
        return null;
    }

    @Override
    public boolean isAbleToGetFreeEnergy() {
        return false;
    }

    /**
     * Get the free energy for a given PDB structure. Returns a map (key: fasta
     * residue location; value: free energy value).
     *
     * @param protein      the given protein
     * @param pdbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    @Override
    public Map<Integer, Double> getFreeEnergyForStructure(T protein, PdbInfo pdbAccession) {
        return null;
    }

    /**
     * Get the relative solvent accessibility for a given PDB structure. Returns
     * a map (key: fasta residue location; value: rel. solvent acc. value).
     *
     * @param protein      the given protein
     * @param psbAccession the PDB accession
     * @return the rel. solvent acc. map
     */
    @Override
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(T protein, String psbAccession) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isAbleToGetSolventAccessibility() {
        return false;
    }

    /**
     * @param protein
     * @param pdbAccession
     * @return
     */
    @Override
    public Map<Integer, String> getSecondaryStructureForStructure(T protein, String pdbAccession) {
        return null;
    }

    /**
     * @param string
     */
    @Override
    public List<InteractionPartner> getInteractionPartnersForPDBName(String string) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isAbleTogetSecondaryStructure() {
        return false;
    }

    @Override
    public Set<PdbInfo> getPdbInforForProtein(T protein) {
        return null;
    }
}
