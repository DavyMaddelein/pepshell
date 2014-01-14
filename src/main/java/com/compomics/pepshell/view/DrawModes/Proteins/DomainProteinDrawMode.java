package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder.DomainWebSites;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 */
public class DomainProteinDrawMode extends StandardPeptideProteinDrawMode {

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int height) {
        try {
            g.drawLine(horizontalOffset, verticalOffset + (height / 2), size, verticalOffset + (height / 2));
            if (protein.getDomains().isEmpty()) {
                try {
                    //ExternalDomainFinder.addDomainsToProtein(protein);
                    try {
                        protein.addDomains(ExternalDomainFinder.getDomainsForUniprotAccessionFromSingleSource(AccessionConverter.ToUniprot(protein.getProteinAccession()), DomainWebSites.PFAM));
                    } catch (ConversionException e) {
                    }
                } catch (XMLStreamException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            }
            for (Domain domain : protein.getDomains()) {
                g.setColor(Color.LIGHT_GRAY);
                double barsize = (double) protein.getProteinSequence().length() / (double) size;
                int domainSize = (int) Math.ceil((((double) (domain.getStopPosition() - domain.getStartPosition())) / barsize));
                int startingLocation = (int) Math.ceil(((double) (domain.getStartPosition()) / barsize));
                g.fillRect(horizontalOffset + startingLocation, verticalOffset, domainSize, height);
                g.setColor(Color.BLACK);
                g.drawString(domain.getDomainName(), horizontalOffset + startingLocation, verticalOffset + 7);
            }
            drawPeptides(protein, g, horizontalOffset, verticalOffset, height, (double) protein.getProteinSequence().length() / (double) size);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "there has been a problem retrieving the domains for protein: " + protein.getProteinAccession());
            FaultBarrier.getInstance().handleException(ex);
        }
    }
}
