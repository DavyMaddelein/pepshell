package com.compomics.peppi.view.DrawModes;

import com.compomics.peppi.controllers.properties.ViewProperties;
import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.Proteases;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.enums.ViewPropertyEnum;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
/**
 *
 * @author Davy
 */
public class StandardPeptideProteinDrawMode {

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        g.setColor(Color.PINK);
        List<String> possiblePeptides = Proteases.getProteaseMap().get(ViewProperties.getInstance().getProperty(ViewPropertyEnum.PREFERREDENZYME.getKey())).digest(protein.getProteinSequence());
        g.fillRect(horizontalOffset, verticalOffset, size, width);
        for (String aPossiblePeptide : possiblePeptides) {
            //TODO: what about multiple times the same peptide?
            drawPossiblePeptide(protein.getProteinSequence().indexOf(aPossiblePeptide), aPossiblePeptide.length(), g, horizontalOffset, verticalOffset, width);
        }

    }

    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int width, int barSize) {
        for (PeptideGroup peptideGroup : peptideGroups) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
        }
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int proteinLength, int width, int barSize) {
        g.setColor(new Color(255, 133, 0));
        int startingLocation = (int) Math.ceil(((peptideGroup.getStartingAlignmentPosition() / (double) proteinLength) * barSize));
        int size = (int) Math.ceil((((peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition()) / (double) proteinLength) * barSize));
        g.fillRect(horizontalOffset + startingLocation, verticalOffset, size, width);
    }

    private void drawPossiblePeptide(int possiblePeptideStart, int possiblePeptideStop, Graphics g, int horizontalOffset, int verticalOffset, int width) {
        g.fillRect(horizontalOffset + possiblePeptideStart, verticalOffset, possiblePeptideStop, width);
    }
}
