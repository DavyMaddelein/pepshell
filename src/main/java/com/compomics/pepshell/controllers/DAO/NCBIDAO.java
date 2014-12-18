package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;

import java.io.IOException;

/**
 *
 * @author Davy Maddelein
 */
class NCBIDAO {
    
        public static String fetchSequenceFromNCBI(String accession) throws IOException {
        StringBuilder sequence = new StringBuilder();
                String splitaccession = accession.substring(accession.indexOf("|")+1);
        String uniprotFasta = WebUtils.getHTMLPage("http://www.ncbi.nlm.nih.gov/protein/" + splitaccession + "?report=fasta&log$=seqview&format=text");
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
    
}
