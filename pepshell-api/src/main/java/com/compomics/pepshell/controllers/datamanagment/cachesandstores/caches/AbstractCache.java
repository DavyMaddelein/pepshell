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

package com.compomics.pepshell.controllers.datamanagment.cachesandstores.caches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * Created by Davy Maddelein on 6/01/2016.
 */
public class AbstractCache<T> implements CacheManagerInterface<T> {

    private List<T> cache;
    private static final Random randomgen = new Random();
    private static boolean cacheOnlyUnique = false;
    private int cacheSize = 1000;

    public AbstractCache(){
        cache = new ArrayList<>(cacheSize);
    }

    public AbstractCache(List<T> cache) {
        this.cache = cache;
        cacheSize = this.cache.size();
    }

    @Override
    public T getFromCache(T cachedReference) {
        return cache.get(cache.indexOf(cachedReference));
    }

    @Override
    public T getRandomFromCache() {
        return cache.get(randomgen.nextInt(cache.size()));
    }

    @Override
    public T getLastFromCache() {
        return cache.get(cache.size());
    }

    @Override
    public boolean pushToCache(T objectToCache) {
        if (cache.size() == cacheSize) {
            cache.remove(0);
        }
        return !(cacheOnlyUnique && cache.contains(objectToCache))
                && cache.add(objectToCache);
    }

    @Override
    public T popFromCache() {
        return cache.remove(0);
    }

    @Override
    public List<T> popBatchFromCache(int amountToGet) {
        if (amountToGet < 1 || amountToGet > cache.size()) {
            throw new IllegalArgumentException("requested amount cannot be handled: value was " + amountToGet);
        }
        if (amountToGet == cache.size()) {
            return cache.stream().collect(Collectors.toList());
        } else {
            return cache.subList(0, amountToGet);
        }
    }

    public void setCacheOnlyUnique(boolean cacheOnlyUnique) {
        AbstractCache.cacheOnlyUnique = cacheOnlyUnique;
    }

    public void setCacheSize(int cacheSize){
        if (cacheSize < 1) {
            throw new IllegalArgumentException("cache size not supported: "+cacheSize);
        } else if (cacheSize < cache.size()) {
            cache = cache.subList(0,cacheSize);
        } else {
         this.cacheSize = cacheSize;
        }
    }
}
