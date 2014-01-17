package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.Color;

/**
 *
 * @author Davy
 */
public interface GradientDrawModeInterface<T extends Protein, U extends Peptide> extends DrawModeInterface<T, U> {

    Color calculateAminoAcidGradient(T protein, int location) throws CalculationException;

    Color calculatePeptideGradient(U peptide) throws CalculationException;
}
