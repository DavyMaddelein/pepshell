package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * this class is a skeletal implementation of the {@code DrawModeInterface} to
 * draw a simple protein representation with its peptides it will draw a
 * uniformly coloured bar for the proteins and will try and fit the protein and
 * its peptides on the given horizontal size the colour of the protein and
 * peptides are taken from the program properties as is the scale
 *
 * @author Davy Maddelein
 * @param <T> the pepshell protein type to draw
 * @param <U> the pepshell peptide type to draw
 */
public class AbstractPeptideProteinDrawMode<T extends ProteinInterface, U extends PeptideInterface> implements DrawProteinPeptidesInterface<T, U> {

    /**
     * the alpha value to draw the protein at
     */
    protected float proteinAlpha = 1f;

    /**
     * the alpha value to draw the peptides at
     */
    protected float peptideAlpha = 1f;

    /**
     * method to draw a protein on the given graphics with the given parameters
     * the drawn protein and peptides will be scaled horizontally to the set
     * scale in the {@code DrawModeUtilities} class {@literal .} The colour of
     * the protein and the peptides will be the ones defined in the program
     * variables. The alpha will be the ones set by the alpha setters, standard
     * this is 1 {@literal .} if there were no exceptions raised, the composite
     * is returned to its original state.
     *
     * @param protein the protein to draw
     * @param g the graphics to draw on
     * @param startPoint the upper left point to start drawing the protein and
     * its peptides
     * @param length the horizontal size of the protein bar drawn on the
     * graphics
     * @param height the vertical size of the protein bar drawn on the graphics
     * @throws UndrawableException thrown when the given protein could not be
     * drawn,this will leave the composite of the graphics object pointing to an
     * alpha composite partial execution will result in partial drawing of the
     * protein and peptides
     */
    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        //set colour and alpha for protein
        g.setColor(ProgramVariables.PROTEINCOLOR);
        Composite defensiveComposite = ((Graphics2D) g).getComposite();
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, proteinAlpha));
        //actually draw the protein
        g.fillRect(startPoint.x, startPoint.y, length, height);

        //protein has been drawn, we move on to the peptides, first alpha
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, peptideAlpha));

        //we will use this point as a sliding starting point for each peptide
        Point peptideStartPoint = (Point)startPoint.clone();
        //start on the proteins, we will draw the shortest peptide in the peptide group
        for (PeptideGroup aGroup : protein.getPeptideGroups()) {
            Peptide peptideToDraw = aGroup.getShortestPeptide();
            if (peptideToDraw.getEndProteinMatch() != -1 && peptideToDraw.getBeginningProteinMatch() != -1) {
                //we scale the length of the peptide to fit the protein context
                int scaledLength = DrawModeUtilities.getInstance().scale(peptideToDraw.getEndProteinMatch() - peptideToDraw.getBeginningProteinMatch());
                //we scale the starting point of the peptide to the context of the peptide
                peptideStartPoint.x = DrawModeUtilities.getInstance().scale(peptideToDraw.getBeginningProteinMatch()) + startPoint.x;
                //look up how to fix this generics brainfart
                drawPeptide((U) peptideToDraw, g, peptideStartPoint, scaledLength, height);
            }
        }
        ((Graphics2D) g).setComposite(defensiveComposite);
    }

    /**
     * {@inheritDoc }
     *
     * @throws UndrawableException
     */
    @Override
    public void drawPeptide(U peptide, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        g.setColor(ProgramVariables.PEPTIDECOLOR);
        ((Graphics2D) g).setStroke(new BasicStroke(2F));
        g.drawRect(startPoint.x, startPoint.y, length, height);
    }

    @Override
    public void setProteinAlpha(float alpha) {
        proteinAlpha = alpha;
    }

    @Override
    public void setPeptideAlpha(float alpha) {
        proteinAlpha = alpha;
    }
}
