package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public interface GradientDrawModeInterface<T extends ProteinInterface, U extends PeptideInterface> {

    Color calculateAminoAcidGradient(T protein, int aminoAcidLocation) throws CalculationException;

    Color calculatePeptideGradient(U peptide) throws CalculationException;
    
    void drawColorLegend(Point legendStartPoint, Graphics g);
}
