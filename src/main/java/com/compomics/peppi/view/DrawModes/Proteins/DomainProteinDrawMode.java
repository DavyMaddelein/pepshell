package com.compomics.peppi.view.DrawModes.Proteins;

import com.compomics.peppi.FaultBarrier;
import com.compomics.peppi.controllers.InfoFinders.DomainFinder;
import com.compomics.peppi.model.Domain;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.ConversionException;
import com.compomics.peppi.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 */
public class DomainProteinDrawMode extends StandardPeptideProteinDrawMode {


    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        try {
            int proteinLength = protein.getProteinSequence().length();
            g.drawLine(horizontalOffset, verticalOffset + 7, horizontalOffset + proteinLength, verticalOffset + 7);
            if (protein.getDomains().isEmpty()) {
                try {
                    DomainFinder.addDomainsToProtein(protein);
                } catch (XMLStreamException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            }
            for (Domain domain : protein.getDomains()) {
                g.setColor(Color.LIGHT_GRAY);
                int startingLocation = (int) ((domain.getStartPosition() / (double) proteinLength) * size);
                int domainSize = (int) (((domain.getStopPosition() - domain.getStartPosition()) / (double) proteinLength) * size);
                g.fillRect(horizontalOffset + startingLocation, verticalOffset, domainSize, width);
                g.setColor(Color.BLACK);
                g.drawString(domain.getDomainName(), horizontalOffset + startingLocation, verticalOffset + 7);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "there has been a problem retrieving the domains for protein: " + protein.getProteinAccession());
            FaultBarrier.getInstance().handleException(ex);
        } catch (ConversionException ex) {
            JOptionPane.showMessageDialog(null, "there has been a problem retrieving the domains for protein: " + protein.getProteinAccession());
            FaultBarrier.getInstance().handleException(ex);
        }
    }
    /**
     * public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g,
     * int horizontalOffset, int verticalOffset, int size, int width) { for
     * (PeptideGroup peptideGroup : peptideGroups) { //TODO set decent color
     * scale g.setColor(new Color(255, 255, 255)); g.fillRect(horizontalOffset +
     * peptideGroup.getStartingAlignmentPosition(), verticalOffset,
     * peptideGroup.getEndAlignmentPosition() -
     * peptideGroup.getStartingAlignmentPosition(), width); } }
     *
     * public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int
     * horizontalOffset, int verticalOffset, int proteinLength, int hight, int
     * barSize, int colourScale) { g.setColor(new Color(255 * colourScale, 133,
     * 0)); int startingLocation = (int)
     * ((peptideGroup.getStartingAlignmentPosition() / (double) proteinLength) *
     * barSize); int size = (int) (((peptideGroup.getEndAlignmentPosition() -
     * peptideGroup.getStartingAlignmentPosition()) / (double) proteinLength) *
     * barSize); g.fillRect(horizontalOffset + startingLocation, verticalOffset,
     * size, hight); }
     */
}
