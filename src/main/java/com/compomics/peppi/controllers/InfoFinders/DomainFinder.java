package com.compomics.peppi.controllers.InfoFinders;

import com.compomics.peppi.controllers.AccessionConverter;
import com.compomics.peppi.controllers.DAO.DasParser;
import com.compomics.peppi.controllers.DAO.URLController;
import com.compomics.peppi.model.DAS.DasFeature;
import com.compomics.peppi.model.Domain;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/7/13 Time: 8:14 AM To change
 * this template use File | Settings | File Templates.
 */
public class DomainFinder {

    public enum DomainWebSites {
        //todo, add website specific strings to enum to iterate over them and add them

        PFAM,
        SMART,
        PROSITE
    }

    public static List<Domain> getDomainsForSwissprotAccessionFromSingleSource(String aUniProtAccession, DomainWebSites aDomainWebSite) throws IOException, XMLStreamException {

        List<Domain> foundDomains = new ArrayList<Domain>();
        List<DasFeature> features = new ArrayList<DasFeature>();
        if (aDomainWebSite == DomainWebSites.PFAM) {
            //TODO check if das is parsed decently for pfam
            features = DasParser.getAllDasFeatures(URLController.readUrl("http://das.sanger.ac.uk/das/pfam/features?segment=" + aUniProtAccession));
        } else if (aDomainWebSite == DomainWebSites.SMART) {
            features = DasParser.getAllDasFeatures(URLController.readUrl("http://smart.embl.de/smart/das/smart/features?segment=" + aUniProtAccession));
        } else if (aDomainWebSite == DomainWebSites.PROSITE) {
            features = DasParser.getAllDasFeatures(URLController.readUrl("http://proserver.vital-it.ch/das/prositefeature/features?segment=" + aUniProtAccession));
        }
        for (DasFeature feature : features) {
            foundDomains.add(new Domain(feature.getFeatureLabel(), feature.getStart(), feature.getEnd(), aDomainWebSite.toString()));
            //smart.setId(features[j].getFeatureLabel());
        }
        return foundDomains;
    }

    public static List<Domain> getDomainsFromAllSitesForSwissprotAccession(String aUniProtAccession) throws IOException, XMLStreamException {
        List<Domain> foundDomains = new ArrayList<Domain>();
        for (DomainWebSites aDomainWebSite : DomainWebSites.values()) {
            foundDomains.addAll(getDomainsForSwissprotAccessionFromSingleSource(aUniProtAccession, aDomainWebSite));
        }
        return foundDomains;
    }

    public static void addDomainsToProtein(Protein protein) throws IOException, ConversionException, XMLStreamException {
        if (protein.getProteinAccession().contains("|") || protein.getProteinAccession().contains("gi")) {
            protein.addDomains(getDomainsFromAllSitesForSwissprotAccession(AccessionConverter.GIToUniprot(protein.getProteinAccession())));
        } else {
            protein.addDomains(getDomainsFromAllSitesForSwissprotAccession(protein.getProteinAccession()));
        }
    }
}
