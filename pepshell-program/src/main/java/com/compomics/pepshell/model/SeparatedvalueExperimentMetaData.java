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

import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.view.PossibleMetaDataAnnotationsEnum;
import com.compomics.pepshell.model.exceptions.ExperimentMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Davy Maddelein
 */
public class SeparatedValueExperimentMetadata extends ExperimentMetaData {

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
    private boolean experimentHasIntensityValues;
    private boolean experimentHasRatio;
    private int proteinSequenceColumn;
    private boolean hasPeptideLocationValues;
    private int peptideStartColumn;
    private int peptideEndColumn;
    private String missingValue;
    private boolean hasMissingValues = false;
    //todo change this to properties
    public SeparatedValueExperimentMetadata(DataSourceEnum aDataSource) {
        super(aDataSource);
    }

    public boolean fileHasHeaders() {
        return hasHeaders;
    }

    public int getProteinSequenceColumn() {
        return proteinSequenceColumn;
    }

    public int getProteinAccessionColumn() {
        return proteinAccessionColumn;
    }

    public int getPeptideSequenceColumn() {
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

    public SeparatedValueExperimentMetadata addMetaData(PossibleMetaDataAnnotationsEnum dataAnnotationsEnum, String aColumn) {
        switch (dataAnnotationsEnum) {
            case INTENSITY:
                setIntensityColumn(Integer.parseInt(aColumn));
                break;
            case SIZEOFEXPERIMENTCOLUMS:
                setColumnsForEachExperiment(Integer.parseInt(aColumn));
                break;
            case RATIO:
                setRatioColumn(Integer.parseInt(aColumn));
                break;
            case PEPTIDESEQUENCE:
                setPeptidesequenceColumn(Integer.parseInt(aColumn));
                break;
            case PROTEINACCESSION:
                setProteinAccessionColumn(Integer.parseInt(aColumn));
                break;
            case PROTEINSEQUENCE:
                setProteinSequenceColumn(Integer.parseInt(aColumn));
                break;
            case VALUESEPARATOR:
                setValueSeparator(aColumn);
                break;
            case HASHEADERS:
                setHasHeaders((Boolean.parseBoolean(aColumn)));
                break;
            case PEPTIDEENDLOCATION:
                setPeptideEndColumn(Integer.parseInt(aColumn));
                break;
            case PEPTIDESTARTLOCATION:
                setPeptideStartColumn(Integer.parseInt(aColumn));
            default:
                break;
        }

        return this;
    }

    public SeparatedValueExperimentMetadata setHasHeaders(boolean fileHasHeaders) {
        hasHeaders = fileHasHeaders;
        return this;
    }

    public SeparatedValueExperimentMetadata setProteinAccessionColumn(int aColumn) {
        proteinAccessionColumn = aColumn;
        return this;
    }

    public SeparatedValueExperimentMetadata setPeptidesequenceColumn(int aColumn) {
        peptideSequence = aColumn;
        return this;
    }

    public SeparatedValueExperimentMetadata setColumnsForEachExperiment(int numberOfColumns) {
        this.setMultipleExperimentsPerFile(true);
        columnsPerExperiment = numberOfColumns;
        return this;
    }

    private SeparatedValueExperimentMetadata setMultipleExperimentsPerFile(boolean b) {
        hasMultipleExperimentsPerFile = b;
        return this;
    }

    public SeparatedValueExperimentMetadata setRatioColumn(int aColumn) {
        ratioColumn = aColumn;
        this.experimentHasRatio = true;
        return this;
    }

    public SeparatedValueExperimentMetadata setLightIntensityColumn(int aColumn) {
        this.setSplitPeptidesOnIntensity(true);
        lightIntensityColumn = aColumn;
        return this;
    }

    public SeparatedValueExperimentMetadata setHeavyIntensityColumn(int aColumn) {
        this.setSplitPeptidesOnIntensity(true);
        this.experimentHasIntensityValues = true;
        heavyIntensityColumn = aColumn;
        return this;
    }

    public SeparatedValueExperimentMetadata setIntensityColumn(int aColumn) {
        this.setSplitPeptidesOnIntensity(false);
        this.experimentHasIntensityValues = true;
        intensityColumn = aColumn;
        return this;
    }

    public SeparatedValueExperimentMetadata setFirstExperimentColumn(int aColumn) {
        firstExperimentColumn = aColumn;
        return this;
    }

    public SeparatedValueExperimentMetadata setLastExperimentColumn(int aColumn) {
        lastExperimentColumn = aColumn;
        return this;
    }

    private void setSplitPeptidesOnIntensity(boolean b) {
        splitPeptidesOnIntensity = b;
    }

    public String getValueSeparator() {
        return valueSeparator;
    }

    public SeparatedValueExperimentMetadata setValueSeparator(String aSeparator) {
        valueSeparator = aSeparator;
        return this;
    }

    public boolean experimentHasIntensityValues() {
        return experimentHasIntensityValues;
    }

    public boolean experimentHasRatio() {
        return this.experimentHasRatio;
    }

    public Map<PossibleMetaDataAnnotationsEnum, String> getMetaDataAsMap() {
        Map<PossibleMetaDataAnnotationsEnum, String> metaDataMap = new HashMap<>();
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.VALUESEPARATOR, valueSeparator);
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.RATIO, Integer.toString(ratioColumn));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.HASHEADERS, Boolean.toString(hasHeaders));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.INTENSITY, Integer.toString(intensityColumn));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.PEPTIDESEQUENCE, Integer.toString(peptideSequence));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.PROTEINACCESSION, Integer.toString(proteinAccessionColumn));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.SIZEOFEXPERIMENTCOLUMS, Integer.toString(columnsPerExperiment));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.PEPTIDEENDLOCATION, Integer.toString(peptideEndColumn));
        metaDataMap.put(PossibleMetaDataAnnotationsEnum.PEPTIDESTARTLOCATION, Integer.toString(peptideStartColumn));
        return metaDataMap;
    }

    public SeparatedValueExperimentMetadata setProteinSequenceColumn(int aColumn) {
        proteinSequenceColumn = aColumn;
        return this;
    }

    public int getPeptideStartColumn() {
        return peptideStartColumn;
    }

    public SeparatedValueExperimentMetadata setPeptideStartColumn(int peptideStartColumn) {
        setExperimentHasPeptideLocationValues(true);
        this.peptideStartColumn = peptideStartColumn;
        return this;
    }

    public int getPeptideEndColumn() {
        return peptideEndColumn;
    }

    public SeparatedValueExperimentMetadata setPeptideEndColumn(int peptideEndColumn) {
        setExperimentHasPeptideLocationValues(true);
        this.peptideEndColumn = peptideEndColumn;
        return this;
    }

    public boolean experimentHasPeptideLocationValues() {
        return hasPeptideLocationValues;
    }

    public void setExperimentHasPeptideLocationValues(boolean hasLocationValues) {
        this.hasPeptideLocationValues = hasLocationValues;
    }

    public String getMissingValue() {
        return missingValue;
    }

    public void setMissingValue(String missingValue) {
        this.missingValue = missingValue;
    }

    public boolean hasMissingValues() {
        return hasMissingValues;
    }
    
    
}
