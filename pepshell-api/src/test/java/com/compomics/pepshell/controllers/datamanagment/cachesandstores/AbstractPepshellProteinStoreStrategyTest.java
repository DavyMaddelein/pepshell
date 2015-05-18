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

import com.compomics.pepshell.controllers.datamanagment.cachesandstores.stores.AbstractProteinStoreStrategy;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.ProteinInterface;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AbstractPepshellProteinStoreStrategyTest {

    private PepshellProtein testPepshellProtein = new PepshellProtein("P53");

    private ArrayList<PepshellProtein> someProteinsUnique = new ArrayList(10) {{
        this.add(new PepshellProtein("1"));
        this.add(new PepshellProtein("2"));
        this.add(new PepshellProtein("3"));
        this.add(new PepshellProtein("4"));
        this.add(new PepshellProtein("5"));
        this.add(new PepshellProtein("6"));
        this.add(new PepshellProtein("7"));
        this.add(new PepshellProtein("8"));
        this.add(new PepshellProtein("9"));
        this.add(new PepshellProtein("10"));
    }};

    private ArrayList<PepshellProtein> someProteinsNonUnique = new ArrayList(10) {{
        this.add(new PepshellProtein("1"));
        this.add(new PepshellProtein("2"));
        this.add(new PepshellProtein("3"));
        this.add(new PepshellProtein("4"));
        this.add(new PepshellProtein("3"));
        this.add(new PepshellProtein("6"));
        this.add(new PepshellProtein("7"));
        this.add(new PepshellProtein("3"));
        this.add(new PepshellProtein("5"));
        this.add(new PepshellProtein("10"));
    }};

    @Test
    public void testAcceptSingleElement() throws Exception {
        AbstractProteinStoreStrategy<String, ProteinInterface> strategy = new AbstractProteinStoreStrategy<>();
        assertThat(strategy.accept(testPepshellProtein), is(true));

    }

    @Test
    public void testAcceptCollection() throws Exception {
        AbstractProteinStoreStrategy<String,ProteinInterface> strategy = new AbstractProteinStoreStrategy<>();
        assertThat(strategy.accept(someProteinsUnique),is(true));
    }

    @Test
    public void testRetrieve() throws Exception {
        AbstractProteinStoreStrategy<String,PepshellProtein> strategy = new AbstractProteinStoreStrategy<>();
        assertThat(strategy.accept(testPepshellProtein),is(true));
        assertThat(strategy.retrieve(testPepshellProtein.getOriginalAccession()),is(equalTo(testPepshellProtein)));
        assertThat(strategy.accept(someProteinsUnique),is(true));
        assertThat(strategy.retrieveAll().size(),is(11));
        assertThat(strategy.retrieve(testPepshellProtein.getOriginalAccession()),is(equalTo(testPepshellProtein)));
        assertThat(strategy.retrieve(someProteinsUnique.get(4).getOriginalAccession()),is(equalTo(someProteinsUnique.get(4))));
        assertThat(strategy.purge(testPepshellProtein.getOriginalAccession()),is(true));
        try{
        assertThat(strategy.retrieve(testPepshellProtein.getOriginalAccession()),is(nullValue()));}
        catch (NoSuchElementException ex){
            //as expected
        }


    }

    @Test
    public void testRetrieveAllProteins() throws Exception {

    }

    @Test
    public void testRetrieveSubSet() throws Exception {

    }

    @Test
    public void testPurge() throws Exception {

    }

    @Test
    public void testPurgeSubset() throws Exception {

    }

    @Test
    public void testPurgeAll() throws Exception {

    }
}