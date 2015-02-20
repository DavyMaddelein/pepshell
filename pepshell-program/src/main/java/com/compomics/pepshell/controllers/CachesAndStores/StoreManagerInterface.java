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

import com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.StoreStrategy;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.util.Collection;

/**
 * the general contract that an information storage manager must fulfill in the Pepshell program
 * Created by Davy Maddelein on 18/02/2015.
 */
public interface StoreManagerInterface<T, U> {

    /**
     * retrieves the entry identified by the given accession from the underlying {@link com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies.StoreStrategy}
     *
     * @param accession the entry accession that identifies the entry for retrieval
     * @return the requested object
     */
    U retrieveFromStore(T accession);

    /**
     * retrieves mutiple entries from the store identified by the passed collection of identifying accessions
     *
     * @param accessions the accessions to retrieve the entries for
     * @return a {@link java.util.Collection} of the requested entries
     */
    Collection<? extends U> retrieveMultipleFromStore(Collection<T> accessions);

    /**
     * add the passed entry to the underlying storage manager
     *
     * @param entry the entry to store
     */
    void addToStore(U entry);

    /**
     * adds a collection of entries to the underlying storage manager
     *
     * @param entryCollection the entries to add to the storage manager
     */
    void addMultipleToStore(Collection<? extends U> entryCollection);

    /**
     * switches out the current storage strategy with the one passed to this method, depending on the implementations of both strategies, this might take a while and if needed should be called in a separate thread
     *
     * @param newStorageStrategy the storage strategy to replace the current one with
     */
    void switchStorageStrategies(StoreStrategy<T, U> newStorageStrategy);
}
