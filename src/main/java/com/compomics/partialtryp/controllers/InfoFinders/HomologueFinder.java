package com.compomics.partialtryp.controllers.InfoFinders;

import com.compomics.partialtryp.controllers.DAO.URLController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class HomologueFinder {

    public static HashSet<String> findHomologueForNcbiAccession(String aNcbiAccession) throws IOException {
        String htmlPage = URLController.readUrl("http:eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=homologene&term=" + aNcbiAccession);
        String count = htmlPage.substring(htmlPage.indexOf("<Count>") + 7, htmlPage.indexOf("</Count>"));
        if(count.equalsIgnoreCase("1") ){
            String urlMake = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=homologene&id=" + htmlPage.substring(htmlPage.indexOf("<Id>") + 4, htmlPage.indexOf("</Id>"));
            htmlPage = URLController.readUrl(urlMake);
        }
        return findHomologuesFromHtml(htmlPage);
    }

    public static HashMap<String,HashSet<String>> findHomologueForNcbiAccessions(ArrayList<String> NcbiAccessions) throws IOException {
        HashMap<String,HashSet<String>>  accessionsToHomologues = new HashMap<String, HashSet<String>>();
        for (String aNcbiAccession : NcbiAccessions) {
            accessionsToHomologues.put(aNcbiAccession,findHomologueForNcbiAccession(aNcbiAccession));
        }
        return accessionsToHomologues;
    }

    public static HashSet<String> findHomologuesFromHtml(String aHtml){
        int startPos = 0;
        HashSet<String> accessions = new HashSet<String>();
        while(aHtml.indexOf("prot-acc", startPos) > 0){
            String acc = aHtml.substring(aHtml.indexOf("prot-acc &quot;",startPos) + 15 , aHtml.indexOf("&quot;",aHtml.indexOf("prot-acc &quot;",startPos) + 15));
            accessions.add(acc);
            startPos = aHtml.indexOf("prot-len", startPos) + 5;
        }
        return accessions;
    }
}