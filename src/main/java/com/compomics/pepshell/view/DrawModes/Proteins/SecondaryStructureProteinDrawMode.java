package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.secondarystructureprediction.UniprotSecondaryStructurePrediction;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class SecondaryStructureProteinDrawMode extends StandardPeptideProteinDrawMode {

    private static UniprotSecondaryStructurePrediction predictor = new UniprotSecondaryStructurePrediction();
    private static FaultBarrier faultBarrier = FaultBarrier.getInstance();
    private static final Font font = new Font("Dialog", Font.PLAIN, 24);

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        try {
            g.setFont(font);
            g.drawString(predictor.getPrediction(protein.getProteinAccession()), horizontalOffset, verticalOffset);
        } catch (IOException ex) {
            faultBarrier.handleException(ex);
        }
    }
/**
    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        for (PeptideGroup peptideGroup : peptideGroups) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
        }
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int size, int width,int barSize, int colourScale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
