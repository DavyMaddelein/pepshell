package com.compomics.peppi.view.DrawModes;

import com.compomics.peppi.model.HydrophilicityMaps;
import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.Protein;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
//TODO standard and hydrofilicity extend abstract class graphical protein representation
public class HydrophilicityPeptideProteinDrawMode implements PeptideProteinDrawMode {

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int width, int height) {
        List<Integer> gradientArray = calculateGradient(protein);
        int scale = (int) ((width/(double)protein.getProteinSequence().length()));
        int previousEnd = 0;
        for (int aminoAcidColor : gradientArray) {
            g.setColor(new Color(aminoAcidColor, 0, 225));
            g.fillRect(horizontalOffset + previousEnd, verticalOffset, scale, height);
            previousEnd += scale;
        }
    }

    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int hight) {
        for (PeptideGroup peptideGroup : peptideGroups) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), hight);
        }
    }

    private List<Integer> calculateGradient(Protein protein) {
        ArrayList<Integer> gradientList = new ArrayList<Integer>();
        String[] proteinSequence = protein.getProteinSequence().split("");
        int i = 1;
        while (i < proteinSequence.length) {
            Double temp = HydrophilicityMaps.getHydrophilicityMapPh7().get(proteinSequence[i]) * 255;
            gradientList.add(temp.intValue());
            i++;
        }
        return gradientList;
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int proteinLength, int hight, int barSize, int colourScale) {
        g.setColor(new Color(255 * colourScale, 133, 0));
        int startingLocation = (int) ((peptideGroup.getStartingAlignmentPosition() / (double) proteinLength) * barSize);
        int size = (int) (((peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition()) / (double) proteinLength) * barSize);
        g.fillRect(horizontalOffset + startingLocation, verticalOffset, size, hight);
    }
}
