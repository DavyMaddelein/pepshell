package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 */
public class PDBDAO {

    public static Set<PdbInfo> getPDBFileAccessionsForProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        String pdbLine;
        Set<PdbInfo> pdbFilesToReturn = new HashSet<PdbInfo>();
        URL pdbURL = new URL("http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=" + AccessionConverter.ToUniprot(protein.getVisibleAccession()));
        URLConnection pdbFileConnection = pdbURL.openConnection();
        BufferedReader br = new BufferedReader(new LineNumberReader(new InputStreamReader(pdbFileConnection.getInputStream(), "UTF-8")));
        while ((pdbLine = br.readLine()) != null) {
            if (pdbLine.contains("Uniprot to PDB mapping")) {
                pdbFilesToReturn = startUniprotPDBParsing(br);
            }
        }
        return pdbFilesToReturn;
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
        URL pdbURL = new URL("http://www.pdb.org/pdb/rest/describePDB?structureId=" + pdbAccession);
        URLConnection pdbFileConnection = pdbURL.openConnection();
        BufferedReader br = new BufferedReader(new LineNumberReader(new InputStreamReader(pdbFileConnection.getInputStream(), "UTF-8")));

        return null;
    }

    //get uniprot accessions from pdb from http://www.pdb.org/pdb/rest/describeMol?structureId=4hhb
    public static Set<PdbInfo> getPdbFileForProteinFromRepository(Protein protein) throws SQLException {
        Set<PdbInfo> pdbNames = new HashSet<PdbInfo>();
        SQLStatements.getPdbFilesFromDb();
        return pdbNames;
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
}
