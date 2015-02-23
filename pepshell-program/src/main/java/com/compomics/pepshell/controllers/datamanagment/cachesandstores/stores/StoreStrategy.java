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

package com.compomics.pepshell.controllers.datamanagment.cachesandstores.stores;

import java.util.Collection;

/**
 * Created by Davy Maddelein on 15/02/2015.
 */

/**
 * an interface to define the basic functions of a store strategy
 *
 * @param <T> the object type to be used as an identifier for retrieval
 * @param <U> the object type to store in the underlying data structure
 */
public interface StoreStrategy<T, U> {

    /**
     * tries to accept a single entry into the underlying data structure
     *
     * @param anEntry the entry to be passed to the underlying data structure
     * @return true if successfully stored, false otherwise
     */
    public boolean accept(U anEntry);

    /**
     * tries to accept a collection of entries into the underlying data structure, there are no guarantees about the state of the store if an exception happens while storing
     *
     * @param entries the collection of entries
     * @return
     */
    public boolean accept(Collection<? extends U> entries);

    /**
     * tries to retrieve an entry from the store with the given identifier
     *
     * @param identifier the identifier to retrieve the entry with
     * @return the entry from the store
     */
    public U retrieve(T identifier);

    /**
     * retrieves all entries from the store
     *
     * @return a {@link java.util.Collection} filled with all the entries from the store
     */
    public Collection<? extends U> retrieveAll();

    /**
     * retrieves a subset of entries from the store identified by members of the passed collection
     *
     * @param identifiers the identifiers to retrieve the entries for
     * @return a {@link java.util.Collection} filled with all the entries requested
     */
    public Collection<? extends U> retrieveSubSet(Collection<? extends T> identifiers);

    /**
     * removes a single entry from the store identified by the passed identifier
     *
     * @param identifierToPurgeFromStore the identifier to pure the entry for
     * @return true if successfully purged, false otherwise
     */
    public boolean purge(T identifierToPurgeFromStore);

    /**
     * removes a subset of entries from the store identified by members of the passed collection
     *
     * @param identifiersToPurgeFromStore the identifiers to purge the entries for
     * @return true if successfully purged, false otherwise
     */
    public boolean purgeSubset(Collection<? extends T> identifiersToPurgeFromStore);

    /**
     * removes all entries from the store
     *
     * @return true if successfully purged, false otherwise
     */
    public boolean purgeAll();
}
