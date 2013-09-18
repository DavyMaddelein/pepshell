package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.controllers.AccessionConverter;
import com.compomics.peppi.model.DAS.DasFeature;
import com.compomics.peppi.model.PdbInfo;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.ConversionException;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 */
public class PDBDAO {

    public static Set<String> getPDBFileAccessionsForProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        String pdbLine;
        Set<String> pdbFilesToReturn = new HashSet<String>();
        URL pdbURL = new URL("http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=" + AccessionConverter.GIToUniprot(protein.getProteinAccession()));
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
        bw.write(URLController.readUrl("www.ebi.ac.uk/pdbe-srv/view/files/" + aPdbAccession+".ent"));
        bw.close();
        return pdbFile;
    }

    public static String getPdbFileInMem(String aPdbAccession) throws IOException{
        StringWriter pdbFile = new StringWriter();
        BufferedWriter bw = new BufferedWriter(pdbFile);
        bw.write(URLController.readUrl("http://www.ebi.ac.uk/pdbe-srv/view/files/" + aPdbAccession+".ent"));
        return pdbFile.toString();
    }
    
    private static Set<String> startUniprotPDBParsing(BufferedReader br) throws IOException {
        Set<String> pdbFilesToReturn = new HashSet<String>();
        String pdbLine = "#";
        while ((pdbLine = br.readLine()) != null && !pdbLine.startsWith("#")) {
            pdbFilesToReturn.add(pdbLine.split("\t")[3]);
        }
        return pdbFilesToReturn;
    }
    
    public static PdbInfo getPdbInfoForPdbAccession(String pdbAccession) throws IOException, XMLStreamException {
        List<DasFeature>pdbFeatures = new ArrayList<DasFeature>();
        XMLInputFactory xmlParseFactory = XMLInputFactory.newInstance();
        DasParser.getAllDasFeatures(xmlParseFactory.createXMLEventReader(new URL(String.format("http://www.ebi.ac.uk/das-svr/proteindas/das/pdbe_summary/features?segment=%s", pdbAccession)).openStream()));
        
        //List<DasFeature>pdbFeatures = DasParser.getAllDasFeatures(new URL("http://www.ebi.ac.uk/das-svr/proteindas/das/pdbe_summary/features?segment="+pdbAccession).openStream());
        
        for(DasFeature pdbFeature : pdbFeatures){
            pdbFeature.getMethod();
        }
        return null;
    }
}
