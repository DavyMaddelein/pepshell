package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

/**
 *
 * @author Davy
 */
public class DomainProteinDrawMode<T extends Protein, U extends Peptide> extends StandardPeptideProteinDrawMode<T, U> {

    @Override
    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarSize) throws UndrawableException {
        g.setColor(Color.BLACK);
        g.drawLine(horizontalOffset, verticalOffset + (verticalBarSize / 2), horizontalBarSize, verticalOffset + (verticalBarSize / 2));
        if (protein.getDomains().isEmpty()) {
            throw new UndrawableException("No domain data found.");
        }
        drawDomains(protein, g, horizontalOffset, verticalOffset, verticalBarSize);
    }

    public void drawDomains(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) {
        Iterator<Domain> it = protein.getDomains().iterator();
        int scaler = 0;
        while (it.hasNext()) {
            if (scaler == 6) {
                scaler = 0;
            }
            g.setColor(ProgramVariables.DOMAINCOLOR);
            Domain domain = it.next();
            int domainSize = (int) Math.ceil((((double) (domain.getStopPosition() - domain.getStartPosition())) * ProgramVariables.SCALE));
            int startingLocation = (int) Math.ceil(((double) (domain.getStartPosition()) * ProgramVariables.SCALE));
            g.fillRect(horizontalOffset + startingLocation, verticalOffset, domainSize, verticalBarSize);
            g.setColor(Color.BLACK);
            g.drawString(domain.getDomainName(), horizontalOffset + startingLocation, verticalOffset + 7 + scaler);
            scaler += 2;
        }
    }
}
