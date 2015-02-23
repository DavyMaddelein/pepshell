package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.controllers.dataimport.filehandlers.FileParserFactory;
import com.compomics.pepshell.model.*;
import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

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

        SeparatedValueExperimentMetadata metadata = new SeparatedValueExperimentMetadata(DataSourceEnum.FILE);
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
