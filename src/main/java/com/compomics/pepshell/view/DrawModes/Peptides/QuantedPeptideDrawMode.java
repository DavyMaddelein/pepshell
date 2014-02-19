package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Davy
 * @param <U>
 */
public class QuantedPeptideDrawMode<U extends QuantedPeptide> extends StandardPeptideProteinDrawMode<Protein, U> implements GradientDrawModeInterface<Protein, U> {

    @Override
    public void drawPeptide(U peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            FaultBarrier.getInstance().handleException(ex);
            throw new UndrawableException("could not calculate the ratio gradient");
        }
        super.drawPeptide(peptide, g, horizontalOffset, verticalOffset, verticalBarSize);
    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        //log quant code from rover
        Color gradientColor = null;
        QuantedPeptide aQuantedPeptide = (QuantedPeptide) peptide;
        //returnValue = (int) Math.ceil(125 * aQuantedPeptideGroup.getLogRatio());
        gradientColor = ProgramVariables.PEPTIDECOLOR;
        return gradientColor;

    }

    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) throws CalculationException {
        return ProgramVariables.PROTEINCOLOR;
    }

    @Override
    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
