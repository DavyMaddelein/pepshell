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

package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.enums.DomainWebsitesEnum;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.xml.sax.SAXException;

/**
 * Created by Davy Maddelein on 11/02/2015.
 */
public class ExternalDomainInfoFinderTest {

    String p53UniprotAccession = "P15056";

    @Test
    public void testGetDomainsForUniprotAccessionFromSingleSource() throws IOException, XMLStreamException, ParserConfigurationException, SAXException {
        List<FeatureWithLocation> domainsReturned;
        domainsReturned = ExternalDomainFinder.getDomainsForUniprotAccessionFromSingleSource(p53UniprotAccession, DomainWebsitesEnum.UNIPROT);
        assertThat(domainsReturned.size(),is(2));
        assertThat(domainsReturned.get(0).getDescription(),is("RBD"));
        assertThat(domainsReturned.get(0).getStartPosition(),is(155));
        assertThat(domainsReturned.get(0).getEndPosition(),is(227));
        assertThat(domainsReturned.get(1).getDescription(),is("Protein kinase"));
        assertThat(domainsReturned.get(1).getStartPosition(),is(457));
        assertThat(domainsReturned.get(1).getEndPosition(),is(717));
        domainsReturned = ExternalDomainFinder.getDomainsForUniprotAccessionFromSingleSource(p53UniprotAccession, DomainWebsitesEnum.PFAM);

    }
}
