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

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 3/29/2016.
 */
public class InternetStructureDataSourceTest {

    @Test
    public void testGetDomainDataOnlyDomains() throws Exception {
        InternetStructureDataSource<PepshellProtein> test = new InternetStructureDataSource<>();
        List<FeatureWithLocation> resultList = test.getDomainData(new PepshellProtein("P15056"));
        assertThat(resultList.size(), equalTo(2));
    }

    @Test
    public void testGetDomainDataAllPossible() throws Exception {
        InternetStructureDataSource<PepshellProtein> test = new InternetStructureDataSource<>();
        List<FeatureWithLocation> resultList = test.getDomainData(new PepshellProtein("P15056"), ExternalStructureDataSource.DomainDataLevel.ALLDOMAINDATA);
    }

    @Test
    public void testGetPdbInforForProtein() throws Exception {
        InternetStructureDataSource<PepshellProtein> test = new InternetStructureDataSource<>();
        Set<PdbInfo> resultList = test.getPdbInforForProtein(new PepshellProtein("P15056"));
        assertThat(resultList.size(),equalTo(45));
    }
}