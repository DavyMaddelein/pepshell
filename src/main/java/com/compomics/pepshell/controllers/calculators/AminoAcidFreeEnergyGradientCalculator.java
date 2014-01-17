package com.compomics.pepshell.controllers.calculators;

import java.awt.Color;

/**
 *
 * @author Davy
 */
public class AminoAcidFreeEnergyGradientCalculator extends GradientCalculator {

    @Override
    protected Color calculateScaledColor(double value) {
        //TODO actually look at this step calculation for very differing number. i.e. actually work out the scaling
        int gradientStep = (int) Math.ceil((value / this.max) * 64);
        return new Color(gradientStep);
    }
}
