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

package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.controllers.DAO.DAUtils.FileUtils;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.UpdateMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class CPDTAnalysis extends DataRetrievalStep {

    private CPDTAnalysis(List<PepshellProtein> aPepshellProteinList) {
        this.pepshellProteinList = aPepshellProteinList;
    }

    public CPDTAnalysis() {

    }

    @Override
    public DataRetrievalStep getInstance(List<PepshellProtein> aPepshellProteinList) {
        return new CPDTAnalysis(aPepshellProteinList);
    }

    @Override
    public List<PepshellProtein> call() throws Exception {
        //todo add location to preferences so people can define their own location
        URL CPDTLocationURL = CPDTAnalysis.class.getClassLoader().getResource("CPDT.exe");
        File CPDTLocation = null;
        if (CPDTLocationURL != null) {
            CPDTLocation = new File(CPDTLocationURL.getPath());
        }
        if (CPDTLocation == null || !CPDTLocation.exists()) {
            //if cannot be found on the classpath try the original folder
            CPDTLocation = new File(CPDTAnalysis.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            CPDTLocation = new File(CPDTLocation.getParentFile().getAbsolutePath() + "/resources/CPDT.exe");
        }
        final File CPDTFolder = new File(System.getProperty("java.io.tmpdir"), "CPDToutput");
        if (!CPDTFolder.exists()) {
            CPDTFolder.mkdir();
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                FileUtils.recursivelyDeleteFilesFromDir(CPDTFolder);
            }
        });

        for (PepshellProtein aPepshellProtein : pepshellProteinList) {
            this.setChanged();
            this.notifyObservers(new UpdateMessage(false, "running CP-DT on " + aPepshellProtein.getVisibleAccession(), false));
            String filename = System.currentTimeMillis() + aPepshellProtein.getVisibleAccession();
            filename = filename.replaceAll("\\|", "");
            File outputFile = new File(CPDTFolder, filename);
            ProcessBuilder builder = new ProcessBuilder(CPDTLocation.getAbsolutePath(), "--all", "--sequence", aPepshellProtein.getProteinSequence());
            builder.redirectOutput(outputFile);
            builder.start().waitFor();
            aPepshellProtein.setCPDTPeptideList(parseCPDTOutput(outputFile, aPepshellProtein));
            this.setChanged();
            this.notifyObservers(new UpdateMessage(true, "done running CP-DT on " + aPepshellProtein.getVisibleAccession(), false));
        }
        return Collections.unmodifiableList(pepshellProteinList);
    }

    private List<PeptideGroup> parseCPDTOutput(File CPDTFile, PepshellProtein aPepshellProtein) throws FileNotFoundException, IOException {
        List<PeptideGroup> CPDTPeptideGroups = new ArrayList<>();
        LineNumberReader reader = new LineNumberReader(new FileReader(CPDTFile));
        String line;
        int previousCut = 0;
        while ((line = reader.readLine()) != null) {
            if (line.equalsIgnoreCase("cut position information")) {
                reader.readLine();
                while ((line = reader.readLine()) != null && line.contains("cut position")) {
                    //string is of format cut position = 866;     probability = 0.881353
                    //could be replaced with a split on = and then [2] and [4] for better readability if needed
                    String[] cutData = line.split(";");
                    Peptide cutPeptide = new Peptide(aPepshellProtein.getProteinSequence().substring(previousCut, Integer.parseInt(cutData[0].split("=")[1].trim())), Double.parseDouble(cutData[1].split("=")[1].trim()));
                    cutPeptide.setBeginningProteinMatch(Integer.parseInt(cutData[0].split("=")[1].trim()));
                    cutPeptide.setProbability(Double.parseDouble(cutData[1].split("=")[1].trim()));
                    previousCut = cutPeptide.getBeginningProteinMatch();
                    PeptideGroup CPDTPeptideGroup = new PeptideGroup(cutPeptide);
                    CPDTPeptideGroup.setShortestPeptideIndex(0);
                    CPDTPeptideGroups.add(CPDTPeptideGroup);
                }
            }
        }
        return CPDTPeptideGroups;
    }

    @Override
    public String getRetrievalStepDescription() {
        return "CPDT cleavage analysis";
    }
}
