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

import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Davy Maddelein on 11/02/2015.
 */
public class UniprotDAOTest {

    private String testAccession = "P15056";
    private List<String> dbReferenceDescriptors;


    @Test
    public void testLoadUniprotXMLIntoDOM() throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
        //petrified xml file
        File testXML = new File(ClassLoader.getSystemClassLoader().getResource("testuniprotxml.xml").getPath());
        Document doc = UniprotDAO.loadUniprotXMLIntoDOM(new FileInputStream(testXML));
        //online xml file
        URI testURI = new URI("http://www.uniprot.org/uniprot/" + testAccession + ".xml");
        HttpURLConnection connection = (HttpURLConnection) testURI.toURL().openConnection();
        BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
        doc = UniprotDAO.loadUniprotXMLIntoDOM(in);

    }

    //TODO put following tests in a conditional testsuite so they only run when the previous test passed the online section and run them with both docs

    @Test
    public void testFetchSequenceFromUniprot() throws IOException {

        String sequence = UniprotDAO.fetchSequenceFromUniprot(testAccession);
        assertThat(sequence, is(equalTo("MAALSGGGGGGAEPGQALFNGDMEPEAGAGAGAAASSAADPAIPEEVWNIKQMIKLTQEH" +
                "IEALLDKFGGEHNPPSIYLEAYEEYTSKLDALQQREQQLLESLGNGTDFSVSSSASMDTV" +
                "TSSSSSSLSVLPSSLSVFQNPTDVARSNPKSPQKPIVRVFLPNKQRTVVPARCGVTVRDS" +
                "LKKALMMRGLIPECCAVYRIQDGEKKPIGWDTDISWLTGEELHVEVLENVPLTTHNFVRK" +
                "TFFTLAFCDFCRKLLFQGFRCQTCGYKFHQRCSTEVPLMCVNYDQLDLLFVSKFFEHHPI" +
                "PQEEASLAETALTSGSSPSAPASDSIGPQILTSPSPSKSIPIPQPFRPADEDHRNQFGQR" +
                "DRSSSAPNVHINTIEPVNIDDLIRDQGFRGDGGSTTGLSATPPASLPGSLTNVKALQKSP" +
                "GPQRERKSSSSSEDRNRMKTLGRRDSSDDWEIPDGQITVGQRIGSGSFGTVYKGKWHGDV" +
                "AVKMLNVTAPTPQQLQAFKNEVGVLRKTRHVNILLFMGYSTKPQLAIVTQWCEGSSLYHH" +
                "LHIIETKFEMIKLIDIARQTAQGMDYLHAKSIIHRDLKSNNIFLHEDLTVKIGDFGLATV" +
                "KSRWSGSHQFEQLSGSILWMAPEVIRMQDKNPYSFQSDVYAFGIVLYELMTGQLPYSNIN" +
                "NRDQIIFMVGRGYLSPDLSKVRSNCPKAMKRLMAECLKKKRDERPLFPQILASIELLARS" +
                "LPKIHRSASEPSLNRAGFQTEDFSLYACASPKTPIQAGGYGAFPVH")));
    }

    @Test
    public void testGetSequenceFromUniprotXML() throws IOException, SAXException, ParserConfigurationException {
        File testXML = new File(ClassLoader.getSystemClassLoader().getResource("testuniprotxml.xml").getPath());

        String sequence = UniprotDAO.getProteinSequenceFromUniprotXML(UniprotDAO.loadUniprotXMLIntoDOM(new FileInputStream(testXML)));
        assertThat(sequence, is(equalTo("MAALSGGGGGGAEPGQALFNGDMEPEAGAGAGAAASSAADPAIPEEVWNIKQMIKLTQEH" +
                "IEALLDKFGGEHNPPSIYLEAYEEYTSKLDALQQREQQLLESLGNGTDFSVSSSASMDTV" +
                "TSSSSSSLSVLPSSLSVFQNPTDVARSNPKSPQKPIVRVFLPNKQRTVVPARCGVTVRDS" +
                "LKKALMMRGLIPECCAVYRIQDGEKKPIGWDTDISWLTGEELHVEVLENVPLTTHNFVRK" +
                "TFFTLAFCDFCRKLLFQGFRCQTCGYKFHQRCSTEVPLMCVNYDQLDLLFVSKFFEHHPI" +
                "PQEEASLAETALTSGSSPSAPASDSIGPQILTSPSPSKSIPIPQPFRPADEDHRNQFGQR" +
                "DRSSSAPNVHINTIEPVNIDDLIRDQGFRGDGGSTTGLSATPPASLPGSLTNVKALQKSP" +
                "GPQRERKSSSSSEDRNRMKTLGRRDSSDDWEIPDGQITVGQRIGSGSFGTVYKGKWHGDV" +
                "AVKMLNVTAPTPQQLQAFKNEVGVLRKTRHVNILLFMGYSTKPQLAIVTQWCEGSSLYHH" +
                "LHIIETKFEMIKLIDIARQTAQGMDYLHAKSIIHRDLKSNNIFLHEDLTVKIGDFGLATV" +
                "KSRWSGSHQFEQLSGSILWMAPEVIRMQDKNPYSFQSDVYAFGIVLYELMTGQLPYSNIN" +
                "NRDQIIFMVGRGYLSPDLSKVRSNCPKAMKRLMAECLKKKRDERPLFPQILASIELLARS")));

    }

    @Test
    public void testGetDbReferencesForAccession() throws ParserConfigurationException, SAXException, IOException {
        List<String> identifiers = UniprotDAO.getDbReferenceEntriesForAccession(testAccession,"PDB");
        assertThat(identifiers.size(),is(20));
        assertThat(identifiers.get(5),is("X145"));
    }

    @Test
    public void testGetDbReferencesEntriesFromUniprotXML() throws IOException, SAXException, ParserConfigurationException {
        File testXML = new File(ClassLoader.getSystemClassLoader().getResource("testuniprotxml.xml").getPath());

        List<String> identifiers = UniprotDAO.getDbReferencesEntriesFromUniprotXML(UniprotDAO.loadUniprotXMLIntoDOM(new FileInputStream(testXML)), "PDB");
        assertThat(identifiers.size(),is(45));
        assertThat(identifiers.get(5),is("3D4Q"));
    }

    @Test
    public void testGetMultipleDbReferencesEntriesFromUniprotXML() throws IOException, SAXException, ParserConfigurationException {
        File testXML = new File(ClassLoader.getSystemClassLoader().getResource("testuniprotxml.xml").getPath());
        Map<String,List<String>> identifiers = UniprotDAO.getMultipleDbReferencesEntriesFromUniprotXML(UniprotDAO.loadUniprotXMLIntoDOM(new FileInputStream(testXML)),new ArrayList<String>(){{this.add("PDB");this.add("GO");}});
        assertThat(identifiers.get("PDB").size(),is(45));
        assertThat(identifiers.get("PDB").get(5),is("3D4Q"));
        assertThat(identifiers.get("GO").size(),is(42));
        assertThat(identifiers.get("GO").get(3),is("GO:0043005"));
    }

    @Test
    public void testGetMultipleDbReferencesEntriesForAccession() throws IOException, SAXException, ParserConfigurationException {
        Map<String,List<String>> identifiers = UniprotDAO.getMultipleDbReferencesEntriesFromUniprotXML(testAccession,new ArrayList<String>(){{this.add("PDB");this.add("GO");}});
        assertThat(identifiers.get("PDB").size(),is(45));
        assertThat(identifiers.get("PDB").get(5),is("3D4Q"));
        assertThat(identifiers.get("GO").size(),is(42));
        assertThat(identifiers.get("GO").get(3),is("GO:0043005"));
    }

    @Test
    public void testGetDomainsForAccession() throws IOException, ParserConfigurationException, SAXException {
        List<FeatureWithLocation> domains = UniprotDAO.getDomainsForAccession(testAccession);

        assertThat(domains.size(), is(2));
        assertThat(domains.get(0).getDescription(), is("RBD"));
        assertThat(domains.get(0).getStartPosition(), is(155));
        assertThat(domains.get(0).getEndPosition(), is(227));

        assertThat(domains.get(1).getDescription(), is("Protein kinase"));
        assertThat(domains.get(1).getStartPosition(), is(457));
        assertThat(domains.get(1).getEndPosition(), is(717));
    }

    @Test
    public void testGetDomainsForAccessionOffline() throws IOException, ParserConfigurationException, SAXException {
        File testXML = new File(ClassLoader.getSystemClassLoader().getResource("testuniprotxml.xml").getPath());

        List<FeatureWithLocation> domains = UniprotDAO.getDomainsFromUniprotXML(UniprotDAO.loadUniprotXMLIntoDOM(new FileInputStream(testXML)));

        assertThat(domains.size(), is(2));
        assertThat(domains.get(0).getDescription(), is("RBD"));
        assertThat(domains.get(0).getStartPosition(), is(155));
        assertThat(domains.get(0).getEndPosition(), is(227));

        assertThat(domains.get(1).getDescription(), is("Protein kinase"));
        assertThat(domains.get(1).getStartPosition(), is(457));
        assertThat(domains.get(1).getEndPosition(), is(717));
    }

    @Test
    public void testGetSecondaryStructureFromUniprotXML() throws IOException, SAXException, ParserConfigurationException {
        File testXML = new File(ClassLoader.getSystemClassLoader().getResource("testuniprotxml.xml").getPath());
        List<FeatureWithLocation> secondaryStructures = UniprotDAO.getSecondaryStructureFromUniprotXML(UniprotDAO.loadUniprotXMLIntoDOM(new FileInputStream(testXML)));
        assertThat(secondaryStructures.size(), is(39));
        assertThat(secondaryStructures.get(3).getDescription(), is("helix"));
    }

    @Test
    public void testGetSecondaryStructureForAccession() throws ParserConfigurationException, SAXException, IOException {
        List<FeatureWithLocation> secondaryStructures = UniprotDAO.getSecondaryStructureForAccession(testAccession);
        assertThat(secondaryStructures.size(), is(39));
        assertThat(secondaryStructures.get(3).getDescription(), is("helix"));

    }
}