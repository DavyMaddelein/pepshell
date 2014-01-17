package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import java.awt.Graphics;
import java.util.Iterator;

/**
 *
 * @author Davy
 * @param <T>
 * @param <N>
 * @param <U>
 */
public class StandardPeptideProteinDrawMode<T extends Protein<N>, N extends PeptideGroup<U>, U extends Peptide> implements DrawModeInterface<T, U> {

    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, double scale, int width) throws UndrawableException {
        g.setColor(ProgramVariables.PROTEINCOLOR);
        g.fillRect(horizontalOffset, verticalOffset, (int) Math.ceil(protein.getProteinSequence().length() / scale), width);
    }

    public void drawPeptide(U peptide, Graphics g, int horizontalOffset, int verticalOffset, double barSize, int width) throws UndrawableException {
        g.setColor(ProgramVariables.PEPTIDECOLOR);
        if (peptide.getEndProteinMatch() == -1 || peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide");
        }
        int startingLocation = (int) Math.ceil(((peptide.getBeginningProteinMatch()) / barSize));
        int size = (int) Math.ceil(((peptide.getEndProteinMatch() - peptide.getEndProteinMatch()) / barSize));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, size, width);
    }

    public void drawProteinAndPeptidesOfProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, double scale, int verticalBarWidth) throws UndrawableException {
        drawProtein(protein, g, horizontalOffset, verticalOffset, scale, verticalBarWidth);
        Iterator<N> peptideGroupIterator = protein.getPeptideGroupsForProtein();
        while (peptideGroupIterator.hasNext()) {
            drawPeptide(peptideGroupIterator.next().getShortestPeptide(), g, horizontalOffset, verticalOffset, scale, verticalBarWidth);
        }
    }
}
