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
import com.compomics.pepshell.model.gradientMaps.HydrophobicityMaps;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;

/**
 * @param <T>
 * @param <U>
 * @author Davy Maddelein
 */
public class HydrophobicityProteinDrawMode<T extends PepshellProtein, U extends Peptide> extends AbstractPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    private List<Color> colorLegend = new ArrayList();

    @Override
    public void drawProteinAndPeptides(T protein, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        for (int previousEnd = 0; previousEnd < protein.getProteinSequence().length(); previousEnd++) {
            g.setColor(HydrophobicityMaps.getCurrentHydrophobicityMap().get(protein.getProteinSequence().charAt(previousEnd)));
            g.fillRect(startPoint.x + (DrawModeUtilities.getInstance().scale(previousEnd)), startPoint.y, DrawModeUtilities.getInstance().scale(1), height);
        }
    }

    @Override
    public Color calculateAminoAcidGradient(T protein, int location) {
        return HydrophobicityMaps.getCurrentHydrophobicityMap().get(protein.getProteinSequence().charAt(location));

    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        return ProgramVariables.PEPTIDECOLOR;
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        //order colors by red value
        if (colorLegend == null) {
            HydrophobicityMaps.getCurrentHydrophobicityMap().values().stream()
                    .sorted().distinct().forEach((sortedColor) -> {
                g.setColor(sortedColor);
                g.fillRect(legendStartPoint.x + colorLegend.size() * 5, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
                colorLegend.add(sortedColor);
            });
        }
        colorLegend.stream().forEach((sortedColor) -> {
            g.setColor(sortedColor);
            g.fillRect(legendStartPoint.x + colorLegend.indexOf(sortedColor) * 5, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
        });

        g.setColor(Color.black);
        g.drawString("acidic", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("basic", legendStartPoint.x + colorLegend.size() * 5 - 5, legendStartPoint.y + 25);
    }
}
