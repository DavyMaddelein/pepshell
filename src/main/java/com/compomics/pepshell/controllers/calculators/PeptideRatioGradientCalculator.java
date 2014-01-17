package com.compomics.pepshell.controllers.calculators;

import java.awt.Color;

/**
 *
 * @author Davy
 */
public class PeptideRatioGradientCalculator extends GradientCalculator {

    
    @Override
    protected Color calculateScaledColor(double value) {
        //todo change this to log 2 and fill in aNumber
        int aNumber = -1;
        int gradientStep = (int) Math.ceil(Math.log(value / this.max)) * aNumber;
        return new Color(gradientStep);
    }
}
