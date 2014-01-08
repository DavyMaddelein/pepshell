package com.compomics.peppi.view.DrawModes;

import com.compomics.peppi.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.Protein;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
        drawPeptides(protein, g, horizontalOffset, verticalOffset, width, (double) protein.getProteinSequence().length() / (double) size);

    }

    public void drawPeptides(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        g.setColor(new Color(255, 133, 0));
        for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
            if (peptideGroup.getEndAlignmentPosition() == -1 && peptideGroup.getStartingAlignmentPosition() == -1) {
                PeptideGroupController.mapPeptideGroupToProtein(protein, peptideGroup);
            }
            //g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
            drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, width, barSize);
        }
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        int startingLocation = (int) Math.ceil(((double) (peptideGroup.getStartingAlignmentPosition()) / barSize));
        int size = (int) Math.ceil((((double) (peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition())) / barSize));
        g.fillRect(horizontalOffset + startingLocation, verticalOffset, size, width);
    }

    public void setPeptideAlpha(float aTransparency) {
        this.transparency = aTransparency;
    }
}
