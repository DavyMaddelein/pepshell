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

package com.compomics.pepshellstandalone.drawmodes;

import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import javaslang.Tuple2;

import java.awt.*;

/**
 * the root drawing interface for all the protein and peptide visualisations in
 * the project comparison
 * <p/>
 * the interface is aimed at drawing proteins and its accompanying peptides at a given alpha value on a graphics object
 *
 * @param <T> the pepshell protein type to draw
 * @param <U> the pepshell peptide type to draw
 * @author Davy Maddelein
 */
public interface DrawProteinPeptidesInterface<T extends PepshellProtein, U extends PeptideInterface> {


    /**
     * method to draw a protein on the given graphics with the given parameters
     * the drawn protein and peptides will be scaled horizontally to the set
     * scale in the {@link DrawModeUtilities} class {@literal .}. The colour of
     * the protein and the peptides will be the ones defined in the program
     * variables. The alpha will be the ones set by the alpha setters, standard
     * this is 1 {@literal .} if there were no exceptions raised, the composite
     * is returned to its original state.
     *
     * @param protein the protein to draw
     * @param g the graphics to draw on
     * @param startPoint the upper left point to start drawing the protein and
     * its peptides
     * @param length the horizontal size of the protein bar drawn on the
     * graphics
     * @param height the vertical size of the protein bar drawn on the graphics
     * @throws UndrawableException thrown when the given protein could not be
     * drawn,this will leave the composite of the graphics object pointing to an
     * alpha composite partial execution will result in partial drawing of the
     * protein and peptides
     */
    default void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        //set colour and alpha for protein
        g.setColor(DrawModeUtilities.getProteinColor());
        Composite defensiveComposite = ((Graphics2D) g).getComposite();
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DrawModeUtilities.getProteinAlpha()));
        //actually draw the protein
        g.fillRect(startPoint.x, startPoint.y, length, height);

        //protein has been drawn, we move on to the peptides, first alpha
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DrawModeUtilities.getPeptideAlpha()));

        //we will use this point as a sliding starting point for each peptide
        Point peptideStartPoint = (Point) startPoint.clone();
        //start on the proteins, we will draw the shortest peptide in the peptide group
        for (PeptideGroup aGroup : protein.getPeptideGroups()) {
            PeptideInterface peptideToDraw = aGroup.getRepresentativePeptide();
            if (peptideToDraw.getEndProteinMatch() != -1 && peptideToDraw.getBeginningProteinMatch() != -1) {
                //we scale the length of the peptide to fit the protein context
                int scaledLength = DrawModeUtilities.getInstance().scale(peptideToDraw.getEndProteinMatch() - peptideToDraw.getBeginningProteinMatch());
                //we scale the starting point of the peptide to the context of the peptide
                peptideStartPoint.x = DrawModeUtilities.getInstance().scale(peptideToDraw.getBeginningProteinMatch()) + startPoint.x;
                //look up how to fix this generics brainfart
                drawPeptide((U) peptideToDraw, g, peptideStartPoint, scaledLength, height);
            }
        }
        ((Graphics2D) g).setComposite(defensiveComposite);

    }

    /**
     * @param peptide
     * @param g
     * @param startPoint
     * @param length
     * @param height
     */
    default void drawPeptide(U peptide, Graphics g, Point startPoint, int length, int height) {
        g.setColor(DrawModeUtilities.getPeptideColor());
        ((Graphics2D) g).setStroke(new BasicStroke(2F));
        g.drawRect(startPoint.x, startPoint.y, length, height);
    }

}
