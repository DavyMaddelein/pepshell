/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshellstandalone.drawmodes.Proteins;

import com.compomics.pepshellstandalone.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshellstandalone.drawmodes.DrawModeUtilities;
import com.compomics.pepshellstandalone.drawmodes.DrawProteinPeptidesInterface;

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
public class DomainProteinDrawMode<T extends PepshellProtein, U extends Peptide> implements DrawProteinPeptidesInterface<T, U> {

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        Composite originalComposite = ((Graphics2D)g).getComposite();
//g.setColor(Color.BLACK);
        //g.drawLine(startPoint.x, startPoint.y + (height / 2), length, startPoint.y + (height / 2));
        ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,DrawModeUtilities.getProteinAlpha()));
        Point domainStartPoint = (Point) startPoint.clone();
        for (FeatureWithLocation domain : protein.getDomains()) {
            int scaledLength = DrawModeUtilities.getInstance().scale(domain.getEndPosition() - domain.getStartPosition());
            domainStartPoint.x = DrawModeUtilities.getInstance().scale(domain.getStartPosition());
            drawDomain(domain, g, domainStartPoint, scaledLength, height);

        }
        ((Graphics2D)g).setComposite(originalComposite);
    }

    void drawDomain(FeatureWithLocation domain, Graphics g, Point domainStartPoint, int length, int height) {
        g.setColor(ProgramVariables.DOMAINCOLOR);
        g.fillRect(domainStartPoint.x, domainStartPoint.y, length, height);
        g.setColor(Color.BLACK);
        g.drawString(domain.getDescription(), domainStartPoint.x, domainStartPoint.y);
    }
}
