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

import com.compomics.pepshell.controllers.DAO.UniprotDAO;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.enums.DomainWebsitesEnum;
import com.compomics.pepshell.model.exceptions.ConversionException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/7/13 Time: 8:14 AM To change
 * this template use File | Settings | File Templates.
 */
public class ExternalDomainFinder {


    public static List<FeatureWithLocation> getDomainsForUniprotAccessionFromSingleSource(String aUniProtAccession, DomainWebsitesEnum aDomainWebSite) throws IOException, XMLStreamException, ParserConfigurationException, SAXException {

        List<FeatureWithLocation> foundDomains = new ArrayList<>();

        if (aDomainWebSite == DomainWebsitesEnum.UNIPROT) {

            foundDomains = UniprotDAO.getDomainsForAccession(aUniProtAccession);

        } else {
            //    features = DasParser.getAllDasFeatures(WebUtils.getPage(aDomainWebSite.getDomainURLString(aUniProtAccession)));
        }
        return foundDomains;
    }

    public static List<FeatureWithLocation> getDomainsFromAllSitesForUniprotAccession(String aUniProtAccession) throws IOException, XMLStreamException {
        List<FeatureWithLocation> foundDomains = new ArrayList<>();
        int failedSources = 0;

            for (DomainWebsitesEnum aDomainWebSite : DomainWebsitesEnum.values()) {
                try {
                    foundDomains.addAll(getDomainsForUniprotAccessionFromSingleSource(aUniProtAccession, aDomainWebSite));
                } catch (IOException | ParserConfigurationException | SAXException ioe) {
                    failedSources++;
                }
            }
        if (failedSources > 0) {
            if (failedSources < DomainWebsitesEnum.values().length) {
                //FaultBarrier.getInstance().handlePartialFailure(new IOException("could not retrieve domain data from all possible sources"));
            } else if (failedSources == DomainWebsitesEnum.values().length) {
                //FaultBarrier.getInstance().handleException(new IOException("could not retrieve domain data from online services"));
            }
        }
        return foundDomains;
    }

    public static void addDomainsToProtein(PepshellProtein pepshellProtein) throws IOException, ConversionException, XMLStreamException {
        pepshellProtein.addDomains(getDomainsFromAllSitesForUniprotAccession(pepshellProtein.getVisibleAccession()));
    }
}
