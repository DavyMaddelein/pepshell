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
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshellstandalone.drawmodes.DrawModeUtilities;
import com.compomics.pepshellstandalone.drawmodes.DrawProteinPeptidesInterface;
import com.compomics.pepshellstandalone.drawmodes.GradientDrawModeInterface;
import com.compomics.pepshellstandalone.exceptionhandling.ExceptionEvent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

/**
 * @param <T>
 * @param <U>
 * @author Davy Maddelein
 */
public class SolventAccessibleProteinDrawMode<T extends PepshellProtein, U extends Peptide> implements DrawProteinPeptidesInterface<T, U>, GradientDrawModeInterface<U> {

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint,int length, int height) throws UndrawableException {
        Map<Integer, Double> relSasValues = ProgramVariables.relativeSolventAccessibilityPredictor.getRelativeSolventAccessibilityForStructure(protein);
        //go over all locations retrieved from the data source

        if(relSasValues.containsKey(null)){
            ProgramVariables.exceptionBus.post(new ExceptionEvent("could not determine relative solvent accessability",false));
        }

        for (int location : relSasValues.keySet()) {
            Double relSasValue = relSasValues.get(location);
            //check for null values
            if (relSasValue != null) {
                Color relativeAccessibilityGradientColor = new Color(Math.min((int) Math.ceil(relSasValue * 255), 255), 255, 125);
                g.setColor(relativeAccessibilityGradientColor);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(startPoint.x + DrawModeUtilities.getInstance().scale(location), startPoint.y, DrawModeUtilities.getInstance().scale(1), height);
        }
    }


    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return DrawModeUtilities.getPeptideColor();
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        int colorCounter = 0;
        while (colorCounter < 64) {
            g.setColor(new Color(colorCounter * 4, 255, 125));
            g.fillRect(legendStartPoint.x + colorCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 1;
        }
        g.setColor(Color.black);
        g.drawString("low", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("high", legendStartPoint.x + colorCounter - 5, legendStartPoint.y + 25);
    }
}
