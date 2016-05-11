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

package com.compomics.pepshell.controllers.datasources.structuredatasources;

import com.compomics.pepshell.model.DataModes.StructureDataSource;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Davy Maddelein on 4/21/2016.
 */
public class NextProtStructureDataSource<T extends PepshellProtein> implements StructureDataSource<T> {

    private static NextProtStructureDataSource instance = new NextProtStructureDataSource();

    @Override
    public StructureDataSource getInstance() {
        return instance;
    }

    @Override
    public List<FeatureWithLocation> getDomainData(T aProtein) throws DataRetrievalException {
        return null;
    }

    @Override
    public String getPDBDataForPDBName(String pdbAccession) {
        return null;
    }

    @Override
    public List<InteractionPartner> getInteractionPartners(T aProtein) {
        return null;
    }

    @Override
    public List<InteractionPartner> getInteractionPartnersForRange(T aProtein, int start, int stop) {
        return null;
    }

    @Override
    public boolean isAbleToGetFreeEnergy() {
        return false;
    }

    @Override
    public Map<Integer, Double> getFreeEnergyForStructure(T protein, PdbInfo pdbAccession) {
        return null;
    }

    @Override
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(T protein, String psbAccession) {
        return null;
    }

    @Override
    public boolean isAbleToGetSolventAccessibility() {
        return false;
    }

    @Override
    public Map<Integer, String> getSecondaryStructureForStructure(T protein, String pdbAccession) {
        return null;
    }

    @Override
    public List<InteractionPartner> getInteractionPartnersForPDBName(String string) {
        return null;
    }

    @Override
    public boolean isAbleTogetSecondaryStructure() {
        return false;
    }

    @Override
    public Set<PdbInfo> getPdbInforForProtein(T protein) throws DataRetrievalException {
        return null;
    }
}
