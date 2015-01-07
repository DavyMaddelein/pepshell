package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.model.gradientMaps.SecondaryStructureMaps;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public class SecondaryStructureProteinDrawMode<T extends Protein, U extends Peptide> extends AbstractPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    private String pdbAccession;

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleTogetSecondaryStructure() && pdbAccession != null) {
            Map<Integer, String> secondaryStructurePrediction = ProgramVariables.STRUCTUREDATASOURCE.getSecondaryStructureForStructure(protein, pdbAccession);
            for (Entry<Integer, String> location : secondaryStructurePrediction.entrySet()) {
                if (location.getValue() != null) {
                    g.setColor(SecondaryStructureMaps.getColorMap().get(location.getValue()));
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(startPoint.x + (DrawModeUtilities.getInstance().scale(location.getKey())), startPoint.y, DrawModeUtilities.getInstance().scale(1), height);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the secondary structure", startPoint.x, startPoint.y + 5);
            throw new UndrawableException("could not calculate gradient");
        }
    }

    @Override
    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        int colorCounter = 0;
        for (Entry<String, Color> entry : SecondaryStructureMaps.getColorMap().entrySet()) {
            g.setColor(entry.getValue());
            g.fillRect(legendStartPoint.x + colorCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            g.drawString(entry.getKey(), legendStartPoint.x + 10 + colorCounter, legendStartPoint.y + 7);
            colorCounter += ProgramVariables.VERTICALSIZE;
            colorCounter += 15;
        }

    }

    public void setPdbAccession(String pdbAccession) {
        this.pdbAccession = pdbAccession;
    }
}
