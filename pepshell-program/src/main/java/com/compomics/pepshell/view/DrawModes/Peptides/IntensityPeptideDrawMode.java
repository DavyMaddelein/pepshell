package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 */
public class IntensityPeptideDrawMode extends AbstractPeptideProteinDrawMode<Protein, Peptide> implements GradientDrawModeInterface<Protein, Peptide> {

    private double maxIntensity = 5.0;

    @Override
    public void drawPeptide(Peptide peptide, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            UndrawableException colourFailed = new UndrawableException("could not draw the spectrum intensity");
            colourFailed.initCause(ex);
            throw colourFailed;
        }
        if (peptide.getEndProteinMatch() == -1 || peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide \n beginning protein match = " + peptide.getBeginningProteinMatch() +" ending protein match = "+ peptide.getEndProteinMatch());
        }
        ((Graphics2D) g).setStroke(new BasicStroke(2F));
        g.drawRect(startPoint.x, startPoint.y, length, height);
    }

    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) throws CalculationException {
        return ProgramVariables.PROTEINCOLOR;
    }

    @Override
    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        int colorValue = (int) Math.floor(255 * (peptide.getTotalSpectrumIntensity() / maxIntensity));
        if (colorValue > 255 || colorValue < 0) {
            throw new CalculationException("the range of colours was exceeded \n value was " + colorValue);
        }
        return new Color(colorValue, 255 - colorValue, 115);
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        int colourCounter = 0;
        while (colourCounter < 51) {
            g.setColor(new Color(colourCounter * 5, 255 - (colourCounter * 5), 115));
            g.fillRect(legendStartPoint.x + colourCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colourCounter += 5;
        }

        g.setColor(Color.black);
        g.drawString("low", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("high", legendStartPoint.x + colourCounter - 5, legendStartPoint.y + 25);
    }

    public void setMaxIntensity(double maxIntensity) {
        this.maxIntensity = maxIntensity;
    }
}
