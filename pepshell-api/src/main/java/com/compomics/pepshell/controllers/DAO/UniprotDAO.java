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

package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Davy Maddelein
 */
public class UniprotDAO {

    public static String fetchSequenceFromUniprot(String accession) throws IOException {
        StringBuilder sequence = new StringBuilder();
        String uniprotFasta = WebUtils.getPage("http://www.uniprot.org/uniprot/" + accession + ".fasta");
        for (String fastaLine : uniprotFasta.split("\n")) {
            if (fastaLine.contains(">")) {
                //header for protein info object?
            } else {
                // use saxparser if more info requested --> also if string empty what then?
                sequence.append(fastaLine);
            }
        }
        return sequence.toString();
    }


    public static List<String> getDbReferenceEntriesForAccession(String accession, String dbReferenceType) throws IOException, ParserConfigurationException, SAXException {
        return getDbReferencesEntriesFromUniprotXML(loadUniprotXMLIntoDOM(WebUtils.openStream("http://www/uniprot/org/uniprot/" + accession + ".xml")), dbReferenceType);
    }

    public static List<String> getDbReferencesEntriesFromUniprotXML(Document uniprotXML, String dbReferenceType) {
        NodeList nodes = uniprotXML.getElementsByTagName("dbReference");
        List<String> accessions = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {

            Node aNode = nodes.item(i);
            if (aNode.getNodeType() == Node.ELEMENT_NODE && ((Element) aNode).getAttribute("type").equals(dbReferenceType)) {
                accessions.add(((Element) aNode).getAttribute("id"));
            }
        }
        return accessions;
    }

    public static Map<String, List<String>> getMultipleDbReferencesEntriesFromUniprotXML(String accession, List<String> dbReferenceTypes) throws IOException, ParserConfigurationException, SAXException {
        return getMultipleDbReferencesEntriesFromUniprotXML(loadUniprotXMLIntoDOM(WebUtils.openStream("http://www/uniprot/org/uniprot/" + accession + ".xml")), dbReferenceTypes);

    }

    public static Map<String, List<String>> getMultipleDbReferencesEntriesFromUniprotXML(Document uniprotXML, List<String> dbReferenceTypes) {
        NodeList nodes = uniprotXML.getElementsByTagName("dbReference");
        Map<String, List<String>> accessions = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); i++) {

            Node aNode = nodes.item(i);
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                String type = ((Element) aNode).getAttribute("type");
                if (dbReferenceTypes.contains(type)) {
                    if (accessions.get(type) == null) {
                        accessions.put(type, new ArrayList<String>() {{
                            this.add(((Element) aNode).getAttribute("id"));
                        }});
                    } else {
                        accessions.get(type).add(((Element) aNode).getAttribute("id"));
                    }
                }
            }
        }
        return accessions;
    }

    public static List<FeatureWithLocation> getDomainsForAccession(String accession) throws IOException, ParserConfigurationException, SAXException {
        return getDomainsFromUniprotXML(loadUniprotXMLIntoDOM(WebUtils.openStream("http://www.uniprot.org/uniprot/" + accession + ".xml")));
    }

    public static List<FeatureWithLocation> getDomainsFromUniprotXML(Document xmlDoc) {
        NodeList nodes = xmlDoc.getElementsByTagName("feature");
        List<FeatureWithLocation> domainsToReturn = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {

            Node aNode = nodes.item(i);
            if (aNode.getNodeType() == Node.ELEMENT_NODE && ((Element) aNode).getAttribute("type").equals("domain")) {
                FeatureWithLocation domain = new FeatureWithLocation(((Element) aNode).getAttribute("description"), Integer.parseInt(((Element) ((Element) aNode).getElementsByTagName("begin").item(0)).getAttribute("position")), Integer.parseInt(((Element) ((Element) aNode).getElementsByTagName("end").item(0)).getAttribute("position")));
                domainsToReturn.add(domain);
            }
        }
        return domainsToReturn;

    }

    public static List<FeatureWithLocation> getSecondaryStructureForAccession(String accession) throws IOException, ParserConfigurationException, SAXException {
        return getSecondaryStructureFromUniprotXML(loadUniprotXMLIntoDOM(WebUtils.openStream("http://www.uniprot.org/uniprot/" + accession + ".xml")));
    }

    public static List<FeatureWithLocation> getSecondaryStructureFromUniprotXML(Document uniprotDoc) {
        NodeList nodes = uniprotDoc.getElementsByTagName("feature");
        List<FeatureWithLocation> returnFeatures = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {

            Node aNode = nodes.item(i);
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                String type = ((Element) aNode).getAttribute("type");
                if (type.equals("strand") || type.equals("helix") || type.equals("turn")) {
                    int start = Integer.parseInt(((Element) ((Element) aNode).getElementsByTagName("begin").item(0)).getAttribute("position"));
                    int end = Integer.parseInt(((Element) ((Element) aNode).getElementsByTagName("end").item(0)).getAttribute("position"));
                    FeatureWithLocation secStructSpan = new FeatureWithLocation(((Element) aNode).getAttribute("type"), start, end);
                    returnFeatures.add(secStructSpan);
                }
            }
        }
        return returnFeatures;

    }

    //in case of slow or unwieldy --> stax impl

    public static Document loadUniprotXMLIntoDOM(InputStream page) throws ParserConfigurationException, IOException, SAXException {
        // return dom tree
        DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = domBuilder.parse(page);
        dom.getDocumentElement().normalize();
        return dom;
    }

    public static String getProteinSequenceFromUniprotXML(Document uniprotDoc) {
        NodeList sequenceNode = uniprotDoc.getElementsByTagName("sequence");
        String sequence = "";
        if (sequenceNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
            sequence = (sequenceNode.item(0)).getTextContent();
        }
        return sequence;
    }
}
