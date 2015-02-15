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

package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class HomologueFinder {

    private static HashSet<String> findHomologueForNcbiAccession(String aNcbiAccession) throws IOException {
        String htmlPage = WebUtils.getPage("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=homologene&term=" + aNcbiAccession);
        String count = htmlPage.substring(htmlPage.indexOf("<Count>") + 7, htmlPage.indexOf("</Count>"));
        if(count.equalsIgnoreCase("1") ){
            String urlMake = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=homologene&id=" + htmlPage.substring(htmlPage.indexOf("<Id>") + 4, htmlPage.indexOf("</Id>"));
            htmlPage = WebUtils.getPage(urlMake);
        }
        return findHomologuesFromHtml(htmlPage);
    }

    public static HashMap<String,HashSet<String>> findHomologueForNcbiAccessions(ArrayList<String> NcbiAccessions) throws IOException {
        HashMap<String,HashSet<String>>  accessionsToHomologues = new HashMap<>();
        for (String aNcbiAccession : NcbiAccessions) {
            accessionsToHomologues.put(aNcbiAccession,findHomologueForNcbiAccession(aNcbiAccession));
        }
        return accessionsToHomologues;
    }

    private static HashSet<String> findHomologuesFromHtml(String aHtml){
        int startPos = 0;
        HashSet<String> accessions = new HashSet<>();
        while(aHtml.indexOf("prot-acc", startPos) > 0){
            String acc = aHtml.substring(aHtml.indexOf("prot-acc &quot;",startPos) + 15 , aHtml.indexOf("&quot;",aHtml.indexOf("prot-acc &quot;",startPos) + 15));
            accessions.add(acc);
            startPos = aHtml.indexOf("prot-len", startPos) + 5;
        }
        return accessions;
    }
}