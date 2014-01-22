package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;

/**
 *
 * @author Davy
 * @param <T>
 * @param <N>
 * @param <U>
 */
public class StandardPeptideProteinDrawMode<T extends Protein<N>, N extends PeptideGroup<U>, U extends Peptide> implements DrawModeInterface<T, U> {

    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarSize) throws UndrawableException {
        g.setColor(ProgramVariables.PROTEINCOLOR);
        g.fillRect(horizontalOffset, verticalOffset, horizontalBarSize, verticalBarSize);
    }

    public void drawPeptide(U peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) throws UndrawableException {
        g.setColor(ProgramVariables.PEPTIDECOLOR);
        if (peptide.getEndProteinMatch() == -1 || peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide");
        }
        int startingLocation = (int) Math.ceil(((peptide.getBeginningProteinMatch()) * ProgramVariables.SCALE));
        int size = (int) Math.ceil(((peptide.getEndProteinMatch() - peptide.getBeginningProteinMatch()) * ProgramVariables.SCALE));
        ((Graphics2D)g).setStroke(new BasicStroke(2F));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, size, verticalBarSize);
    }

    public void drawProteinAndPeptidesOfProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarSize) throws UndrawableException {
        drawProtein(protein, g, horizontalOffset, verticalOffset, horizontalBarSize, verticalBarSize);
        Iterator<N> peptideGroupIterator = protein.getPeptideGroupsForProtein();
        while (peptideGroupIterator.hasNext()) {
            drawPeptide(peptideGroupIterator.next().getShortestPeptide(), g, horizontalOffset, verticalOffset, verticalBarSize);
        }
    }
}
