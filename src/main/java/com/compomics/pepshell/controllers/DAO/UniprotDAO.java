package com.compomics.pepshell.controllers.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Davy
 */
public class UniprotDAO {

    public static String fetchSequenceFromUniprot(String accession) throws IOException {
        StringBuilder sequence = new StringBuilder();
        String uniprotFasta = URLController.readUrl("http://www.uniprot.org/uniprot/" + accession + ".fasta");
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

    public static void parseUniprotXML(String accession) throws IOException {
        String inputLine;
        StringBuilder sequence = new StringBuilder();
        URL uniprotURL = new URL("http://www.uniprot.org/uniprot/" + accession + ".fasta");
        URLConnection uniprotConnection = uniprotURL.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(uniprotConnection.getInputStream(), "UTF-8"));
        while ((inputLine = br.readLine()) != null) {
            if (inputLine.contains(">")) {
                //header for protein info object?
            } else {
                // use saxparser if more info requested --> also if string empty what then?
                sequence.append(inputLine);
            }
        }
        br.close();
    }
}
