package com.compomics.pepshell.view.dataviewing;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.dataimport.filehandlers.FileParserFactory;
import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.FileBasedExperiment;
import com.compomics.pepshell.model.SeparatedValueExperimentMetadata;
import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Iain on 18/02/2015.
 */
public class ExperimentPanelTest {
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
    public void testJCombobox1QuantedMode() throws CouldNotParseException, UndrawableException {
        FileBasedExperiment experiment = setupExperiment();

        QuantedPeptideDrawMode proteinDrawMode = new QuantedPeptideDrawMode();
        proteinDrawMode.setMaxRatio(experiment.getMaxRatio());
        proteinDrawMode.setMinRatio(experiment.getMinRatio());

        int width = 800;
        int height = 600;

        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel(new GridLayout());
        jFrame.setSize(width, height);
        panel.setSize(width,height);
        jFrame.add(panel);
        jFrame.setVisible(true);

        Graphics graphics = panel.getGraphics();

        proteinDrawMode.drawProteinAndPeptides(experiment.getProteins().get(0), graphics, new Point(0, 0), width, height);

        while(true);


    }
}
