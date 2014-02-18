package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.gradientMaps.HydrophobicityMaps;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import static com.compomics.pepshell.model.gradientMaps.HydrophobicityMaps.hydrophobicityMapPh7;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import java.awt.Color;
import java.awt.Graphics;
import java.util.SortedSet;

/**
 *
 * @author Davy
 */
public class HydrophobicityProteinDrawMode extends StandardPeptideProteinDrawMode<Protein, Peptide> implements GradientDrawModeInterface<Protein, Peptide> {

    SortedSet<Color> colorLegend;

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarWidth) throws UndrawableException {
        for (int previousEnd = 0; previousEnd < protein.getProteinSequence().length(); previousEnd++) {
            g.setColor(HydrophobicityMaps.hydrophobicityMapPh7.get(protein.getProteinSequence().charAt(previousEnd)));
            g.fillRect(horizontalOffset + (previousEnd * ProgramVariables.SCALE), verticalOffset, ProgramVariables.SCALE, verticalBarWidth);
        }

    }

    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) {
        return HydrophobicityMaps.hydrophobicityMapPh7.get(protein.getProteinSequence().charAt(location));

    }

    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        //order colors by red value
        if (colorLegend == null) {
            Ordering<Color> colorOrdering = Ordering.natural().onResultOf(getRedValue);
            colorLegend = ImmutableSortedSet.orderedBy(colorOrdering).addAll(hydrophobicityMapPh7.values()).build();
        }
        int colorCounter = 0;
        for (Color aColor : colorLegend) {
            g.setColor(aColor);
            g.fillRect(xOffset + colorCounter, yOffset, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 5;
        }
        g.setColor(Color.black);
        g.drawString("acidic", xOffset, yOffset + 25);
        g.drawString("basic", xOffset + colorCounter - 5, yOffset + 25);
    }

    Function<Color, Integer> getRedValue = new Function<Color, Integer>() {
        public Integer apply(Color input) {
            return input.getRed();
        }
    };
}
