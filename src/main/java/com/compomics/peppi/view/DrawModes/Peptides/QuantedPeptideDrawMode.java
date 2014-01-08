package com.compomics.peppi.view.DrawModes.Peptides;

import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.QuantedPeptideGroup;
import com.compomics.peppi.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class QuantedPeptideDrawMode extends StandardPeptideProteinDrawMode {

    @Override
    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int width, double barSize) {
        if (peptideGroup instanceof QuantedPeptideGroup) {
            g.setColor(Color.yellow);
        }
        g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
    }

    public int calculateRatioColor(QuantedPeptideGroup aQuantedPeptideGroup) {
        int returnValue = 0;
        //log quant code from rover
        if (aQuantedPeptideGroup.getRatio() > ((aQuantedPeptideGroup.getProjectInfo().getMaxRatio() + aQuantedPeptideGroup.getProjectInfo().getMinRatio()) / 2)) {
        } else {
        }
        return returnValue;
    }
}
