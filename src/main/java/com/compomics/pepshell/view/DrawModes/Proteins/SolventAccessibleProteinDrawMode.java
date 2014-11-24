package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.ProteinInterface;
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
public class SolventAccessibleProteinDrawMode<T extends ProteinInterface, U extends PeptideInterface> extends AbstractPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    private String pdbAccession;

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetSolventAccessibility() && pdbAccession != null) {
            Map<Integer, Double> relSasValues = ProgramVariables.STRUCTUREDATASOURCE.getRelativeSolventAccessibilityForStructure(protein, pdbAccession);
            //go over all locations retrieved from the data source
            for (int location : relSasValues.keySet()) {
                Double relSasValue = relSasValues.get(location);
                //check for null values
                if (relSasValue != null) {
                    Color relativeAccessibilityGradientColor = new Color(Math.min((int) Math.ceil(relSasValue * 255), 255), 255, 125);
                    g.setColor(relativeAccessibilityGradientColor);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(startPoint.x + DrawModeUtilities.getInstance().scale(location), startPoint.y, DrawModeUtilities.getInstance().scale(1), height);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the solvent accessibility", startPoint.x, startPoint.y + 5);
            throw new UndrawableException("could not calculate gradient");
        }

    }

    @Override
    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        int colorCounter = 0;
        while (colorCounter < 64) {
            g.setColor(new Color(colorCounter * 4, 255, 125));
            g.fillRect(legendStartPoint.x + colorCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 1;
        }
        g.setColor(Color.black);
        g.drawString("low", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("high", legendStartPoint.x + colorCounter - 5, legendStartPoint.y + 25);
    }

    public void setPdbAccession(String pdbAccession) {
        this.pdbAccession = pdbAccession;
    }
}
