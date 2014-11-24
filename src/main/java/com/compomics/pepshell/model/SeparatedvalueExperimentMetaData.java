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

/**
 * @author Davy Maddelein
 */
public class SeparatedvalueExperimentMetaData {

    private boolean hasHeaders;
    private int proteinAccessionColumn;
    private int peptideSequence;
    private int columnsPerExperiment;
    private boolean hasMultipleExperimentsPerFile;
    private int ratioColumn;
    private int lightIntensityColumn;
    private int heavyIntensityColumn;
    private int intensityColumn;
    private boolean splitPeptidesOnIntensity;
    private String valueSeparator;
    private int firstExperimentColumn;
    private int lastExperimentColumn;

    public boolean fileHasHeaders() {
        return hasHeaders;
    }

    public int getProteinAccessionColumn() {
        return proteinAccessionColumn;
    }

    public int getPeptideSequence() {
        return peptideSequence;
    }

    public int getColumnsPerExperiment() {
        return columnsPerExperiment;
    }

    public boolean hasMultipleExperimentsPerFile() {
        return hasMultipleExperimentsPerFile;
    }

    public int getRatioColumn() {
        return ratioColumn;
    }

    public int getLightIntensityColumn() {
        return lightIntensityColumn;
    }

    public int getHeavyIntensityColumn() {
        return heavyIntensityColumn;
    }

    public int getIntensityColumn() {
        return intensityColumn;
    }

    public boolean splitPeptidesOnIntensity() {
        return splitPeptidesOnIntensity;
    }


    public int getFirstExperimentColumn() {
        return firstExperimentColumn;
    }

    public int getLastExperimentColumn() {
        return lastExperimentColumn;
    }

    public SeparatedvalueExperimentMetaData setHasHeaders(boolean fileHasHeaders) {
        hasHeaders = fileHasHeaders;
        return this;
    }

    public SeparatedvalueExperimentMetaData setProteinAccessionColumn(int aColumn) {
        proteinAccessionColumn = aColumn;
        return this;
    }

    public SeparatedvalueExperimentMetaData setPeptidesequenceColumn(int aColumn) {
        peptideSequence = aColumn;
        return this;
    }

    public SeparatedvalueExperimentMetaData setColumnsForEachExperiment(int numberOfColumns) {
        this.setMultipleExperimentsPerFile(true);
        columnsPerExperiment = numberOfColumns;
        return this;
    }

    private SeparatedvalueExperimentMetaData setMultipleExperimentsPerFile(boolean b) {
        hasMultipleExperimentsPerFile = b;
        return this;
    }

    public SeparatedvalueExperimentMetaData setRatioColumn(int aColumn) {
        ratioColumn = aColumn;
        return this;
    }

    public SeparatedvalueExperimentMetaData setLightIntensityColumn(int aColumn) {
        this.setSplitPeptidesOnIntensity(true);
        lightIntensityColumn = aColumn;
        return this;
    }

    public SeparatedvalueExperimentMetaData setHeavyIntensityColumn(int aColumn) {
        this.setSplitPeptidesOnIntensity(true);
        heavyIntensityColumn = aColumn;
        return this;
    }

    public SeparatedvalueExperimentMetaData setIntensityColumn(int aColumn) {
        this.setSplitPeptidesOnIntensity(false);
        intensityColumn = aColumn;
        return this;
    }

    public SeparatedvalueExperimentMetaData setFirstExperimentColumn(int aColumn) {
        firstExperimentColumn = aColumn;
        return this;
    }


    public SeparatedvalueExperimentMetaData setLastExperimentColumn(int aColumn) {
        lastExperimentColumn = aColumn;
        return this;
    }

    private void setSplitPeptidesOnIntensity(boolean b) {
        splitPeptidesOnIntensity = b;
    }

    public String getValueSeparator() {
        return valueSeparator;
    }

    public SeparatedvalueExperimentMetaData setValueSeparator(String aSeparator) {
        valueSeparator = aSeparator;
        return this;
    }

}
