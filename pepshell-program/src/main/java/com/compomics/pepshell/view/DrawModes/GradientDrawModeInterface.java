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

package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 * @param <U>
 */
public interface GradientDrawModeInterface<T extends ProteinInterface, U extends PeptideInterface> {

    Color calculateAminoAcidGradient(T protein, int aminoAcidLocation) throws CalculationException;

    Color calculatePeptideGradient(U peptide) throws CalculationException;
    
    void drawColorLegend(Point legendStartPoint, Graphics g);
}
