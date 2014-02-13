package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.secondarystructureprediction.UniprotSecondaryStructurePrediction;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

/**
 *
 * @author Davy
 * @param <T>
 * @param <W>
 */
public class SecondaryStructureProteinDrawMode<T extends Protein, W extends Peptide> extends StandardPeptideProteinDrawMode<T, W> implements GradientDrawModeInterface<T, W> {

    private static UniprotSecondaryStructurePrediction predictor = new UniprotSecondaryStructurePrediction();
    private static final Font font = new Font("Dialog", Font.PLAIN, 24);

    @Override
    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarSize) {
        try {
            g.setFont(font);
            g.drawString(predictor.getPrediction(protein.getProteinAccession()), horizontalOffset, verticalOffset);
        } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
    }

    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        ProgramVariables.STRUCTUREDATASOURCE.getSecondaryStructureForResidue(protein, location);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {

    }

    /**
     * public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g,
     * int horizontalOffset, int verticalOffset, int size, int width) { for
     * (PeptideGroup peptideGroup : peptideGroups) { g.setColor(new Color(255,
     * 255, 255)); g.fillRect(horizontalOffset +
     * peptideGroup.getStartingAlignmentPosition(), verticalOffset,
     * peptideGroup.getEndAlignmentPosition() -
     * peptideGroup.getStartingAlignmentPosition(), width); } }
     *
     * public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int
     * horizontalOffset, int verticalOffset, int size, int width,int barSize,
     * int colourScale) { throw new UnsupportedOperationException("Not supported
     * yet."); //To change body of generated methods, choose Tools | Templates.
     * }
     */
}
