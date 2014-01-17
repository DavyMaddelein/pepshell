package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;

/**
 *
 * @author Davy
 * @param <T>
 * @param <N>
 * @param <U>
 */
public class SolventAccessibleProteinDrawMode<T extends Protein<N>, N extends PeptideGroup<U>, U extends Peptide> extends StandardPeptideProteinDrawMode<T, N, U> implements GradientDrawModeInterface<T, U> {

    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        Color freeEnergyGradientColor = ProgramVariables.PROTEINCOLOR;
        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetSolventAccessability()) {
            ProgramVariables.STRUCTUREDATASOURCE.getRelativeSolventAccessabilityForResidue(protein, location);
        } else {
            throw new CalculationException("cannot retrieve solvent accessability values for protein");
        }
        return freeEnergyGradientColor;
    }

    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

}
