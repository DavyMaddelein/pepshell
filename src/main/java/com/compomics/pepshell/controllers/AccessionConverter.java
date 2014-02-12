package com.compomics.pepshell.controllers;

import com.compomics.pepshell.controllers.DAO.URLController;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/4/13 Time: 11:16 AM To change
 * this template use File | Settings | File Templates.
 */
public class AccessionConverter {

    public static List<String> spToUniProt(String aSpAccession) throws IOException {
        String xmlPage = URLController.readUrl("http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + aSpAccession + "&database=SWISSPROT");
        List<String> accessions = new ArrayList<String>();
        int startIndex = 0;
        while (xmlPage.indexOf("<ns2:databaseDescription>UniProtKB/Swiss-Prot</ns2:databaseDescription>", startIndex) != -1) {
            startIndex = xmlPage.indexOf("<ns2:databaseDescription>UniProtKB/Swiss-Prot</ns2:databaseDescription>", startIndex) + 1;
            String feature = xmlPage.substring(xmlPage.indexOf("<ns2:identicalCrossReferences>", startIndex - 200), xmlPage.indexOf("</ns2:identicalCrossReferences>", xmlPage.indexOf("<ns2:identicalCrossReferences>", startIndex - 200)));
            String sub = feature.substring(feature.indexOf("<ns2:accession>") + 15, feature.indexOf("</ns2:accession>"));
            accessions.add(sub);

        }
        return accessions;
    }

    public static List<String> refSeqToSp(String aNcbiAccession) throws IOException {
        int startIndex = 0;
        List<String> accessions = new ArrayList<String>();
        String xmlPage = URLController.readUrl("http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + aNcbiAccession + "&database=swissprot");
        while (xmlPage.indexOf("<ns2:databaseDescription>SWISSPROT</ns2:databaseDescription>", startIndex) != -1) {
            startIndex = xmlPage.indexOf("<ns2:databaseDescription>SWISSPROT</ns2:databaseDescription>", startIndex) + 1;
            String feature = xmlPage.substring(xmlPage.indexOf("<ns2:logicalCrossReferences>", startIndex - 200), xmlPage.indexOf("</ns2:logicalCrossReferences>", xmlPage.indexOf("<ns2:logicalCrossReferences>", startIndex - 200)));
            String sub = feature.substring(feature.indexOf("<ns2:accession>") + 15, feature.indexOf("</ns2:accession>"));
            accessions.add(sub);

        }
        return accessions;
    }

    public static List<String> spToRefSeq(String aSpAccession) throws IOException {
        int startIndex = 0;
        List<String> ncbiAccessions = new ArrayList<String>();
        String xmlPage = URLController.readUrl("http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + aSpAccession + "&database=REFSEQ");
        while (xmlPage.indexOf("<ns2:databaseDescription>RefSeq release + updates</ns2:databaseDescription>", startIndex) != -1) {
            startIndex = xmlPage.indexOf("<ns2:databaseDescription>RefSeq release + updates</ns2:databaseDescription>", startIndex) + 1;
            String feature = xmlPage.substring(xmlPage.indexOf("<ns2:identicalCrossReferences>", startIndex - 200), xmlPage.indexOf("</ns2:identicalCrossReferences>", xmlPage.indexOf("<ns2:identicalCrossReferences>", startIndex - 200)));
            String sub = feature.substring(feature.indexOf("<ns2:accession>") + 15, feature.indexOf("</ns2:accession>"));
            ncbiAccessions.add(sub);

        }
        return ncbiAccessions;
    }

    public static List<String> uniProtToSp(String aUniprotAccession) {
        int startIndex = 0;
        List<String> accessions = new ArrayList<String>();

        return accessions;
    }

    public static List<String> uniProtToRefSeq(String aUniprotAccession) {
        int startIndex = 0;
        List<String> accessions = new ArrayList<String>();

        return accessions;
    }

    public static String GIToUniprot(String GINumber) throws IOException, ConversionException {
        String splitaccession = GINumber.substring(GINumber.indexOf("|") + 1);
        String conversion = URLController.readUrl("http://www.uniprot.org/mapping/?from=P_GI&to=ACC&format=tab&query=" + splitaccession);
        conversion = conversion.substring(conversion.lastIndexOf("\t") + 1, conversion.lastIndexOf("\n"));
        if (!conversion.matches("To")) {
            return conversion;
        } else {
            throw new ConversionException("Could not convert GI number");
        }
    }
    //TODO finish this, needs to take any accession and try to convert to uniprot

    public static String toUniprot(String accession) throws IOException, ConversionException {
        String tempaccession = accession;
        if (isGIAccession(accession)) {
            tempaccession = GIToUniprot(accession);
        } else if (isSwissprotAccession(accession)) {
            //TODO check if it is possible to get the isoform data from swissprot to uniprot
            // tempaccession = spToUniProt(accession).get(0);
            //}
        }
        return tempaccession;
    }

    private static boolean isGIAccession(String accession) {
        return accession.toLowerCase().contains("gi");
        //return accession.matches("^(GI|gi)\\|[0-9]");
    }

    private static boolean isSwissprotAccession(String accession) {
        return accession.matches("something something something");
    }
}
