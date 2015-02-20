package com.compomics.pepshell.controllers.dataimport.filehandlers;

import com.compomics.pepshell.model.*;
import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by Iain on 16/02/2015.
 */
public class FileParserFactoryTest {

    public String testFile;

    /**
     * Prepare the braf experiment
     *
     * @return The braf experiment
     */
    public FileBasedExperiment setupExperiment(String path) {
        testFile = this.getClass().getClassLoader().getResource("braf/" + path).getFile();

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

        return experiment;
    }

    /**
     * Testing the path of gold
     *
     * @throws CouldNotParseException
     */
    @Test
    public void testParseExperimentFileWithGenericFile() throws CouldNotParseException {
        FileParserFactory.getInstance().parseExperimentFile(setupExperiment("reference.txt"));
    }

    /**
     * Test that less proteins are returned than sequences in the file
     *
     * @throws CouldNotParseException
     * @throws IOException
     */
    @Test
    public void testParseExperimentReturnsLessProteinsThanSequences() throws CouldNotParseException, IOException {
        FileBasedExperiment experiment = setupExperiment("reference.txt");

        FileParserFactory.getInstance().parseExperimentFile(experiment);

        LineNumberReader reader = new LineNumberReader(new FileReader(testFile));
        reader.skip(Long.MAX_VALUE);

        assertThat(experiment.getProteins().size(), lessThan(reader.getLineNumber() - 1));
    }

    /**
     * Test that all sequences are accounted for amongst the proteins
     *
     * @throws CouldNotParseException
     * @throws IOException
     */
    @Test
    public void testParseExperimentIncludesAllPeptides() throws CouldNotParseException, IOException {
        FileBasedExperiment experiment = setupExperiment("reference.txt");

        FileParserFactory.getInstance().parseExperimentFile(experiment);

        LineNumberReader reader = new LineNumberReader(new FileReader(testFile));
        reader.skip(Long.MAX_VALUE);

        assertEquals(countPeptides(experiment), reader.getLineNumber() - 1);
    }

    /**
     * Test that a file with an empty sequence field is parsed correctly
     *
     * @throws CouldNotParseException
     * @throws IOException
     */
    @Test
    public void testParseExperimentMissingSequence() throws CouldNotParseException, IOException {
        FileBasedExperiment experiment = setupExperiment("reference_missing_seq.txt");

        FileParserFactory.getInstance().parseExperimentFile(experiment);

        LineNumberReader reader = new LineNumberReader(new FileReader(testFile));
        reader.skip(Long.MAX_VALUE);

        assertEquals(countPeptides(experiment), reader.getLineNumber() - 2);
    }

    private int countPeptides(FileBasedExperiment experiment) {
        int peptideCount = 0;

        for (Protein protein : experiment.getProteins()) {
            for (PeptideGroup peptideGroup : protein.getPeptideGroups()) {
                for (PeptideInterface peptide : peptideGroup.getPeptideList()) {
                    peptideCount += peptide.getTimesFound();
                }
            }
        }

        return peptideCount;
    }

    @Test
    public void testPassedNonsensicalIntensityValues() throws CouldNotParseException {
        FileBasedExperiment experiment = setupExperiment("exp6.txt");

        FileParserFactory.getInstance().parseExperimentFile(experiment);

        for (Protein aProtein : experiment.getProteins()) {
            for (PeptideGroup peptideGroup : aProtein.getPeptideGroups()) {
                for (PeptideInterface peptide : peptideGroup.getPeptideList()) {
                    int colorValue = (int) Math.floor(255 * ((peptide.getTotalSpectrumIntensities().stream().reduce(0.0, Double::sum)) / peptide.getTotalSpectrumIntensities().size() / experiment.getMaxIntensity()));
                    System.out.println(colorValue);
                    assertTrue(colorValue <= 255 && colorValue >= 0);
                }
            }
        }
    }
}