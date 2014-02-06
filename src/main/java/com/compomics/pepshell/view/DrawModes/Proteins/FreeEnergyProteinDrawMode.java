package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
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
 * @param <T>
 * @param <N>
 * @param <U>
 */
public class FreeEnergyProteinDrawMode<T extends Protein, U extends Peptide> extends StandardPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    @Override
    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarWidth) throws UndrawableException {

        int sizePerAminoAcid = (int) Math.ceil(horizontalBarSize / protein.getProteinSequence().length());
        for (int previousEnd = 0; previousEnd < protein.getProteinSequence().length(); previousEnd++) {
            try {
                g.setColor(calculateAminoAcidGradient(protein, previousEnd));
            } catch (CalculationException ex) {
                g.drawString("could not draw the solvent accessibility", horizontalOffset, verticalOffset);
                throw new UndrawableException("could not calculate gradient");
            }
            g.fillRect(horizontalOffset + (previousEnd * sizePerAminoAcid), verticalOffset, sizePerAminoAcid, verticalBarWidth);
        }
    }

    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        Color freeEnergyGradientColor = ProgramVariables.PROTEINCOLOR;
        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetFreeEnergy()) {
            ProgramVariables.STRUCTUREDATASOURCE.getFreeEnergyForResidue(protein, location);
        } else {
            throw new CalculationException("cannot retrieve free energy values for protein");
        }
        return freeEnergyGradientColor;
    }

    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        double colorIncrement = 1 / 64;
        for (int i = 0; i > 64; i++) {
            g.setColor(new Color((int) Math.ceil((255 * colorIncrement)), 125, 125));
            colorIncrement += 1 / 64;
            g.fillRect(xOffset + i * 5, yOffset, 5, ProgramVariables.VERTICALSIZE);
        }
    }

}
