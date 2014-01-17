package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.HydrophobicityMaps;
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
 */
public class HydrophobicityProteinDrawMode<T extends Protein<N>, N extends PeptideGroup<U>, U extends Peptide> extends StandardPeptideProteinDrawMode<T, N, U> implements GradientDrawModeInterface<T, U> {

    @Override
    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, double scale, int verticalBarWidth) throws UndrawableException {

        int horizontalAminoAcidBarSize = (int) Math.ceil(protein.getProteinSequence().length() * scale);
        for (int previousEnd = 0; previousEnd < protein.getProteinSequence().length(); previousEnd++) {
            g.setColor(ProgramVariables.PROTEINCOLOR);
            g.fillRect(horizontalOffset + previousEnd, verticalOffset, horizontalAminoAcidBarSize, verticalBarWidth);
            previousEnd += horizontalAminoAcidBarSize;
        }
    }

    @Override
    public Color calculateAminoAcidGradient(T protein, int location) {
        return HydrophobicityMaps.hydrophobicityMapPh7.get(protein.getProteinSequence().charAt(location));

    }

    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }
}
