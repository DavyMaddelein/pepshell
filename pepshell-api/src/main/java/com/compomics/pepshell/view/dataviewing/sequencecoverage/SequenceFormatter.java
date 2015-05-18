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

package com.compomics.pepshell.view.dataviewing.sequencecoverage;

import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.exceptions.FormattingException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Davy Maddelein on 03/04/2015.
 */
public class SequenceFormatter {

    private static int aminoAcidNumberBetweenEachDivide = 10;
    private static Character dividingCharacter = '\t';
    private static String peptideColor = "FF530D";
    private static String domainColor = "9C9C9C";


    /**
     * formats a sequence string with the parameters set at the time of calling
     * uses the number set for the division between each amino acid group and the dividing character to be used between each group
     *
     * @param sequence the sequence to format
     * @return the formatted sequence
     */
    public String formatSequenceString(String sequence) {
        StringBuilder formattedOutputter = new StringBuilder();
        //final array with one element because everything inside a lambda must be final
        int counter = 0;

        for(Character aChar : sequence.toCharArray()){
            if (counter != aminoAcidNumberBetweenEachDivide){
                formattedOutputter.append(aChar);
                counter++;
            } else {
                formattedOutputter.append(dividingCharacter).append(aChar);
                counter = 1;
            }
        }
        return formattedOutputter.toString();
    }

    /**
     * formats
     *
     * @param proteinToFormat
     * @return
     */
    public String formatSequenceWithProteinInformation(PepshellProtein proteinToFormat) throws FormattingException {
        String bareSequence = formatSequenceString(proteinToFormat.getProteinSequence());

        Map<String, String> attributes = new HashMap<>();

        String domainsAdded = addFeatures(proteinToFormat.getDomains(), bareSequence.split(String.valueOf(dividingCharacter)), setDomainAttributes(attributes));

        List<FeatureWithLocation> peptideFeatures = proteinToFormat.getPeptideGroups()
                .stream()
                .map(PeptideGroup::getRepresentativePeptide)
                .map(e -> new FeatureWithLocation("peptide", e.getBeginningProteinMatch(), e.getEndProteinMatch()))
                .collect(Collectors.toList());

        return addFeatures(peptideFeatures, domainsAdded.split(String.valueOf(dividingCharacter)), setPeptideAttributes(attributes));
    }

    public String addFeatures(List<FeatureWithLocation> features, String[] splitSequences, Map<String, String> attributes) throws FormattingException {

        for (FeatureWithLocation aFeature : features) {

            //add start
            int block = (int) Math.floor(aFeature.getStartPosition() / aminoAcidNumberBetweenEachDivide);
            splitSequences[block] = addTagToSequence("div", splitSequences[block], aFeature.getStartPosition() - block, false, attributes);

            //add end
            block = (int) Math.floor(aFeature.getEndPosition() / aminoAcidNumberBetweenEachDivide);
            splitSequences[block] = addTagToSequence("div", splitSequences[block], aFeature.getEndPosition() - block, true, attributes);

        }
        return String.join("", splitSequences);
    }

    private String addTagToSequence(String tag, String toInject, int locationInString, boolean isClosing, Map<String, String> attributes) throws FormattingException {
        int charCounter = 0;
        //in case people REALLY REALLY want to use tags inside tags for some reason
        int insideTag = 0;
        StringBuilder injectedString = new StringBuilder();

        for (Character aChar : toInject.toCharArray()) {
            if (aChar.equals('<')) {
                //stop counter
                insideTag++;

            } else if (insideTag > 0 && aChar.equals('>')) {
                insideTag--;

            } else if (insideTag <= 0 && aChar.equals('>')) {
                //something went wrong with the formatting
                throw new FormattingException("closing tag without opening tag");

            } else if (charCounter > locationInString) {
                if (isClosing) {
                    injectedString.append("</").append(tag).append(">").append(aChar);
                    charCounter++;
                } else {
                    //we format the string <tag attributes... with attributes in key=value style>
                    injectedString.append(String.format("<%s %s>", tag, attributes.entrySet()
                            .stream()
                            .map(e -> String.format("%s=\"%s\" ", e.getKey(), e.getValue()))
                            .reduce("", String::concat)))
                            .append(aChar);
                    charCounter++;
                }


            } else {
                injectedString.append(aChar);
                charCounter++;
            }
        }
        return injectedString.toString();
    }

    private Map<String, String> setDomainAttributes(Map<String, String> domainAttributes) {
        domainAttributes.clear();
        domainAttributes.put("class", "domain");
        domainAttributes.put("color", "#" + domainColor);
        return domainAttributes;
    }


    private Map<String, String> setPeptideAttributes(Map<String, String> peptideAttributes) {
        peptideAttributes.clear();
        peptideAttributes.put("class", "peptide");
        peptideAttributes.put("color", "#" + peptideColor);
        return peptideAttributes;
    }

    public SequenceFormatter setAminoAcidNumberBetweenEachDivide(int aminoAcidNumberBetweenEachDivide) {
        SequenceFormatter.aminoAcidNumberBetweenEachDivide = aminoAcidNumberBetweenEachDivide;
        return this;
    }

    public SequenceFormatter setPeptideColor(String aPeptideColorInHex) {
        peptideColor = aPeptideColorInHex;
        return this;
    }

    public SequenceFormatter setDomainColor(String aDomainColorInHex) {
        domainColor = aDomainColorInHex;
        return this;
    }

    public SequenceFormatter setDividingCharacter(Character dividingCharacter) {
        SequenceFormatter.dividingCharacter = dividingCharacter;
        return this;
    }
}
