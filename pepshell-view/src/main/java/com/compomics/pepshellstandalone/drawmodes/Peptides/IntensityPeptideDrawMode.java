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

package com.compomics.pepshellstandalone.drawmodes.Peptides;

import com.compomics.pepshellstandalone.ProgramVariables;
import com.compomics.pepshell.model.Peptide;

import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshellstandalone.drawmodes.DrawProteinPeptidesInterface;
import com.compomics.pepshellstandalone.drawmodes.GradientDrawModeInterface;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 */
public class IntensityPeptideDrawMode<T extends PepshellProtein, U extends Peptide> implements DrawProteinPeptidesInterface<T, U>, GradientDrawModeInterface<U> {

    private double maxIntensity = 1.0;

    @Override
    public void drawPeptide(U peptide, Graphics g, Point startPoint, int length, int height) {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            UndrawableException colourFailed = new UndrawableException("could not draw the spectrum intensity");
            colourFailed.initCause(ex);
            ProgramVariables.exceptionBus.post(colourFailed);
        }
        if (peptide.getEndProteinMatch() == -1 || peptide.getBeginningProteinMatch() == -1) {
            ProgramVariables.exceptionBus.post(new UndrawableException("could not determine relative position of the peptide \n beginning protein match = " + peptide.getBeginningProteinMatch() +" ending protein match = "+ peptide.getEndProteinMatch()));
        }
        ((Graphics2D) g).setStroke(new BasicStroke(2F));
        g.drawRect(startPoint.x, startPoint.y, length, height);
    }


    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        int colorValue = (int) Math.floor(255 * ((peptide.getTotalSpectrumIntensities().stream().reduce(0.0, Double::sum)) / peptide.getTotalSpectrumIntensities().size() / maxIntensity));
        if (colorValue > 255 || colorValue < 0) {
            throw new CalculationException("the range of colours was exceeded \n value was " + colorValue);
        }
        return new Color(colorValue, 255 - colorValue, 115);
    }

    public void setMaxIntensity(double maxIntensity) {
        this.maxIntensity = maxIntensity;
    }
}
