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
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.gradientMaps.HydrophobicityMaps;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.SortedSet;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public class HydrophobicityProteinDrawMode<T extends Protein, U extends Peptide> extends AbstractPeptideProteinDrawMode<T, U> implements GradientDrawModeInterface<T, U> {

    private SortedSet<Color> colorLegend;

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
            Ordering<Color> colorOrdering = Ordering.natural().onResultOf(getRedValue);
            colorLegend = ImmutableSortedSet.orderedBy(colorOrdering).addAll(HydrophobicityMaps.getCurrentHydrophobicityMap().values()).build();
        }
        int colorCounter = 0;
        for (Color aColor : colorLegend) {
            g.setColor(aColor);
            g.fillRect(legendStartPoint.x + colorCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colorCounter += 5;
        }
        g.setColor(Color.black);
        g.drawString("acidic", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("basic", legendStartPoint.x + colorCounter - 5, legendStartPoint.y + 25);
    }

    private Function<Color, Integer> getRedValue = new Function<Color, Integer>() {
        @Override
        public Integer apply(Color input) {
            return input.getRed();
        }
    };
}
