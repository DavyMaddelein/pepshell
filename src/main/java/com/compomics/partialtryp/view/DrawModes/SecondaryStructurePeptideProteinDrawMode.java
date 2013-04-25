package com.compomics.partialtryp.view.DrawModes;

import com.compomics.partialtryp.FaultBarrier;
import com.compomics.partialtryp.controllers.secondarystructureprediction.UniprotSecondaryStructurePrediction;
import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Protein;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Davy
 */
public class SecondaryStructurePeptideProteinDrawMode implements PeptideProteinDrawMode {

    private static UniprotSecondaryStructurePrediction predictor = new UniprotSecondaryStructurePrediction();
    private static FaultBarrier faultBarrier = FaultBarrier.getInstance();
    private static final Font font = new Font("Dialog", Font.PLAIN, 24);

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        try {
            g.setFont(font);
            g.drawString(predictor.getPrediction(protein.getProteinAccession()), horizontalOffset, verticalOffset);
        } catch (IOException ex) {
            faultBarrier.handleError(ex);
        }
    }

    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        for (PeptideGroup peptideGroup : peptideGroups) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
        }
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int size, int width,int barSize, int colourScale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
