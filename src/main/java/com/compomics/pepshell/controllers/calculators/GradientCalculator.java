package com.compomics.pepshell.controllers.calculators;

import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.Color;

/**
 *
 * @author Davy
 */
public abstract class GradientCalculator {

    Double max;
    Double min;

    private Color calculateGradient(double value) throws CalculationException {
        if (valueIsSane(value)) {
            return calculateScaledColor(value);
        } else {
            throw new CalculationException("provided value is unusable");
        }
    }

    private boolean valueIsSane(double value) {
        boolean sane = false;
        if (this.min == null || this.max == null) {
            if (this.min < value || this.max > value) {
                sane = true;
            }
        }
        return sane;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    protected abstract Color calculateScaledColor(double value);
}
