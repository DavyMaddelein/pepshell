package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder.DomainWebSites;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 */
public class DomainProteinDrawMode<T extends Protein, U extends Peptide> extends StandardPeptideProteinDrawMode<T, U> {

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarSize) throws UndrawableException {
        try {
            g.drawLine(horizontalOffset, verticalOffset + (verticalBarSize / 2), horizontalBarSize, verticalOffset + (verticalBarSize / 2));
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
            drawDomains(protein, g, horizontalOffset, verticalOffset, verticalBarSize);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "there has been a problem retrieving the domains for protein: " + protein.getProteinAccession());
            FaultBarrier.getInstance().handleException(ex);
        }
    }

    public void drawDomains(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) {
        g.setColor(ProgramVariables.DOMAINCOLOR);
        Iterator<Domain> it = protein.getDomains().iterator();
        while (it.hasNext()) {
            Domain domain = it.next();
            int domainSize = (int) Math.ceil((((double) (domain.getStopPosition() - domain.getStartPosition())) * ProgramVariables.SCALE));
            int startingLocation = (int) Math.ceil(((double) (domain.getStartPosition()) * ProgramVariables.SCALE));
            g.fillRect(horizontalOffset + startingLocation, verticalOffset, domainSize, verticalBarSize);
            g.setColor(Color.BLACK);
            g.drawString(domain.getDomainName(), horizontalOffset + startingLocation, verticalOffset + 7);
        }
    }
}
