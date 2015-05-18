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
public class QuantedPeptide extends Peptide {

    private List<Double> heavy = new ArrayList<>();
    private List<Double> light = new ArrayList<>();
    private Double ratio;
    private Double standardError = 0.0;

    public QuantedPeptide(String sequence) {
        super(sequence);
    }
    
    public QuantedPeptide(String sequence, Double spectrumIntensity) {
        super(sequence,spectrumIntensity);
    }

    public QuantedPeptide(String sequence, Double spectrumIntensity, Double aRatio){
        super(sequence,spectrumIntensity);
        this.ratio = aRatio;
    }
    
    public QuantedPeptide(String sequence, Double spectrumIntensity, Double aRatio,Double aStandardError){
        super(sequence,spectrumIntensity);
        this.ratio = aRatio;
        this.standardError = aStandardError;
    }
    
    public Double getRatio() {
        return ratio;
    }

    public List<Double> getHeavy() {
        return heavy;
    }

    public void addHeavy(Double heavy) {
        this.heavy.add(heavy);
    }

    public List<Double> getLight() {
        return light;
    }

    public void addLight(Double light) {
        this.light.add(light);
    }

    public void setRatio(Double aRatio) {
        this.ratio = aRatio;
    }
    
    public Double getStandardError(){
        return standardError;
    }
    
    public void setStandardError(Double aStandardError){
        this.standardError = aStandardError;
    }
}
