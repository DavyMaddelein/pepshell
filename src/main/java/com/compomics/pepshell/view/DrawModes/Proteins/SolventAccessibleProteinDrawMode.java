package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

/**
 *
 * @author Davy
 * @param <T>
 * @param <U>
 */
public class SolventAccessibleProteinDrawMode<T extends Protein, U extends Peptide> extends StandardPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {
    
    @Override
    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarWidth) throws UndrawableException {

        int sizePerAminoAcid = (int) Math.ceil(horizontalBarSize / protein.getProteinSequence().length());

//        Map<Integer, Double> relSasValues = ProgramVariables.STRUCTUREDATASOURCE.getRelativeSolventAccessibilityForStructure(protein, protein.getPdbFileNames().get(0));
        Map<Integer, Double> relSasValues = ProgramVariables.STRUCTUREDATASOURCE.getRelativeSolventAccessibilityForStructure(protein, "3NY5");
        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetSolventAccessibility()) {
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
                g.fillRect(horizontalOffset + (location * sizePerAminoAcid), verticalOffset, sizePerAminoAcid, verticalBarWidth);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the solvent accessibility", horizontalOffset, verticalOffset + 5);
            throw new UndrawableException("could not calculate gradient");
        }

    }    
    
    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        double colorIncrement = 255 / 64;
        for (int i = 0; i > 64; i++) {
            g.setColor(new Color((int) Math.ceil((colorIncrement)) * 255, 255, 125));
            colorIncrement += 255 / 64;
            g.fillRect(xOffset + (i * 5), yOffset, 5, ProgramVariables.VERTICALSIZE);
        }
    }    
}
