package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Davy
 */
public class IntensityPeptideDrawMode extends StandardPeptideProteinDrawMode<Protein, Peptide> implements GradientDrawModeInterface<Protein, Peptide> {

    private double maxIntensity;
    private double minIntensity;

    @Override
    public void drawPeptide(Peptide peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            throw new UndrawableException("could not draw the spectrum intensity");
        }
        if (peptide.getEndProteinMatch() == -1 || peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide");
        }
        int startingLocation = (int) Math.ceil(((peptide.getBeginningProteinMatch()) * ProgramVariables.SCALE));
        int size = (int) Math.ceil(((peptide.getEndProteinMatch() - peptide.getBeginningProteinMatch()) * ProgramVariables.SCALE));
        ((Graphics2D) g).setStroke(new BasicStroke(2F));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, size, verticalBarSize);
    }

    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) throws CalculationException {
        return ProgramVariables.PROTEINCOLOR;
    }

    @Override
    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        int test = (int) Math.floor(255 * (peptide.getTotalSpectrumIntensity() / maxIntensity));
        return new Color(test, 255 - test, 115);
    }

    @Override
    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        int colourCounter = 0;
        while (colourCounter < 51) {
            g.setColor(new Color(colourCounter * 5, 255 - (colourCounter * 5), 115));
            g.fillRect(xOffset + colourCounter, yOffset, 5, ProgramVariables.VERTICALSIZE);
            colourCounter += 5;
        }

        g.setColor(Color.black);
        g.drawString("low", xOffset, yOffset + 25);
        g.drawString("high", xOffset + colourCounter - 5, yOffset + 25);
    }

    public void setMaxIntensity(double maxIntensity) {
        this.maxIntensity = maxIntensity;
    }

    public void setMinIntensity(double minIntensity) {
        this.minIntensity = minIntensity;
    }

}
