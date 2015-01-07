package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public class FreeEnergyProteinDrawMode<T extends Protein, U extends Peptide> extends AbstractPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetFreeEnergy() && protein.getPreferedPdbFile() != null) {
            Map<Integer, Double> freeEnergyValues = ProgramVariables.STRUCTUREDATASOURCE.getFreeEnergyForStructure(protein, protein.getPreferedPdbFile());
            //go over all locations retrieved from the data source
            for (int location : freeEnergyValues.keySet()) {
                Double freeEnergyValue = freeEnergyValues.get(location);
                //check for null values
                if (freeEnergyValue != null) {
                    Color freeEnergyGradientColor = new Color(Math.min((int) Math.ceil(freeEnergyValue * 255), 255), 255, 125);
                    g.setColor(freeEnergyGradientColor);
                } else {
                    g.setColor(Color.WHITE);
                }
                int scaledLength = DrawModeUtilities.getInstance().scale(1 / protein.getProteinSequence().length());
                g.fillRect(startPoint.x + DrawModeUtilities.getInstance().scale(location), startPoint.y, scaledLength, height);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the free energy", startPoint.x, startPoint.y + 5);
            throw new UndrawableException("could not calculate gradient");
        }
    }

    @Override
    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        throw new UnsupportedOperationException("requires refactor");
    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    @Override
    public void drawColorLegend(Point legendDrawPoint, Graphics g) {
        int colorCounter = 0;
        while (colorCounter < 64) {
            g.setColor(new Color(colorCounter * 4, 255, 125));
            g.fillRect(legendDrawPoint.x + colorCounter, legendDrawPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 1;
        }
        g.setColor(Color.black);
        g.drawString("low", legendDrawPoint.x, legendDrawPoint.y + 25);
        g.drawString("high", legendDrawPoint.x + colorCounter - 5, legendDrawPoint.y + 25);

    }
}
