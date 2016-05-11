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

import com.compomics.pepshell.model.protein.ProteinInterface;

import java.util.*;
import java.util.stream.Collectors;

/**s
 *
 * Created by Davy Maddelein on 15/02/2015.
 */

/**
 * a simple storage strategy to store proteins and retrieve them by their accessions, backed by a {@link java.util.Set}
 *
 * @param <T> the type of the identifier
 * @param <U> the type of the {@link com.compomics.pepshell.model.protein.ProteinInterface} to accept, is lower bounded
 */
public class AbstractProteinStoreStrategy<T, U extends ProteinInterface> implements StoreStrategy<T, U> {

    private Set<U> backingStructure = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(Collection<? extends U> aProtein) {
        return backingStructure.addAll(aProtein);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(U protein) {
        return backingStructure.add(protein);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public U retrieve(T accession) throws NoSuchElementException {
        return backingStructure.stream()
                .filter(e -> e.getVisibleAccession().equals(accession))
                .findFirst()
                .get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends U> retrieveAll() {
        return Collections.unmodifiableCollection(backingStructure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends U> retrieveSubSet(Collection<? extends T> accessions) {
        return backingStructure.stream()
                .filter(accessions::contains)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean purge(T accessionToPurgeFromStore) {
        return backingStructure.remove(backingStructure.stream()
                .filter(e -> e.getVisibleAccession().equals(accessionToPurgeFromStore))
                .findFirst()
                .get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean purgeSubset(Collection<? extends T> accessionsToPurgeFromStore) {
        return backingStructure.removeAll(backingStructure.stream().filter(accessionsToPurgeFromStore::contains).collect(Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean purgeAll() {
        backingStructure.clear();
        return backingStructure.size() == 0;
    }
}
