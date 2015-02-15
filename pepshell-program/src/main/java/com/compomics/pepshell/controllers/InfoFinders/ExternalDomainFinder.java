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

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.DasParser;
import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;
import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.enums.DomainWebsitesEnum;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/7/13 Time: 8:14 AM To change
 * this template use File | Settings | File Templates.
 */
public class ExternalDomainFinder {


    public static List<Domain> getDomainsForUniprotAccessionFromSingleSource(String aUniProtAccession, DomainWebsitesEnum aDomainWebSite) throws IOException, XMLStreamException {

        List<Domain> foundDomains = new ArrayList<>();
        List<DasFeature> features;

        if (aDomainWebSite == DomainWebsitesEnum.UNIPROT) {

            features = DasParser.getAllDasFeatures(WebUtils.getPage(aDomainWebSite.getDomainURLString(aUniProtAccession)))
                    .stream().filter(e -> e.getFeatureId().contains("DOMAIN")).collect(Collectors.toList());
        } else {
            features = DasParser.getAllDasFeatures(WebUtils.getPage(aDomainWebSite.getDomainURLString(aUniProtAccession)));
        }
        features.stream().forEach((feature) -> {
            foundDomains.add(new Domain(feature.getFeatureLabel(), feature.getStart(), feature.getEnd(), aDomainWebSite.toString()));
            //smart.setId(features[j].getFeatureLabel());
        });
        return foundDomains;
    }

    public static List<Domain> getDomainsFromAllSitesForUniprotAccession(String aUniProtAccession) throws IOException, XMLStreamException {
        List<Domain> foundDomains = new ArrayList<>();
        int failedSources = 0;
        if (ProgramVariables.USEINTERNETSOURCES) {
            for (DomainWebsitesEnum aDomainWebSite : DomainWebsitesEnum.values()) {
                try {
                    foundDomains.addAll(getDomainsForUniprotAccessionFromSingleSource(aUniProtAccession, aDomainWebSite));
                } catch (IOException ioe) {
                    failedSources++;
                }
            }
        }
        if (failedSources > 0) {
            if (failedSources < DomainWebsitesEnum.values().length) {
                FaultBarrier.getInstance().handlePartialFailure(new IOException("could not retrieve domain data from all possible sources"));
            } else if (failedSources == DomainWebsitesEnum.values().length) {
                FaultBarrier.getInstance().handleException(new IOException("could not retrieve domain data from online services"));
            }
        }
        return foundDomains;
    }

    public static void addDomainsToProtein(Protein protein) throws IOException, ConversionException, XMLStreamException {
        protein.addDomains(getDomainsFromAllSitesForUniprotAccession(protein.getProteinAccession()));
    }
}
