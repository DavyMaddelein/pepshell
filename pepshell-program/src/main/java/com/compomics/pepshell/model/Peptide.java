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

package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class Peptide implements PeptideInterface {

    private final String sequence;
    private int beginningProteinMatch = -1;
    private int endProteinMatch = -1;
    private int timesFound = 1;
    private boolean isMiscleaved;
    private List<Double> totalSpectrumIntensity = new ArrayList<>();
    private double probability;

    /**
     * create a peptide object instance
     *
     * @param sequence the peptide sequence
     */
    public Peptide(String sequence) {
        this.sequence = sequence;
    }

    public Peptide(String sequence, Double aSpectrumIntensity) {
        this.sequence = sequence;
        totalSpectrumIntensity.add(aSpectrumIntensity);
    }

    @Override
    public String getSequence() {
        return this.sequence;
    }

    @Override
    public int getBeginningProteinMatch() {
        return beginningProteinMatch;
    }

    @Override
    public void setBeginningProteinMatch(int beginningProteinMatch) {
        this.beginningProteinMatch = beginningProteinMatch;
    }

    @Override
    public int getEndProteinMatch() {
        return endProteinMatch;
    }

    @Override
    public void setEndProteinMatch(int endProteinMatch) {
        this.endProteinMatch = endProteinMatch;
    }

    @Override
    public int getTimesFound() {
        return timesFound;
    }

    @Override
    public void setTimesFound(int timesFound) {
        this.timesFound = timesFound;
    }

    @Override
    public void incrementTimesFound() {
        timesFound++;
    }

    @Override
    public boolean isIsMiscleaved() {
        return isMiscleaved;
    }

    @Override
    public void setIsMiscleaved(boolean isMiscleaved) {
        this.isMiscleaved = isMiscleaved;
    }

    @Override
    public List<Double> getTotalSpectrumIntensities() {
        return totalSpectrumIntensity;
    }

    @Override
    public void addTotalSpectrumIntensity(Double aTotalSpectrumIntensity) {
        totalSpectrumIntensity.add(aTotalSpectrumIntensity);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        hash = 71 * hash + this.beginningProteinMatch;
        hash = 71 * hash + this.endProteinMatch;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Peptide other = (Peptide) obj;
        return !((this.sequence == null) ? (other.getSequence() != null) : !this.sequence.equals(other.getSequence())) && this.beginningProteinMatch == other.getBeginningProteinMatch() && this.endProteinMatch == other.getEndProteinMatch();
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
