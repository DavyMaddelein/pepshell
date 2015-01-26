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
import com.compomics.pepshell.controllers.DAO.DasParser;
import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;
import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/7/13 Time: 8:14 AM To change
 * this template use File | Settings | File Templates.
 */
public class ExternalDomainFinder {

    public void execute(List<Protein> proteinList) {
        for (Protein protein : proteinList) {
            try {
                protein.addDomains(getDomainsFromAllSitesForUniprotAccession(protein.getProteinAccession()));
            } catch (IOException | XMLStreamException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
    }

    //TODO move this to internetstructuredatasource
    public enum DomainWebSites {
        //todo, add website specific strings to enum to iterate over them and add them

        PFAM,
        SMART,
        //PROSITE,
        UNIPROT
    }

    public static List<Domain> getDomainsForUniprotAccessionFromSingleSource(String aUniProtAccession, DomainWebSites aDomainWebSite) throws IOException, XMLStreamException {

        List<Domain> foundDomains = new ArrayList<>();
        List<DasFeature> features = new ArrayList<>();
        if (aDomainWebSite == DomainWebSites.PFAM) {
            features = DasParser.getAllDasFeatures(WebUtils.getHTMLPage("http://das.sanger.ac.uk/das/pfam/features?segment=" + aUniProtAccession));
        } else if (aDomainWebSite == DomainWebSites.SMART) {
            features = DasParser.getAllDasFeatures(WebUtils.getHTMLPage("http://smart.embl.de/smart/das/smart/features?segment=" + aUniProtAccession));
//        } else if (aDomainWebSite == DomainWebSites.PROSITE) {
//            features = DasParser.getAllDasFeatures(URLController.getHTMLPage("http://proserver.vital-it.ch/das/prositefeature/features?segment=" + aUniProtAccession));
//        } 
        } else if (aDomainWebSite == DomainWebSites.UNIPROT) {

            features = Lists.newArrayList(Collections2.filter(DasParser.getAllDasFeatures(WebUtils.getHTMLPage("https://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/features?segment=" + aUniProtAccession)), new Predicate<DasFeature>() {

                @Override
                public boolean apply(DasFeature input) {
                    return input.getFeatureId().contains("DOMAIN");
                }
            }));
        }
        for (DasFeature feature : features) {
            foundDomains.add(new Domain(feature.getFeatureLabel(), feature.getStart(), feature.getEnd(), aDomainWebSite.toString()));
            //smart.setId(features[j].getFeatureLabel());
        }
        return foundDomains;
    }

    public static List<Domain> getDomainsFromAllSitesForUniprotAccession(String aUniProtAccession) throws IOException, XMLStreamException {
        List<Domain> foundDomains = new ArrayList<>();
        int failedSources = 0;
        for (DomainWebSites aDomainWebSite : DomainWebSites.values()) {
            try {
                foundDomains.addAll(getDomainsForUniprotAccessionFromSingleSource(aUniProtAccession, aDomainWebSite));
            } catch (IOException ioe) {
                failedSources++;
            }
        }
        if (failedSources > 0 ){
            FaultBarrier.getInstance().handlePartialFailure(new IOException("could not retrieve domain data from all possible sources"));
        }
        return foundDomains;
    }

    public static void addDomainsToProtein(Protein protein) throws IOException, ConversionException, XMLStreamException {
        protein.addDomains(getDomainsFromAllSitesForUniprotAccession(protein.getProteinAccession()));
    }
}
