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

package com.compomics.pepshell.controllers.CachesAndStores.StoreStrategies;

import com.compomics.pepshell.model.ProteinInterface;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * Created by Davy Maddelein on 15/02/2015.
 */

/**
 * a simple storage strategy to store proteins and retrieve them by their accessions, backed by a {@link java.util.Set}
 *
 * @param <T> the type of the string identifier
 * @param <U> the type of the {@link com.compomics.pepshell.model.ProteinInterface} to accept, is lower bounded
 */
public class AbstractProteinStoreStrategy<T extends String, U extends ProteinInterface> implements StoreStrategy<T, U> {

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
    public U retrieve(T identifier) {
        return backingStructure.stream().filter(e -> e.getProteinAccession().equals(identifier)).findFirst().get();
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
    public Collection<? extends U> retrieveSubSet(Collection<? extends T> identifiers) {
        return backingStructure.stream().filter(e -> identifiers.contains(e.getProteinAccession())).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean purge(T identifierToPurgeFromStore) {
        return backingStructure.remove(backingStructure.stream().filter(e -> e.getProteinAccession().equals(identifierToPurgeFromStore)).findFirst().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean purgeSubset(Collection<T> identifiersToPurgeFromStore) {
        return backingStructure.removeAll(backingStructure.stream().filter(e -> identifiersToPurgeFromStore.contains(e.getProteinAccession())).collect(Collectors.toList()));
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
