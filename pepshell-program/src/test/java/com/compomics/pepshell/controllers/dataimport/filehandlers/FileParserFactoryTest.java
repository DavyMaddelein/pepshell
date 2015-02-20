package com.compomics.pepshell.controllers.dataimport.filehandlers;

import com.compomics.pepshell.ExperimentTestBase;
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
public class FileParserFactoryTest extends ExperimentTestBase {

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

        LineNumberReader reader = new LineNumberReader(new FileReader("reference.txt"));
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

        LineNumberReader reader = new LineNumberReader(new FileReader("reference.txt"));
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

        LineNumberReader reader = new LineNumberReader(new FileReader("reference.txt"));
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