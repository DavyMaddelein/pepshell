/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.view.DrawModes;

import com.compomics.partialtryp.FaultBarrier;
import com.compomics.partialtryp.controllers.InfoFinders.DomainFinder;
import com.compomics.partialtryp.model.Domain;
import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.exceptions.ConversionException;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class DomainPeptideProteinDrawMode implements PeptideProteinDrawMode {

    private static FaultBarrier faultBarrier = new FaultBarrier();

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        try {
            int proteinLength = protein.getProteinSequence().length();
            g.drawLine(horizontalOffset, verticalOffset + 7, horizontalOffset + proteinLength, verticalOffset + 7);
            if (protein.getDomains().isEmpty()) {
                DomainFinder.addDomainsToProtein(protein);
            }
            for (Domain domain : protein.getDomains()) {
                g.setColor(Color.LIGHT_GRAY);
                int startingLocation = (int) ((domain.getStartPosition() / (double) proteinLength) * size);
                int domainSize = (int) (((domain.getStopPosition() - domain.getStartPosition()) / (double) proteinLength) * size);
                g.fillRect(horizontalOffset + startingLocation, verticalOffset, domainSize, width);
                g.setColor(Color.BLACK);
                g.drawString(domain.getDomainName(), horizontalOffset+ startingLocation, verticalOffset+7);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "there has been a problem retrieving the domains for protein: " + protein.getProteinAccession());
            faultBarrier.handleError(ex);
        } catch (ConversionException ex) {
            JOptionPane.showMessageDialog(null, "there has been a problem retrieving the domains for protein: " + protein.getProteinAccession());
            faultBarrier.handleError(ex);
        }
    }

    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int width) {
        for (PeptideGroup peptideGroup : peptideGroups) {
            //TODO set decent color scale
            g.setColor(new Color(255, 255, 255));
            g.fillRect(horizontalOffset + peptideGroup.getStartingAlignmentPosition(), verticalOffset, peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition(), width);
        }
    }

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int proteinLength, int hight, int barSize, int colourScale) {
        g.setColor(new Color(255 * colourScale, 133, 0));
        int startingLocation = (int) ((peptideGroup.getStartingAlignmentPosition() / (double) proteinLength) * barSize);
        int size = (int) (((peptideGroup.getEndAlignmentPosition() - peptideGroup.getStartingAlignmentPosition()) / (double) proteinLength) * barSize);
        g.fillRect(horizontalOffset + startingLocation, verticalOffset, size, hight);
    }
}
