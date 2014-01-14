package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class StandardPeptideProteinDrawMode {

    float transparency = 1;

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        g.setColor(Color.PINK);
        g.fillRect(horizontalOffset, verticalOffset, size, width);
        //TODO: what about multiple times the same peptide?
        int length = protein.getProteinSequence().length();
        double scale = (double) length  / (double) size;
        drawPeptides(protein, g, horizontalOffset, verticalOffset, width,scale);

    }

    public void drawPeptides(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        g.setColor(Color.BLACK);
        for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
            if (peptideGroup.getEndAlignmentPosition() == -1 || peptideGroup.getStartingAlignmentPosition() == -1) {
                PeptideGroupController.mapPeptideGroupToProtein(protein, peptideGroup);
            }
            //g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
            drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, width, barSize);
        }
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        int startingLocation = (int) Math.ceil(((double) (peptideGroup.getStartingAlignmentPosition()) / barSize));
        int size = (int) Math.ceil((((double) (peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition())) / barSize));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, size, width);
    }

    public void setPeptideAlpha(float aTransparency) {
        this.transparency = aTransparency;
    }
}
