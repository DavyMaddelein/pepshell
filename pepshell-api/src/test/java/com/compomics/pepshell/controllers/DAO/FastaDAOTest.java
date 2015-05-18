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

package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.DAO.DAUtils.FileUtils;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Davy Maddelein
 */
public class FastaDAOTest {

    private static File testDir;
    private static File testFasta;

    public FastaDAOTest() {
    }

    /**
     * this wil set the testing fasta file in all the tests where it makes sense
     * to have a sane fasta
     *
     * @param aTestFasta the testing file to run the tests on
     */
    public void setTestingFasta(File aTestFasta) {
        testFasta = aTestFasta;
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        testDir = Files.createTempDirectory("pepshellfastadaotestfiles").toFile();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        FileUtils.recursivelyDeleteFilesFromDir(testDir.toPath());
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setProjectProteinsToFastaFileProteins method, of class FastaDAO.
     * this tests all of the internal methods
     */
    @Test
    public void testSetExperimentProteinsToFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToFastaFileProteins");
        File fastaFile = null;
        Experiment experimentToAddProteinsTo = new Experiment("test experiment");
        FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(2));
    }

    @Test(expected = FastaCouldNotBeReadException.class)
    public void testFailSetExperimentProteinsToFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToFastaFileProteins");
        File fastaFile = Files.createTempFile(testDir.toPath(), "emptyfile", "empty").toFile();
        Experiment experimentToAddProteinsTo = new Experiment("test experiment");
        FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(0));
    }

    @Test(expected = IOException.class)
    public void testSetExperimentProteinsToNonExistentFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToFastaFileProteins");
        File fastaFile = null;
        Experiment experimentToAddProteinsTo = new Experiment("test experiment");
        FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(0));
    }

    /**
     * Test of mapFastaSequencesToProteinAccessions method, of class FastaDAO.
     */
    @Test
    public void testMapFastaSequencesToProteinAccessions() throws Exception {
        System.out.println("mapFastaSequencesToProteinAccessions");
        File fastaFile = null;
        List experimentToAddSequencesTo = null;
        FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, experimentToAddSequencesTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProjectProteinsToMultipleFastaFileProteins method, of class
     * FastaDAO.
     */
    @Test
    public void testSetExperimentProteinsToMultipleFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToMultipleFastaFileProteins");
        List<File> fastaFiles = null;
        Experiment experimentToAddProteinsTo = null;
        FastaDAO.setExperimentProteinsToMultipleFastaFileProteins(fastaFiles, experimentToAddProteinsTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
