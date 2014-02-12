package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.controllers.DAO.DasParser;
import com.compomics.pepshell.controllers.DAO.URLController;
import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/7/13 Time: 8:14 AM To change
 * this template use File | Settings | File Templates.
 */
public class ExternalDomainFinder implements DataRetieverInterface {

    public void execute(List<Protein> proteinList) {
        for (Protein protein : proteinList) {
            try {
                protein.addDomains(getDomainsFromAllSitesForUniprotAccession(protein.getProteinAccession()));
            } catch (IOException ex) {
                Logger.getLogger(ExternalDomainFinder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XMLStreamException ex) {
                Logger.getLogger(ExternalDomainFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public enum DomainWebSites {
        //todo, add website specific strings to enum to iterate over them and add them

        PFAM,
        SMART,
        PROSITE
    }

    public static List<Domain> getDomainsForUniprotAccessionFromSingleSource(String aUniProtAccession, DomainWebSites aDomainWebSite) throws IOException, XMLStreamException {

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

    public static List<Domain> getDomainsFromAllSitesForUniprotAccession(String aUniProtAccession) throws IOException, XMLStreamException {
        List<Domain> foundDomains = new ArrayList<Domain>();
        for (DomainWebSites aDomainWebSite : DomainWebSites.values()) {
            foundDomains.addAll(getDomainsForUniprotAccessionFromSingleSource(aUniProtAccession, aDomainWebSite));
        }
        return foundDomains;
    }

    public static void addDomainsToProtein(Protein protein) throws IOException, ConversionException, XMLStreamException {
        protein.addDomains(getDomainsFromAllSitesForUniprotAccession(protein.getProteinAccession()));

    }
}
