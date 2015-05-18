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

package com.compomics.pepshell.controllers.datamanagment;

import com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import org.junit.Test;

import java.util.ArrayList;

public class PepshellProteinStoreManagerTest {

    ProteinStoreManager managerToTest = ProteinStoreManager.getInstance();

    ArrayList<PepshellProtein> uniqueList = new ArrayList(){{
        this.add(new PepshellProtein("1"));
        this.add(new PepshellProtein("2"));
        this.add(new PepshellProtein("3"));
        this.add(new PepshellProtein("4"));
        this.add(new PepshellProtein("5"));
        this.add(new PepshellProtein("6"));
        this.add(new PepshellProtein("7"));
        this.add(new PepshellProtein("8"));
        this.add(new PepshellProtein("8"));
    }};


    ArrayList<PepshellProtein> nonUniqueList = new ArrayList(){{
        this.add(new PepshellProtein("1"));
        this.add(new PepshellProtein("2"));
        this.add(new PepshellProtein("3"));
        this.add(new PepshellProtein("5"));
        this.add(new PepshellProtein("5"));
        this.add(new PepshellProtein("5"));
        this.add(new PepshellProtein("7"));
        this.add(new PepshellProtein("8"));
        this.add(new PepshellProtein("8"));
    }};

    @Test
    public void testRetrieveProteinByAccession() throws Exception {

    }

    @Test
    public void testAddAProteinToStore() throws Exception {

    }

    @Test
    public void testAddProteinsToStore() throws Exception {

    }

    @Test
    public void testSwitchStorageStrategies() throws Exception {

    }
}