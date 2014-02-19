package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.model.gradientMaps.SecondaryStructureMaps;
import com.compomics.pepshell.view.DrawModes.PdbGradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Davy
 * @param <T>
 * @param <W>
 */
public class SecondaryStructureProteinDrawMode<T extends Protein, W extends Peptide> extends StandardPeptideProteinDrawMode<T, W> implements PdbGradientDrawModeInterface<T, W> {

    private String pdbAccession;

    @Override
    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarSize) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleTogetSecondaryStructure() && pdbAccession != null) {
            Map<Integer, String> secondaryStructurePrediction = ProgramVariables.STRUCTUREDATASOURCE.getSecondaryStructureForStructure(protein, pdbAccession);
            for (Entry<Integer, String> location : secondaryStructurePrediction.entrySet()) {
                if (location.getValue() != null) {
                    g.setColor(SecondaryStructureMaps.getColorMap().get(location.getValue()));
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(horizontalOffset + (location.getKey() * ProgramVariables.SCALE), verticalOffset, ProgramVariables.SCALE, verticalBarSize);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the secondary structure", horizontalOffset, verticalOffset + 5);
            throw new UndrawableException("could not calculate gradient");
        }
    }

    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        int colorCounter = 0;
        for (Entry<String, Color> entry : SecondaryStructureMaps.getColorMap().entrySet()) {
            g.setColor(entry.getValue());
            g.fillRect(xOffset + colorCounter, yOffset, 5, ProgramVariables.VERTICALSIZE);
            g.drawString(entry.getKey(), xOffset + 10 + colorCounter, yOffset + 7);
            colorCounter += ProgramVariables.VERTICALSIZE;
            colorCounter += 15;
        }

    }

    public void setPdbAccession(String pdbAccession) {
        this.pdbAccession = pdbAccession;
    }
}
