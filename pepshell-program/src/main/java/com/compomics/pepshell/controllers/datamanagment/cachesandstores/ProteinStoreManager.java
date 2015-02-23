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

package com.compomics.pepshell.controllers.datamanagment.cachesandstores;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.datamanagment.cachesandstores.stores.AbstractProteinStoreStrategy;
import com.compomics.pepshell.controllers.datamanagment.cachesandstores.stores.StoreStrategy;
import com.compomics.pepshell.controllers.properties.ProgramProperties;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.util.Collection;

/**
 * this class is the storage manager for proteins in the pepshell program, it manages all writes and accesses to the storage strategy pepshell is using at that moment
 * it does not guarantee that the requested item is in the underlying storage
 * Created by Davy Maddelein on 15/02/2015.
 */
public class ProteinStoreManager implements StoreManagerInterface<Object, PepshellProtein> {

    /**
     * the protein storage strategy to use for retrieval
     */
    private StoreStrategy<Object, PepshellProtein> currentStoreStrategy;

    /**
     * the singleton instance
     */
    private static ProteinStoreManager manager;

    /**
     * tries to load in the default defined storage strategy from the {@link com.compomics.pepshell.controllers.properties.ProgramProperties}, if this fails
     * defaults to the naive in memory {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.stores.AbstractProteinStoreStrategy} implementation
     */
    private ProteinStoreManager() {
        String storageStrategyClassName = ProgramProperties.getInstance().getProperty("default.protein.storage.strategy");
        if (storageStrategyClassName != null) {
            try {
                ClassLoader.getSystemClassLoader().loadClass(storageStrategyClassName);
            } catch (ClassNotFoundException e) {
                FaultBarrier.getInstance().handleException(e);
                currentStoreStrategy = new AbstractProteinStoreStrategy<>();
            }
        }
        manager = this;
    }

    /**
     * getter for the ProteinStoreManager
     * @return the ProteinStoreManager
     */
    public static ProteinStoreManager getInstance() {
        if (manager == null) {
            new ProteinStoreManager();
        }
        return manager;
    }

    /**
     * retrieves the {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} identified by the given accession from the underlying {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.stores.StoreStrategy}
     *
     * @param accession the accession that identifies the {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} for retrieval
     * @return the requested {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein}
     */
    @Override
    public PepshellProtein retrieveFromStore(Object accession) {
        return currentStoreStrategy.retrieve(accession);
    }

    /**
     * retrieves mutiple {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} from the store identified by the passed collection of identifying accessions
     *
     * @param accessions the accessions to retrieve the entries for
     * @return a {@link java.util.Collection} of the requested entries
     */
    @Override
    public Collection<? extends PepshellProtein> retrieveMultipleFromStore(Collection<Object> accessions) {
        return currentStoreStrategy.retrieveSubSet(accessions);
    }


    /**
     * add the passed {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} to the underlying storage manager
     *
     * @param entry the {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} to store
     */
    @Override
    public void addToStore(PepshellProtein entry) {
        currentStoreStrategy.accept(entry);
    }

    /**
     * adds a collection of {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} to the underlying storage manager
     *
     * @param entryCollection a collection of {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein} to add
     */
    @Override
    public void addMultipleToStore(Collection<? extends PepshellProtein> entryCollection) {
        currentStoreStrategy.accept(entryCollection);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchStorageStrategies(StoreStrategy<Object, PepshellProtein> newStorageStrategy) {
        newStorageStrategy.accept(currentStoreStrategy.retrieveAll());
        this.currentStoreStrategy = newStorageStrategy;

    }
}
