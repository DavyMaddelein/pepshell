package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class CPDTCleavedProteinDrawMode extends StandardPeptideProteinDrawMode<Protein, Peptide> {

    @Override
    public void drawPeptide(Peptide peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) throws UndrawableException {
        g.setColor(ProgramVariables.PEPTIDECOLOR);
        if (peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide");
        }
        int startingLocation = (int) Math.ceil(((peptide.getBeginningProteinMatch()) * ProgramVariables.SCALE));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, 1, verticalBarSize);
    }
}
