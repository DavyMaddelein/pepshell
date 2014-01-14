package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Davy
 */
public class StandardPeptideDrawMode extends StandardPeptideProteinDrawMode {

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
    }

    @Override
    public void drawPeptides(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        g.setColor(Color.BLACK);
        for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
            if (peptideGroup.getEndAlignmentPosition() == -1 || peptideGroup.getStartingAlignmentPosition() == -1) {
                PeptideGroupController.mapPeptideGroupToProtein(protein, peptideGroup);
            }
            //drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, width, barSize);
            drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, width, protein.getProteinSequence().length() / (double) barSize);

        }
    }

    @Override
    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        int startingLocation = (int) Math.ceil(((double) (peptideGroup.getStartingAlignmentPosition()) / barSize));
        int size = (int) Math.ceil((((double) (peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition())) / barSize));
        g.drawRect(horizontalOffset + startingLocation, verticalOffset, size, width);
        g.drawString(String.valueOf(peptideGroup.getStartingAlignmentPosition()), startingLocation, verticalOffset);
    }
}
