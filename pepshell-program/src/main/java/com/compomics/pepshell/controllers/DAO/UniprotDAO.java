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

import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Davy Maddelein
 */
class UniprotDAO {

    public static String fetchSequenceFromUniprot(String accession) throws IOException {
        StringBuilder sequence = new StringBuilder();
        String uniprotFasta = WebUtils.getHTMLPage("http://www.uniprot.org/uniprot/" + accession + ".fasta");
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
        uniprotConnection.setConnectTimeout(500);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(uniprotConnection.getInputStream(), "UTF-8"))) {
            while ((inputLine = br.readLine()) != null) {
                if (inputLine.contains(">")) {
                    //header for protein info object?
                } else {
                    // use saxparser if more info requested --> also if string empty what then?
                    sequence.append(inputLine);
                }
            }
        }
    }
}
