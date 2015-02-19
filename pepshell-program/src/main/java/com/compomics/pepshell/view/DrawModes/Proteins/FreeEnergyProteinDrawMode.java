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
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public class FreeEnergyProteinDrawMode<T extends PepshellProtein, U extends Peptide> extends AbstractPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleToGetFreeEnergy() && protein.getPreferredPdbFile() != null) {
            Map<Integer, Double> freeEnergyValues = ProgramVariables.STRUCTUREDATASOURCE.getFreeEnergyForStructure(protein, protein.getPreferredPdbFile());
            //go over all locations retrieved from the data source
            for (int location : freeEnergyValues.keySet()) {
                Double freeEnergyValue = freeEnergyValues.get(location);
                //check for null values
                if (freeEnergyValue != null) {
                    Color freeEnergyGradientColor = new Color(Math.min((int) Math.ceil(freeEnergyValue * 255), 255), 255, 125);
                    g.setColor(freeEnergyGradientColor);
                } else {
                    g.setColor(Color.WHITE);
                }
                int scaledLength = DrawModeUtilities.getInstance().scale(1 / protein.getProteinSequence().length());
                g.fillRect(startPoint.x + DrawModeUtilities.getInstance().scale(location), startPoint.y, scaledLength, height);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the free energy", startPoint.x, startPoint.y + 5);
            throw new UndrawableException("could not calculate gradient");
        }
    }

    @Override
    public Color calculateAminoAcidGradient(T protein, int location) throws CalculationException {
        throw new UnsupportedOperationException("requires refactor");
    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    @Override
    public void drawColorLegend(Point legendDrawPoint, Graphics g) {
        int colorCounter = 0;
        while (colorCounter < 64) {
            g.setColor(new Color(colorCounter * 4, 255, 125));
            g.fillRect(legendDrawPoint.x + colorCounter, legendDrawPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 1;
        }
        g.setColor(Color.black);
        g.drawString("low", legendDrawPoint.x, legendDrawPoint.y + 25);
        g.drawString("high", legendDrawPoint.x + colorCounter - 5, legendDrawPoint.y + 25);

    }
}
