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

import com.compomics.pepshellstandalone.ProgramVariables;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 * @param <U>
 */
public interface GradientDrawModeInterface<U extends PeptideInterface> {

    Color calculatePeptideGradient(U peptide) throws CalculationException;
    
    default void drawColorLegend(Point legendStartPoint, Graphics g){
        int colourCounter = 0;
        while (colourCounter < 51) {
            g.setColor(new Color(colourCounter * 5, 255 - (colourCounter * 5), 115));
            g.fillRect(legendStartPoint.x + colourCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colourCounter += 5;
        }

        g.setColor(Color.black);
        g.drawString("low", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("high", legendStartPoint.x + colourCounter - 5, legendStartPoint.y + 25);

    }
}
