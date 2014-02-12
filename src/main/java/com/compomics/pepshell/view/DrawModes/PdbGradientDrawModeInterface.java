package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Niels Hulstaert
 */
public interface PdbGradientDrawModeInterface<T extends Protein, U extends Peptide> extends GradientDrawModeInterface<T, U> {

    Color calculateAminoAcidGradient(T protein, int location) throws CalculationException;

    Color calculatePeptideGradient(U peptide) throws CalculationException;
    
    void drawColorLegend(int xOffset, int yOffset, Graphics g);
}
