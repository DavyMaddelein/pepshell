package com.compomics.partialtryp.view.DrawModes;

import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.QuantedPeptideGroup;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Davy
 */
public class StandardPeptideProteinDrawMode implements PeptideProteinDrawMode {

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        g.setColor(Color.PINK);
        g.fillRect(horizontalOffset, verticalOffset, size, width);
    }

    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        for (PeptideGroup peptideGroup : peptideGroups) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
        }
    }

    public void drawPeptide(QuantedPeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int proteinLength, int width, int colourScale) {
        g.setColor(new Color(255 * colourScale, 133, 0));
        int startingLocation = (int)((peptideGroup.getStartingAlignmentPosition()/(double)proteinLength)*1000);
        int size = (int)(((peptideGroup.getEndAlignmentPosition()-peptideGroup.getStartingAlignmentPosition())/(double)proteinLength)*1000);
        g.fillRect(horizontalOffset + startingLocation, verticalOffset, size , width);
    
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int proteinLength, int width,int barSize, int colourScale) {
        g.setColor(new Color(255 * colourScale, 133, 0));
        int startingLocation = (int)Math.ceil(((peptideGroup.getStartingAlignmentPosition()/(double)proteinLength)*barSize));
        int size = (int)Math.ceil((((peptideGroup.getEndAlignmentPosition()-peptideGroup.getStartingAlignmentPosition())/(double)proteinLength)*barSize));
        g.fillRect(horizontalOffset + startingLocation, verticalOffset, size , width);
    }
}
