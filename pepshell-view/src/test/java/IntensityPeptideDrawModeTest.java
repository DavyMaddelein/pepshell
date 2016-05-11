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

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshellstandalone.drawmodes.Peptides.IntensityPeptideDrawMode;
import org.junit.Test;

/**
 * Created by Iain on 18/02/2015.
 */
public class IntensityPeptideDrawModeTest {


    @Test
    public void testCalculatePeptideGradient() throws CalculationException {

        IntensityPeptideDrawMode drawMode = new IntensityPeptideDrawMode();
        Peptide pep = new Peptide("QQQQQQQQ");
        pep.setBeginningProteinMatch(1);
        pep.setEndProteinMatch(10);
        pep.addTotalSpectrumIntensity(15.0);
        pep.addTotalSpectrumIntensity(10000.0);
        pep.addTotalSpectrumIntensity((double) Integer.MAX_VALUE);
        drawMode.setMaxIntensity(1000000000000.0);
        drawMode.calculatePeptideGradient(pep);
    }


}
