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

import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import com.compomics.pepshell.model.exceptions.ConversionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.xml.stream.XMLStreamException;

/**
 * @author Davy Maddelein
 */
public class PDBDAO {

    PDBDAO() {
    }

    private static PDBDAO instance;

    public static PDBDAO getInstance() {
        if (instance == null) {
            instance = new PDBDAO();
        }
        return instance;
    }

    /**
     * get the info of the pdb files connected to the pepshell protein passed
     * @param pepshellProtein the protein to retrieve the information for
     * @return the pdb info objects with the pdb accessions in
     * @throws IOException
     */
    public Set<PdbInfo> getPDBInfoForProtein(PepshellProtein pepshellProtein) throws IOException {
        Set<PdbInfo> pdbInfoToReturn = new HashSet<>();
        try (BufferedReader br = WebUtils.openReader("http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=" + AccessionConverter.toUniprot(pepshellProtein.getVisibleAccession()))){
            String pdbLine = br.readLine();
            while (pdbLine != null) {
                if (pdbLine.contains("Uniprot to PDB mapping")) {
                    pdbInfoToReturn = startUniprotPDBParsing(br);
                }
                pdbLine = br.readLine();
            }
        } catch (ConversionException e) {
            IOException ioe = new IOException("could not retrieve pdb info for "+pepshellProtein.getVisibleAccession());
            ioe.addSuppressed(e);
            throw ioe;
        }
        return pdbInfoToReturn;
    }

    /**
     * fetches a pdb file and writes it to a temporary file
     * it is marked to be deleted on program exit
     * @param aPdbAccession the accession to retrieve fhe file for
     * @return the downloaded pdb file on disk location
     * @throws IOException if the file could not be downloaded or written to disk
     */
    public static File getPdbFile(String aPdbAccession) throws IOException {
        File pdbFile = File.createTempFile(aPdbAccession, ".pdb");
        pdbFile.deleteOnExit();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pdbFile), "UTF-8"))) {
            bw.write(WebUtils.getPage("http://www.ebi.ac.uk/pdbe-srv/view/files/" + aPdbAccession.toLowerCase(Locale.UK) + ".ent"));
        }
        pdbFile.deleteOnExit();
        return pdbFile;
    }

    /**
     * get the pdb file from the pdb server and return it as a string
     * @param aPdbAccession the pdb accession to get from the server
     * @return the file as string
     * @throws IOException if the file could not be retrieved
     */
    public static String getPdbFileInMem(String aPdbAccession) throws IOException {
        StringWriter pdbFile = new StringWriter();
        if (aPdbAccession != null) {
            BufferedWriter bw = new BufferedWriter(pdbFile);
            bw.write(WebUtils.getPage("http://www.ebi.ac.uk/pdbe-srv/view/files/" + aPdbAccession.toLowerCase() + ".ent"));
        }
        return pdbFile.toString();
    }

    /**
     * reads the pdb accessions linked to a uniprot accession, does not reset the reader
     * @param br the reader to parse from
     * @return the pdb accessions contained in the buffered reader
     * @throws IOException
     */
    private static Set<PdbInfo> startUniprotPDBParsing(BufferedReader br) throws IOException {
        Set<PdbInfo> pdbFilesToReturn = new HashSet<>();
        String pdbLine;
        while ((pdbLine = br.readLine()) != null) {
            if (!pdbLine.startsWith("#")) {
                String[] pdbSplitLine = pdbLine.split("\t");
                PdbInfo info = new PdbInfo(pdbSplitLine[3]);
                info.setUniprotAccession(pdbSplitLine[0]);
                info.setPdbAccession(pdbSplitLine[3]);
                info.setName(pdbSplitLine[4]);
                pdbFilesToReturn.add(info);
            }
        }
        return pdbFilesToReturn;
    }

    //TODO change this to actual xml parsing so we don't have to throw nullpointers around

    /**
     * get the pdb info part from a pdb file  on the pdb webserver
     * @param pdbAccession the accession to retrieve the info for
     * @return a pdbInfo object
     * @throws IOException
     * @throws XMLStreamException
     * @throws NullPointerException
     */
    public static PdbInfo getPdbHeaderForPdbAccession(String pdbAccession) throws IOException, XMLStreamException, NullPointerException {
        PdbInfo info = null;
        try (BufferedReader br = WebUtils.openReader("http://www.pdb.org/pdb/rest/describePDB?structureId=" + pdbAccession)){
        String pbdDescription = br.readLine();
        String[] splitXML = pbdDescription.split(" ");
        for (String anXMLPart : splitXML) {
            if (anXMLPart.contains("structureId")) {
                info = new PdbInfo(anXMLPart.split("=\"")[1].replaceAll("\"", ""));
                info.setPdbAccession(anXMLPart.split("=\"")[1].replaceAll("\"", ""));
            }

            if (anXMLPart.contains("title")) {
                info.setName(anXMLPart.split("=\"")[1].replaceAll("\"", ""));
            }
            if (anXMLPart.contains("resolution")) {
                info.setResolution(Double.parseDouble((anXMLPart.split("=")[1]).replaceAll("\"", "")));
            }
            if (anXMLPart.contains("expMethod")) {
                info.setMethod(anXMLPart.split("=")[1].replaceAll("\"", ""));
            }
        }
        }
        return info;
    }

    /**
     * retrieves the protein sequence from a pdb file passed as string
     * @param pdbFileInMem the pdb file to retrieve the sequence from
     * @return the protein sequence
     */
    public static String getSequenceFromPdbFile(String pdbFileInMem) {
        StringBuilder sequence = new StringBuilder();
        for (String pdbLine : pdbFileInMem.split("\n")) {
            if (pdbLine.startsWith("SEQRES")) {
                String[] splitLine = pdbLine.split(" ");
                for (int i = 8; i < splitLine.length; i++) {
                    sequence.append(splitLine[i]);
                }
            }
        }
        return sequence.toString();
    }

    //todo get gene ontology from http://www.pdb.org/pdb/rest/goTerms?structureId=4HHB

    //todo allow blasting against pdb
}
