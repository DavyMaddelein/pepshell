package com.compomics.pepshell.model;

import com.compomics.pepshell.model.exceptions.CalculationException;
import java.util.ArrayList;
import java.util.Collections;
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
    
    public Double getRatio() throws CalculationException {
        return ratio;
    }

    public List<Double> getHeavy() {
        return Collections.unmodifiableList(heavy);
    }

    public void addHeavy(Double heavy) {
        this.heavy.add(heavy);
    }

    public List<Double> getLight() {
        return Collections.unmodifiableList(light);
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
