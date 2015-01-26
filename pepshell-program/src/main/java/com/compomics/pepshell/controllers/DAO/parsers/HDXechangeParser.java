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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.controllers.DAO.parsers;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
class HDXechangeParser {
// we will return each replicate as a separate experiment. the condensing if needed is done elsewhere

    private static int columncounter = 0;

    public static List<Experiment> parseHDXechangeTabbedFile(File tabbedFile) throws IOException {
        List<Experiment> parsedExperiments = new ArrayList<>();
        LineNumberReader reader = new LineNumberReader(new FileReader(tabbedFile));
        String line;
        //first line contains the number of experiments we have to create
        if ((line = reader.readLine()) != null) {
            parsedExperiments = parseHeader(tabbedFile.getName().split(".")[0], line);

        }
        //sub headers, atm throwaway can be used later if number of columns is variable
        line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] results = line.split("\t");
            //first columns are general data for all the experiments
            Peptide basePeptide = new Peptide(results[2]);
            Iterator<Experiment> iter = parsedExperiments.iterator();
            for (int i = 5; results.length + columncounter > i; i += columncounter) {
                //a block of results
                if (!iter.hasNext()) {
                    //trouuuuuble
                    FaultBarrier.getInstance().handleException(new IOException());
                }
                Experiment currentExperiment = iter.next();
                // experiment should contain the only protein that is run on hd exchange in the file
                if (currentExperiment.getProteins().isEmpty()) {
                    currentExperiment.addProtein(new Protein(results[0]));
                }
                //to make sure no overlap is lost we just make a new group per peptide
                if (!results[i + 4].isEmpty()) {
                    currentExperiment.getProteins().get(0).addPeptideGroup(new PeptideGroup(new QuantedPeptide(basePeptide.getSequence(), 0.0, Double.parseDouble(results[i + 4]))));
                }
            }
        }
        return parsedExperiments;
    }

    private static List<Experiment> parseHeader(String filename, String line) {
        List<Experiment> experimentList = new ArrayList<>();
        String[] experiments = line.split("\t");
        String previousExperiment = "";
        int replicates = 1;
        for (String possibleExperiment : experiments) {
            if (!possibleExperiment.isEmpty()) {
                //lazy fix
                columncounter = 0;
                String title = filename + " - " + possibleExperiment;
                if (previousExperiment.contentEquals(possibleExperiment)) {
                    replicates++;
                } else {
                    replicates = 1;
                }
                title = title + " - replicate " + replicates;
                experimentList.add(new Experiment(title));
                previousExperiment = possibleExperiment;
            } else {
                columncounter++;
            }
        }
        return experimentList;
    }
}
