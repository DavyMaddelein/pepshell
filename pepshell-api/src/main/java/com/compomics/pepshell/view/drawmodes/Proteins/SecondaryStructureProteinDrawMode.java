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

package com.compomics.pepshell.view.drawmodes.Proteins;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.model.gradientMaps.SecondaryStructureMaps;
import com.compomics.pepshell.view.drawmodes.DrawModeUtilities;
import com.compomics.pepshell.view.drawmodes.DrawProteinPeptidesInterface;
import com.compomics.pepshell.view.drawmodes.GradientDrawModeInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public class SecondaryStructureProteinDrawMode<T extends PepshellProtein, U extends Peptide> implements DrawProteinPeptidesInterface<T, U>, GradientDrawModeInterface<T, U> {

    private String pdbAccession;

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {

        if (ProgramVariables.STRUCTUREDATASOURCE.isAbleTogetSecondaryStructure() && pdbAccession != null) {
            Map<Integer, String> secondaryStructurePrediction = ProgramVariables.STRUCTUREDATASOURCE.getSecondaryStructureForStructure(protein, pdbAccession);
            for (Entry<Integer, String> location : secondaryStructurePrediction.entrySet()) {
                if (location.getValue() != null) {
                    g.setColor(SecondaryStructureMaps.getColorMap().get(location.getValue()));
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(startPoint.x + (DrawModeUtilities.getInstance().scale(location.getKey())), startPoint.y, DrawModeUtilities.getInstance().scale(1), height);
            }
        } else {
            g.setColor(Color.black);
            g.drawString("could not draw the secondary structure", startPoint.x, startPoint.y + 5);
            throw new UndrawableException("could not calculate gradient");
        }
    }

    @Override
    public Color calculatePeptideGradient(Peptide peptide) throws CalculationException {
        return DrawModeUtilities.getPeptideColor();
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        int colorCounter = 0;
        for (Entry<String, Color> entry : SecondaryStructureMaps.getColorMap().entrySet()) {
            g.setColor(entry.getValue());
            g.fillRect(legendStartPoint.x + colorCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            g.drawString(entry.getKey(), legendStartPoint.x + 10 + colorCounter, legendStartPoint.y + 7);
            colorCounter += ProgramVariables.VERTICALSIZE;
            colorCounter += 15;
        }

    }

    public void setPdbAccession(String pdbAccession) {
        this.pdbAccession = pdbAccession;
    }
}
