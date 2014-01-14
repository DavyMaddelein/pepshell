package com.compomics.pepshell.model.drawable;

import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.Drawable;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class DrawablePeptideGroup extends PeptideGroup implements Drawable {

    /**
     * draws this peptide group to scale
     *
     * @param x the horizontal pixel to start at
     * @param y the vertical pixel to start at
     * @param g the graphics to draw on
     * @throws UndrawableException should peptide group fail to be drawn
     */
    public void draw(int x, int y, Graphics g) throws UndrawableException {
        if (this.getShortestPeptide() instanceof DrawablePeptide) {
            ((DrawablePeptide) this.getShortestPeptide()).draw(x, y, g);
        } else {
            throw new UndrawableException("not drawable");
        }
    }

    /**
     * draws a single peptide contained in the peptide group at the specified
     * index
     *
     * @param x the horizontal pixel value to start drawing
     * @param y the vertical pixel value to start drawing
     * @param g the graphics to draw on
     * @param indexOfPeptide the index of the peptide in this peptide group
     * @throws UndrawableException if the peptide could not be drawn
     * @throws IndexOutOfBoundsException if the requested peptide falls outside
     * the range of this peptide group
     */
    public void drawPeptideInGroup(int x, int y, Graphics g, int indexOfPeptide) throws UndrawableException, IndexOutOfBoundsException {
        if (this.get(indexOfPeptide) instanceof DrawablePeptide) {
            ((DrawablePeptide) this.get(indexOfPeptide)).draw(x, y, g);
        } else {
            throw new UndrawableException("could not draw peptide");
        }
    }
}
