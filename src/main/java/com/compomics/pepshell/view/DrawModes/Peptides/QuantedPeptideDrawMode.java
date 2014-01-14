package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.QuantedPeptideGroup;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
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
            g.setColor(new Color(calculateRatioColor((QuantedPeptideGroup)peptideGroup), 255, 255));
        }
        super.drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, width, barSize);
    }

    public int calculateRatioColor(QuantedPeptideGroup aQuantedPeptideGroup) {
        int returnValue = 125;
        //log quant code from rover
        if (aQuantedPeptideGroup.getRatio() > ((aQuantedPeptideGroup.getProjectInfo().getMaxRatio() + aQuantedPeptideGroup.getProjectInfo().getMinRatio()) / 2)) {
            returnValue = (int)Math.ceil(125 * aQuantedPeptideGroup.getLogRatio());
        } else {
        }
        return returnValue;
    }
}
