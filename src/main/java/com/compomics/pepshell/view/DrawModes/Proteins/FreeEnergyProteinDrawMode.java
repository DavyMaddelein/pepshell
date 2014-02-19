package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.PdbGradientDrawModeInterface;
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
public class FreeEnergyProteinDrawMode<T extends Protein, U extends Peptide> extends StandardPeptideProteinDrawMode<T, U> implements PdbGradientDrawModeInterface<T, U> {

    private String pdbAccession;

    @Override
    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarWidth) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetFreeEnergy() && pdbAccession != null) {
            Map<Integer, Double> freeEnergyValues = ProgramVariables.STRUCTUREDATASOURCE.getFreeEnergyForStructure(protein, pdbAccession);
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
                g.fillRect(horizontalOffset + (location * ProgramVariables.SCALE), verticalOffset, ProgramVariables.SCALE, verticalBarWidth);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the free energy", horizontalOffset, verticalOffset + 5);
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
    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        int colorCounter = 0;
        while (colorCounter < 64) {
            g.setColor(new Color(colorCounter * 4, 255, 125));
            g.fillRect(xOffset + colorCounter, yOffset, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 1;
        }
        g.setColor(Color.black);
        g.drawString("low", xOffset, yOffset + 25);
        g.drawString("high", xOffset + colorCounter - 5, yOffset + 25);

    }

    public void setPdbAccession(String pdbAccession) {
        this.pdbAccession = pdbAccession;
    }

}
