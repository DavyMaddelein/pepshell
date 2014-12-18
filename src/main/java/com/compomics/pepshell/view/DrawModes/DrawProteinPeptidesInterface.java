package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * the root drawing interface for all the protein and peptide visualisations in
 * the project comparison
 *
 * the interface is aimed at drawing proteins and its accompanying peptides at a given alpha value on a graphics object
 *
 * @author Davy Maddelein
 * @param <T> the pepshell protein type to draw
 * @param <U> the pepshell peptide type to draw
 */
public interface DrawProteinPeptidesInterface<T extends ProteinInterface, U extends PeptideInterface> {

    
    /**
     * draws the protein at the given starting point of the
     * passed on graphics object for the given length and size
     *
     * @param protein the protein to draw
     * @param g the graphics to draw on
     * @param startPoint the starting point (upper left of the protein visualisation preferably) from where to start drawing on the graphics object
     * @param length the length of the protein bar, it is implied that the desired visualisation surface is calculated before drawing the protein
     * @param height the height of the protein bar @see length
     * @throws UndrawableException should it be impossible to draw the protein
     */
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException;

    /**
     * 
     * @param peptide
     * @param g
     * @param startPoint
     * @param length
     * @param height
     * @throws UndrawableException 
     */
    void drawPeptide(U peptide, Graphics g, Point startPoint, int length, int height) throws UndrawableException;

    /**
     * used to set the alpha value that is possibly taken into account during the {@code drawProtein} call 
     * @param alpha 1
     */
    public void setProteinAlpha(float alpha);

    
    /**
     * used to set the peptide alpha value that is possibly taken into account during the {@code drawProtein} call 
     * @param alpha 
     */
    public void setPeptideAlpha(float alpha);
}
