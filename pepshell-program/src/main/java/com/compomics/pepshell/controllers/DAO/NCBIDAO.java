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

import java.io.IOException;

/**
 *
 * @author Davy Maddelein
 */
class NCBIDAO {
    
        public static String fetchSequenceFromNCBI(String accession) throws IOException {
        StringBuilder sequence = new StringBuilder();
                String splitaccession = accession.substring(accession.indexOf("|")+1);
            String uniprotFasta = WebUtils.getPage("http://www.ncbi.nlm.nih.gov/protein/" + splitaccession + "?report=fasta&log$=seqview&format=text");
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
