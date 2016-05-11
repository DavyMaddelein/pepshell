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

package com.compomics.pepshell.controllers.dataimport;

import com.compomics.pepshell.controllers.dataimport.filehandlers.FileParserFactory;
import com.compomics.pepshell.model.*;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshellstandalone.drawmodes.Peptides.QuantedPeptideDrawMode;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Iain on 17/02/2015.
 */
public class QuantedPeptideDrawModeTest {
    public String testFile;

    /**
     * Prepare the braf experiment
     * @return The braf experiment
     */
    public FileBasedExperiment setupExperiment() throws CouldNotParseException {
        testFile = this.getClass().getClassLoader().getResource("braf/reference.txt").getFile();

        SeparatedValueExperimentMetadata metadata = new SeparatedValueExperimentMetadata();
        metadata.setHasHeaders(true)
            .setPeptidesequenceColumn(1)
            .setProteinAccessionColumn(2)
            .setPeptideStartColumn(3)
            .setPeptideEndColumn(4)
            .setIntensityColumn(5)
            .setRatioColumn(6)
            .setValueSeparator("\t");

        AnnotatedFile aFile = new AnnotatedFile(testFile);
        aFile.addAnnotationsToFile(metadata);

        FileBasedExperiment experiment = new FileBasedExperiment("braf");
        experiment.setFile(aFile);

        FileParserFactory.getInstance().parseExperimentFile(experiment);

        return experiment;
    }

    @Test
    public void testCalculatePeptideGradient() throws Exception {
        FileBasedExperiment experiment = setupExperiment();
        QuantedPeptideDrawMode drawMode = new QuantedPeptideDrawMode();

        drawMode.setMaxRatio(experiment.getMaxRatio());
        drawMode.setMinRatio(experiment.getMinRatio());

        Color color;

        for (PepshellProtein protein : experiment.getProteins()) {
            for (PeptideGroup peptideGroup : protein.getPeptideGroups()) {
                for (PeptideInterface peptide : peptideGroup.getPeptideList()) {
                    color = drawMode.calculatePeptideGradient((QuantedPeptide) peptide);
                }
            }
        }
    }
}
