package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class HydrophilicityProteinDrawMode extends StandardPeptideProteinDrawMode {

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        int scale = (int) Math.ceil(((double) size / (double) protein.getProteinSequence().length()));
        int previousEnd = 0;
        g.setColor(new Color(100, 0, 225));
        g.fillRect(horizontalOffset + previousEnd, verticalOffset, scale, width);
        previousEnd += scale;
        drawPeptides(protein, g, horizontalOffset, verticalOffset, width, (double) protein.getProteinSequence().length() / (double) size);
    }

    private static List<Integer> calculateGradient(Protein protein, Map<String, Double> HydrophilicityMap) {
        ArrayList<Integer> gradientList = new ArrayList<Integer>();
        String[] proteinSequence = protein.getProteinSequence().split("");
        int i = 1;
        while (i < proteinSequence.length) {
            Double temp = HydrophilicityMap.get(proteinSequence[i]) * 255;
            gradientList.add(temp.intValue());
            i++;
        }
        return gradientList;
    }
}
