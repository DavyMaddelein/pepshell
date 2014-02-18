package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 */
public class PDBDAO {

    private static PDBDAO instance;

    public static PDBDAO getInstance() {
        if (instance == null) {
            instance = new PDBDAO();
        }
        return instance;
    }

    public Set<PdbInfo> getPDBInfoForProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        String pdbLine;
        Set<PdbInfo> pdbInfoToReturn = new HashSet<>();
        BufferedReader br = URLController.openReader("http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=" + AccessionConverter.toUniprot(protein.getVisibleAccession()));
        while ((pdbLine = br.readLine()) != null) {
            if (pdbLine.contains("Uniprot to PDB mapping")) {
                pdbInfoToReturn = startUniprotPDBParsing(br);
            }
        }
        return pdbInfoToReturn;
    }

    public static File getPdbFile(String aPdbAccession) throws IOException {
        File pdbFile = File.createTempFile(aPdbAccession, ".pdb");
        pdbFile.deleteOnExit();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pdbFile), "UTF-8"));
        bw.write(URLController.readUrl("www.ebi.ac.uk/pdbe-srv/view/files/" + aPdbAccession.toLowerCase() + ".ent"));
        bw.close();
        return pdbFile;
    }

    public static String getPdbFileInMem(String aPdbAccession) throws IOException {
        StringWriter pdbFile = new StringWriter();
        if (aPdbAccession != null) {
            BufferedWriter bw = new BufferedWriter(pdbFile);
            bw.write(URLController.readUrl("http://www.ebi.ac.uk/pdbe-srv/view/files/" + aPdbAccession.toLowerCase() + ".ent"));
        }
        return pdbFile.toString();
    }

    private static Set<PdbInfo> startUniprotPDBParsing(BufferedReader br) throws IOException {
        Set<PdbInfo> pdbFilesToReturn = new HashSet<PdbInfo>();
        String pdbLine = "#";
        while ((pdbLine = br.readLine()) != null) {
            if (!pdbLine.startsWith("#")) {
                String[] pdbSplitLine = pdbLine.split("\t");
                PdbInfo info = new PdbInfo();
                info.setUniprotAccession(pdbSplitLine[0]);
                info.setPdbAccession(pdbSplitLine[3]);
                info.setName(pdbSplitLine[4]);
                pdbFilesToReturn.add(info);
            }
        }
        return pdbFilesToReturn;
    }

    public static PdbInfo getPdbInfoForPdbAccession(String pdbAccession) throws IOException, XMLStreamException {
        PdbInfo info = new PdbInfo();
        BufferedReader br = URLController.openReader("http://www.pdb.org/pdb/rest/describePDB?structureId=" + pdbAccession);
        String pbdDescription = br.readLine();
        String[] splitXML = pbdDescription.split(" ");
        for (String anXMLPart : splitXML) {
            if (anXMLPart.contains("structureId")) {
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
        return info;
    }

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

    //future feature, get gene ontology from http://www.pdb.org/pdb/rest/goTerms?structureId=4HHB
}
