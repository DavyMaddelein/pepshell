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

package com.compomics.pepshell.controllers.CachesAndStores;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.AbstractProteinStoreStrategy;
import com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.StoreStrategy;
import com.compomics.pepshell.controllers.properties.ProgramProperties;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.ProteinInterface;

import java.util.Collection;

/**
 * Created by Davy Maddelein on 15/02/2015.
 */
public class ProteinStoreManager {

    /**
     * the protein storage strategy to use for retrieval
     */
    private StoreStrategy<String, ProteinInterface> currentStoreStrategy;

    /**
     * tries to load in the default defined storage strategy from the {@link com.compomics.pepshell.controllers.properties.ProgramProperties}, if this fails
     * defaults to the naive in memory {@link com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.AbstractProteinStoreStrategy} implementation
     */
    public ProteinStoreManager() {
        String storageStrategyClassName = ProgramProperties.getInstance().getProperty("default.protein.storage.strategy");
        if (storageStrategyClassName != null) {
            try {
                ClassLoader.getSystemClassLoader().loadClass(storageStrategyClassName);
            } catch (ClassNotFoundException e) {
                FaultBarrier.getInstance().handleException(e);
                currentStoreStrategy = new AbstractProteinStoreStrategy();
            }
        }
    }

    /**
     * creates a new ProteinStoreManager backed by the {@link com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.StoreStrategy} passed along to the constructor
     *
     * @param aStorageStrategy the storage strategy to use
     */
    public ProteinStoreManager(StoreStrategy<String, ProteinInterface> aStorageStrategy) {
        currentStoreStrategy = aStorageStrategy;
    }

    /**
     * retrieves the protein identified by the given accession from the underlying {@link com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.StoreStrategy}
     *
     * @param accession the protein accession that identifies the {@link com.compomics.pepshell.model.ProteinInterface} object for retrieval
     * @return the requested {@link com.compomics.pepshell.model.ProteinInterface}
     */
    public ProteinInterface retrieveProteinByAccession(String accession) {
        return currentStoreStrategy.retrieve(accession);
    }


    /**
     * add the passed protein to the underlying storage manager
     *
     * @param protein the {@link com.compomics.pepshell.model.ProteinInterface} to store
     */
    public void addAProteinToStore(Protein protein) {
        currentStoreStrategy.accept(protein);
    }

    /**
     * adds a {@link java.util.Collection} of {@link com.compomics.pepshell.model.ProteinInterface} objects to the underlying {@link com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.StoreStrategy}
     *
     * @param proteins the {link ProteinInterface} objects to add
     */
    public void addProteinsToStore(Collection<Protein> proteins) {
        currentStoreStrategy.accept(proteins);
    }

    public void switchStorageStrategies(StoreStrategy<? extends Object, ProteinInterface> newStorageStrategy) {
        newStorageStrategy.accept(currentStoreStrategy.retrieveAll());
    }

}
