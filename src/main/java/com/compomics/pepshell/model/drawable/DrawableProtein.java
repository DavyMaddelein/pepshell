package com.compomics.pepshell.model.drawable;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class DrawableProtein extends Protein implements Drawable {

    public DrawableProtein(String accession) {
        super(accession);
    }

    public DrawableProtein(String accession, String sequence) {
        super(accession, sequence);
    }

    /**
     * draws this protein instance and all its containing peptide groups to the
     * set scale
     *
     * @param x the horizontal pixel value
     * @param y the vertical pixel value
     * @param g the graphics to draw on
     * @throws UndrawableException if the protein could not be drawn
     */
    public void draw(int x, int y, Graphics g) throws UndrawableException {
        this.drawProteinOnly(x, y, g);
        this.drawAllPeptideGroups(x, y, g);
    }

    /**
     * draws only the protein sequence to scale
     *
     * @param x the horizontal pixel to start at
     * @param y the vertical pixel to start at
     * @param g the graphics to draw on
     * @throws UndrawableException should the protein fail to be drawn
     */
    public void drawProteinOnly(int x, int y, Graphics g) throws UndrawableException {
        g.setColor(ProgramVariables.PROTEINCOLOR);
        int horizontalDistance = (int) Math.ceil((double) this.getProteinSequence().length() / ProgramVariables.SCALE);
        g.fillRect(x, y, horizontalDistance, ProgramVariables.VERTICALSIZE);
    }

    public void drawAminoAcidsOfSequence(int x, int y, Graphics g, Map<String, Color> gradientColorMap) {
        String[] splitProteinSequence = this.getProteinSequence().split("");
        int i = 0;
        int previousEnd = x;
        while (i < splitProteinSequence.length) {
            if (gradientColorMap == null) {
                g.setColor(ProgramVariables.PROTEINCOLOR);
            } else {
                if (gradientColorMap.isEmpty()) {
                    g.setColor(ProgramVariables.PROTEINCOLOR);
                } else {
                    g.setColor(gradientColorMap.get(splitProteinSequence[i]));
                }
            }
            g.fillRect(x + previousEnd + 1, y, (int) Math.ceil(ProgramVariables.SCALE), ProgramVariables.VERTICALSIZE);
        }
    }

    /**
     * draws all the peptide groups in this protein to scale
     *
     * @param x the pixel start of the protein, all peptide groups are drawn at
     * their relative distances from this point
     * @param y the vertical pixel value
     * @param g the graphics to draw on
     * @throws UndrawableException if a peptide group was not drawable
     */
    public void drawAllPeptideGroups(int x, int y, Graphics g) throws UndrawableException {
        for (PeptideGroup aPeptideGroup : this) {
            if (aPeptideGroup instanceof DrawablePeptideGroup) {
                ((DrawablePeptideGroup) aPeptideGroup).draw(x + aPeptideGroup.getStartingAlignmentPosition(), y, g);
            } else {
                throw new UndrawableException("could not draw a peptide group");
            }
        }
    }

    /**
     * this method will try to draw all peptide groups contained in the protein
     * to scale
     *
     * @param x the pixel start of the protein, all peptide groups are drawn at
     * their relative distances from this point
     * @param y the vertical pixel value
     * @param g the graphics to draw on
     * @return a {@code List} containing the peptide groups that failed to be
     * drawn
     */
    public List<PeptideGroup> forceDrawAllPeptideGroups(int x, int y, Graphics g) {
        List<PeptideGroup> failedPeptideGroups = new ArrayList<PeptideGroup>(this.size());
        for (PeptideGroup aPeptideGroup : this) {
            if (aPeptideGroup instanceof DrawablePeptideGroup) {
                try {
                    ((DrawablePeptideGroup) aPeptideGroup).draw(x + aPeptideGroup.getStartingAlignmentPosition(), y, g);
                } catch (UndrawableException undrawable) {
                    failedPeptideGroups.add(aPeptideGroup);
                }
            }
        }

        return failedPeptideGroups;
    }

    /**
     * draws a single peptide group to scale
     *
     * @param x the horizontal point to start drawing warning, does not take the
     * protein length into account
     * @param y the vertical point to start drawing
     * @param g the graphics to draw on
     * @param indexOfPeptideGroup the index of the peptide group to draw in this
     * protein
     * @throws UndrawableException should the peptide group specified fail to be
     * drawn
     * @throws IndexOutOfBoundsException if the requested peptide group is not
     * in the range of this protein
     */
    public void drawPeptideGroupFromList(int x, int y, Graphics g, int indexOfPeptideGroup) throws UndrawableException, IndexOutOfBoundsException {
        if (this.get(indexOfPeptideGroup) instanceof DrawablePeptideGroup) {
            ((DrawablePeptideGroup) this.get(indexOfPeptideGroup)).draw(x, y, g);
        } else {
            throw new UndrawableException("could not draw peptide group");
        }
    }

    public void drawDomains(int x, int y, Graphics g) throws UndrawableException {
        for (Domain aDomain : this.getDomains()) {
            if (aDomain instanceof DrawableDomain) {
                ((DrawableDomain) aDomain).draw(x, y, g);
            }
        }
    }
}
