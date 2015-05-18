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

import com.compomics.pepshell.model.protein.ProteinInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class Experiment {

    private final int experimentId;
    private final String experimentName;
    private List<PepshellProtein> proteinList = new ArrayList<>();
    private double maxIntensity = Double.MIN_VALUE;
    private double minIntensity = Double.MAX_VALUE;
    private double maxRatio = Double.MIN_VALUE;
    private double minRatio = Double.MAX_VALUE;

    public Experiment(String experimentName) {
        this.experimentId = -1;
        this.experimentName = experimentName;
    }

    public Experiment(int experimentId, String experimentName) {
        this.experimentId = experimentId;
        this.experimentName = experimentName;
    }

    /**
     * returns the experiment identifier
     *
     * @return the experiment identifier
     */
    public int getExperimentId() {
        return experimentId;
    }

    /**
     * sets a list of grouped peptides as this experiments results
     *
     * @param aListOfProteins the proteins to add
     */
    public void setProteins(List<? extends PepshellProtein> aListOfProteins) {
        proteinList.clear();
        proteinList.addAll(aListOfProteins);
    }

    /**
     * adds a list of proteins to the list already connected to this experiment
     *
     * @param proteins the proteins to add
     */
    public void addProteins(List<? extends PepshellProtein> proteins) {
        proteinList.addAll(proteins);
    }

    /**
     * returns an unmodifiable list of the proteins connected to this experiment
     *
     * @return an unmodifiable list of {@link ProteinInterface}
     */
    public List<PepshellProtein> getProteins() {
        return Collections.unmodifiableList(proteinList);
    }

    /**
     * add a protein to this experiment
     *
     * @param aProtein the protein to add
     */
    public void addProtein(PepshellProtein aProtein) {
        if (!proteinList.contains(aProtein)) {
            proteinList.add(aProtein);
        } else {
            proteinList.get(proteinList.indexOf(aProtein)).addPeptideGroups(aProtein.getPeptideGroups());
        }
    }

    public String getExperimentName() {
        return experimentName;
    }

    @Override
    public String toString() {
        return experimentName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.experimentId;
        hash = 61 * hash + (this.experimentName != null ? this.experimentName.hashCode() : 0);
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
        final Experiment other = (Experiment) obj;
        return this.experimentId == other.getExperimentId() && !((this.experimentName == null) ? (other.getExperimentName() != null) : !this.experimentName.equals(other.getExperimentName()));
    }

    public double getMaxIntensity() {
        return maxIntensity;
    }

    public void setMaxIntensity(double maxIntensity) {
        this.maxIntensity = maxIntensity;
    }

    public double getMinIntensity() {
        return minIntensity;
    }

    public void setMinIntensity(double minIntensity) {
        this.minIntensity = minIntensity;
    }

    public double getMaxRatio() {
        return maxRatio;
    }

    public void setMaxRatio(double aRatio) {
        maxRatio = aRatio;
    }

    public double getMinRatio() {
        return minRatio;
    }

    public void setMinRatio(double aRatio) {
        minRatio = aRatio;
    }
}
