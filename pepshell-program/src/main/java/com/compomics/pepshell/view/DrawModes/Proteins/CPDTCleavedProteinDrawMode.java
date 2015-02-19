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

package com.compomics.pepshell.view.DrawModes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
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
 * this drawing mode is meant to only add CPDT 
 * 
 * @author Davy Maddelein
 */

public class CPDTCleavedProteinDrawMode extends AbstractPeptideProteinDrawMode<Protein, Peptide> implements GradientDrawModeInterface<Protein, Peptide> {

    @Override
    public void drawProteinAndPeptides(Protein protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        //set colour and alpha for protein
        g.setColor(proteinColor);
        Composite defensiveComposite = ((Graphics2D) g).getComposite();
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, proteinAlpha));
        //actually draw the protein
        g.fillRect(startPoint.x, startPoint.y, length, height);

        //protein has been drawn, we move on to the peptides, first alpha
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, peptideAlpha));

        //we will use this point as a sliding starting point for each peptide
        Point peptideStartPoint = (Point)startPoint.clone();
        //start on the proteins, we will draw the shortest peptide in the peptide group
        for (PeptideGroup aGroup : protein.getCPDTPeptideList()) {
            PeptideInterface peptideToDraw = aGroup.getShortestPeptide();
            if (peptideToDraw.getBeginningProteinMatch() != -1) {
                //we scale the length of the peptide to fit the protein context
                int scaledLength = DrawModeUtilities.getInstance().scale(peptideToDraw.getEndProteinMatch() - peptideToDraw.getBeginningProteinMatch());
                //we scale the starting point of the peptide to the context of the peptide
                peptideStartPoint.x = DrawModeUtilities.getInstance().scale(peptideToDraw.getBeginningProteinMatch()) + startPoint.x;
                //look up how to fix this generics brainfart
                drawPeptide((Peptide) peptideToDraw, g, peptideStartPoint, scaledLength, height);
            }
        }
        ((Graphics2D) g).setComposite(defensiveComposite);
     //To change body of generated methods, choose Tools | Templates.
    }

    
    
    /**
     * this method draw a peptide gotten from the CPDT analysis, it only draws a straight line denoting the
     * starting location of the predicted peptide and colours this location according to the CPDT confidence
     * @param peptide the CPDT peptide to draw
     * @param g the graphics to draw the peptide on
     * @param startPoint
     * @param length
     * @param height
     * @throws UndrawableException should an error occur while drawing the CPDT peptide
     */
    
    @Override
    public void drawPeptide(Peptide peptide, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            throw new UndrawableException("could not display probability of the calculated peptide");
        }
        if (peptide.getBeginningProteinMatch() == -1) {
            throw new UndrawableException("could not determine relative position of the peptide");
        }
        g.drawRect(startPoint.x, startPoint.y, 1, height);
    }

    /**
     * {@inheritDoc }
     * @param protein
     * @param location
     * @return 
     * @throws com.compomics.pepshell.model.exceptions.CalculationException 
     */
    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) throws CalculationException {
        return proteinColor;
    }

    @Override
    public void drawColorLegend(Point startPoint, Graphics g) {
        // 64 shadings of the Color define by ((int) Math.ceil(255-(255*peptide.getProbability())),(int) Math.ceil(255 * peptide.getProbability()), 0)
        //the CPDT cutoff is fixed to 0.7 for the moment 
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     * @param peptide
     * @return 
     * @throws CalculationException
     */
    @Override
    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        return new Color((int) Math.ceil(255-(255*peptide.getProbability())),(int) Math.ceil(255 * peptide.getProbability()), 0);
    }
}
