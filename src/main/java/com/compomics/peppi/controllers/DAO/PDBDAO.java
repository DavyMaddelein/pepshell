package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.model.DAS.DasFeature;
import com.compomics.peppi.model.PdbInfo;
import com.compomics.peppi.model.Protein;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class PDBDAO {

    public static Set<String> getPDBFileAccessionsForProtein(Protein protein) throws MalformedURLException, IOException {
        String pdbLine;
        Set<String> pdbFilesToReturn = new HashSet<String>();
        URL pdbURL = new URL("http://www.ebi.ac.uk/pdbe-apps/widgets/unipdb?tsv=1&uniprot=" + protein.getProteinAccession());
        URLConnection pdbFileConnection = pdbURL.openConnection();
        BufferedReader br = new BufferedReader(new LineNumberReader(new InputStreamReader(pdbFileConnection.getInputStream(), "UTF-8")));
        while ((pdbLine = br.readLine()) != null) {
            if (pdbLine.contains("Uniprot to PDB mapping")) {
                pdbFilesToReturn = startUniprotParsing(br);
            }
        }
        return pdbFilesToReturn;
    }

    public static File getPdbFile(String aPdbAccession) throws IOException {
        File pdbFile = File.createTempFile(aPdbAccession, ".pdb");
        pdbFile.deleteOnExit();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pdbFile), "UTF-8"));
        bw.write(URLController.readUrl("http://www.rcsb.org/pdb/files/" + aPdbAccession));
        bw.close();
        return pdbFile;
    }

    private static Set<String> startUniprotParsing(BufferedReader br) throws IOException {
        Set<String> pdbFilesToReturn = new HashSet<String>();
        String pdbLine;
        while ((pdbLine = br.readLine()) != null && !pdbLine.contains("#")) {
            pdbFilesToReturn.add(pdbLine.split("\t")[3]);
        }
        return pdbFilesToReturn;
    }
    
    public static PdbInfo getPdbInfoForPdbAccession(String pdbAccession) throws IOException {
        List<DasFeature>pdbFeatures = DasParser.getAllDasFeatures(URLController.readUrl("http://www.ebi.ac.uk/das-svr/proteindas/das/pdbe_summary/features?segment="+pdbAccession));
        
        for(DasFeature pdbFeature : pdbFeatures){
            pdbFeature.getMethod();
        }
        return null;
    }
}
