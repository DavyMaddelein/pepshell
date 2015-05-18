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

package com.compomics.pepshell.model.protein.proteinimplementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class Protease extends PepshellProtein {

    private StringBuilder startRegex = new StringBuilder();
    private StringBuilder stopRegex = new StringBuilder();
    private boolean semiSpecific = false;
    private int maxPeptideLength = 30;
    private int minPeptideLength = 3;

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids) {
        super(accession);
        createCleaveRegex(startRegex, startSpliceAminoAcids, Collections.EMPTY_LIST, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, Collections.EMPTY_LIST);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, boolean semispecific) {
        super(accession);
        this.semiSpecific = semispecific;
        createCleaveRegex(startRegex, startSpliceAminoAcids, Collections.EMPTY_LIST, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, Collections.EMPTY_LIST);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, boolean semispecific, int maxPeptideLength) {
        super(accession);
        this.maxPeptideLength = maxPeptideLength;
        this.semiSpecific = semispecific;
        createCleaveRegex(startRegex, startSpliceAminoAcids, Collections.EMPTY_LIST, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, Collections.EMPTY_LIST);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, List<String> startInhibitorAminoAcids, List<String> stopInhibitorAminoAcids) {
        super(accession);
        createCleaveRegex(startRegex, startSpliceAminoAcids, startInhibitorAminoAcids);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, stopInhibitorAminoAcids);
    }

    public Protease(String accession, List<String> startSpliceAminoAcids, List<String> stopSpliceAminoAcids, List<String> startInhibitorAminoAcids, List<String> stopInhibitorAminoAcids, boolean semispecific, int maxPeptideLength) {
        super(accession);
        this.maxPeptideLength = maxPeptideLength;
        this.semiSpecific = semispecific;
        createCleaveRegex(startRegex, startSpliceAminoAcids, startInhibitorAminoAcids, maxPeptideLength);
        createCleaveRegex(stopRegex, stopSpliceAminoAcids, stopInhibitorAminoAcids);
    }

    public boolean getSemiSpecific() {
        return semiSpecific;
    }

    public void setSemiSpecific(boolean semiSpecific) {
        this.semiSpecific = semiSpecific;
    }

    public List<String> digest(String proteinSequence) {
        List<String> peptideList = new ArrayList<>();
        //peptideList = proteinSequence. split with regex arrays.aslist();
        return peptideList;
    }

    private void createCleaveRegex(StringBuilder regexContainer, List<String> cleavageAminoAcids, List<String> restrictionAminoAcids) {
        for (String aminoAcid : cleavageAminoAcids) {
            regexContainer.append("|").append(aminoAcid);
        }
        if (regexContainer.length() != 0) {
            regexContainer.deleteCharAt(0);
            regexContainer.insert(0, "[").append("]");
        }
        if (!restrictionAminoAcids.isEmpty()) {
            regexContainer.append("(?=!");
            for (String restrictionAminoAcid : restrictionAminoAcids) {
                regexContainer.append("|").append(restrictionAminoAcid);
            }
            regexContainer.append(")");
        }
    }

    private void createCleaveRegex(StringBuilder regexContainer, List<String> cleavageAminoAcids, List<String> restrictionAminoAcids, int maxPeptideLength) {
        createCleaveRegex(regexContainer, cleavageAminoAcids, restrictionAminoAcids);
        regexContainer.append("{").append(minPeptideLength).append(",").append(maxPeptideLength).append("}");
    }
}
