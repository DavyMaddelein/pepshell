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

import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Davy Maddelein
 */

//not happy with how this turned out
public class InternetStructureDataSource<T extends PepshellProtein> extends ExternalStructureDataSource<T> {


    List<FeatureWithLocation> getDomainData(T aProtein,DomainDataLevel aLevel) throws DataRetrievalException{
        List<FeatureWithLocation> domains = new ArrayList<>();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            if(aLevel.equals(DomainDataLevel.ONLYFULLYQUALIFIEDDOMAINS)) {
                XMLEventReader events = factory.createFilteredReader(factory.createXMLEventReader(new BufferedInputStream(new URI("http://www.uniprot.org/uniprot/" + aProtein.getVisibleAccession() + ".xml").toURL().openStream())
                ), event -> event.isStartElement() &&
                        event.asStartElement().getName().getLocalPart().equalsIgnoreCase("feature") &&
                        event.asStartElement().getAttributeByName(new QName("type")).getValue().equalsIgnoreCase("domain"));
                while (events.hasNext()) {
                    XMLEvent event = events.nextEvent();
                    domains.add(new FeatureWithLocation(event.asStartElement().getAttributeByName(new QName("description")).getValue(), 1));
                }
            } else {
               //todo find a good way to filter this data out
            }
        } catch (IOException | XMLStreamException | URISyntaxException e) {
            DataRetrievalException ex = new DataRetrievalException("could not retrieve domain info for " + aProtein.getVisibleAccession());
            ex.addSuppressed(e);
            throw ex;
        }
        return domains;
    }

    @Override
    public List<FeatureWithLocation> getDomainData(T aProtein) throws DataRetrievalException {
      return getDomainData(aProtein,DomainDataLevel.ONLYFULLYQUALIFIEDDOMAINS);
    }

    @Override
    public String getPDBDataForPDBName(String pdbAccession) {
        return super.getPDBDataForPDBName(pdbAccession);
    }

    @Override
    public Set<PdbInfo> getPdbInforForProtein(T protein) throws DataRetrievalException {
        Set<PdbInfo> pdbInfos = new HashSet<>();
        PDBEventFilter filter = new PDBEventFilter();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader events = factory.createFilteredReader(factory.createXMLEventReader(new BufferedInputStream(new URI("http://www.uniprot.org/uniprot/" + protein.getVisibleAccession() + ".xml").toURL().openStream())
            ), filter);
            while (events.hasNext()) {
                XMLEvent event = events.nextEvent();
                PdbInfo pdb = new PdbInfo(event.asStartElement().getAttributeByName(new QName("id")).getValue());
                pdb.setPdbAccession(event.asStartElement().getAttributeByName(new QName("id")).getValue());
                pdbInfos.add(pdb);
            }
        } catch (IOException | XMLStreamException | URISyntaxException e) {
            DataRetrievalException ex = new DataRetrievalException("could not retrieve pdb info for " + protein.getVisibleAccession());
            ex.addSuppressed(e);
            throw ex;
        }
        return pdbInfos;
    }

    @Override
    public boolean isAbleTogetSecondaryStructure() {
        return true;
    }

    @Override
    public boolean isAbleToGetSolventAccessibility() {
        return super.isAbleToGetSolventAccessibility();
    }

    @Override
    public boolean isAbleToGetFreeEnergy() {
        return super.isAbleToGetFreeEnergy();
    }

    private class PDBEventFilter implements EventFilter {
        boolean inblock = false;

        @Override
        public boolean accept(XMLEvent event) {
            if (event.isStartElement() &&
                    event.asStartElement().getName().getLocalPart().equalsIgnoreCase("dbReference") &&
                    event.asStartElement().getAttributeByName(new QName("type")).getValue().equalsIgnoreCase("PDB")) {
                //inblock = true;
                return true;
            }
/*             else if (inblock && event.isStartElement() &&
                    (event.asStartElement().getAttributeByName(new QName("type")).getValue().equalsIgnoreCase("method")
                            //|| event.asStartElement().getAttributeByName(new QName("type")).getValue().equalsIgnoreCase("resolution")
                            //|| event.asStartElement().getAttributeByName(new QName("type")).getValue().equalsIgnoreCase("chains")
                    )) {
                return true;


            } else if (inblock && event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase("dbReference")) {
                inblock = false;
                return false;
            }*/
            return false;
        }
    }
}


