package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.CPDTPeptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class CPDTCleavedProteinDrawMode extends StandardPeptideProteinDrawMode<Protein, CPDTPeptide> implements GradientDrawModeInterface<Protein, CPDTPeptide> {

    @Override
    public void drawPeptide(CPDTPeptide peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            throw new UndrawableException("could not display probability of the calculated peptide");
        }
        if (peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide");
        }
        int startingLocation = (int) Math.ceil(((peptide.getBeginningProteinMatch()) * ProgramVariables.SCALE));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, 1, verticalBarSize);
    }

    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) throws CalculationException {
        return (ProgramVariables.PROTEINCOLOR);
    }

    @Override
    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color calculatePeptideGradient(CPDTPeptide peptide) throws CalculationException {
        return new Color((int) Math.ceil(255-(255*peptide.getProbability())),(int) Math.ceil(255 * peptide.getProbability()), 0);
    }
}
