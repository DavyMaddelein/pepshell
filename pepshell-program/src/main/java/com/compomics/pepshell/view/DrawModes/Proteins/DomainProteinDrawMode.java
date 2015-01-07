package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public class DomainProteinDrawMode<T extends Protein, U extends Peptide> extends AbstractPeptideProteinDrawMode<T, U> {

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        Composite originalComposite = ((Graphics2D)g).getComposite();
//g.setColor(Color.BLACK);
        //g.drawLine(startPoint.x, startPoint.y + (height / 2), length, startPoint.y + (height / 2));
        ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,proteinAlpha));
        Point domainStartPoint = (Point) startPoint.clone();
        for (Domain domain : protein.getDomains()) {
            int scaledLength = DrawModeUtilities.getInstance().scale(domain.getStopPosition() - domain.getStartPosition());
            domainStartPoint.x = DrawModeUtilities.getInstance().scale(domain.getStartPosition());
            drawDomain(domain, g, domainStartPoint, scaledLength, height);

        }
        ((Graphics2D)g).setComposite(originalComposite);
    }

    void drawDomain(Domain domain, Graphics g, Point domainStartPoint, int length, int height) {
        g.setColor(ProgramVariables.DOMAINCOLOR);
        g.fillRect(domainStartPoint.x, domainStartPoint.y, length, height);
        g.setColor(Color.BLACK);
        g.drawString(domain.getDomainName(), domainStartPoint.x, domainStartPoint.y);
    }
}