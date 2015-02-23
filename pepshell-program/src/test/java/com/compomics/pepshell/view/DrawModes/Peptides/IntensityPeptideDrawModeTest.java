package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
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
