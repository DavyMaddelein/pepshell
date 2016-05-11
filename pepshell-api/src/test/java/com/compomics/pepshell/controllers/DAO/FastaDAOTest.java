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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
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
    private static File testFasta2;

    public FastaDAOTest() {

    }


    @BeforeClass
    public static void setUpClass() throws IOException {
        testDir = Files.createTempDirectory("pepshellfastadaotestfiles").toFile();
        testFasta = new File(ClassLoader.getSystemClassLoader().getResource("testfasta.fasta").getPath());
        testFasta2 = new File(ClassLoader.getSystemClassLoader().getResource("testfasta2.fasta").getPath());
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
        Experiment experimentToAddProteinsTo = new Experiment("test experiment");
        FastaDAO.setExperimentProteinsToFastaFileProteins(testFasta, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(1));
        assertThat(experimentToAddProteinsTo.getProteins().get(0).getVisibleAccession(), is("gi|00000021"));
        FastaDAO.setExperimentProteinsToFastaFileProteins(testFasta2, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(3));
        assertThat(experimentToAddProteinsTo.getProteins().get(0).getVisibleAccession(), is("gi|00000021"));
        assertThat(experimentToAddProteinsTo.getProteins().get(1).getVisibleAccession(), is("gi|00000022"));

    }

    @Test
    public void testFailSetExperimentProteinsToFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToFastaFileProteinsEmptyFile");
        File fastaFile = Files.createTempFile(testDir.toPath(), "emptyfile", "empty").toFile();
        Experiment experimentToAddProteinsTo = new Experiment("test experiment");
        //TODO add faultbarrier listener here to check if correct exception is thrown
        FastaDAO.setExperimentProteinsToFastaFileProteins(fastaFile, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(0));
    }

    @Test(expected = IOException.class)
    public void testSetExperimentProteinsToNonExistentFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToFastaFileProteins");
        File fastaFile = new File("doesn't exist");
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
        File fastaFile = testFasta;
        List<PepshellProtein> proteinsToAddSequencesTo = new ArrayList<>();
        proteinsToAddSequencesTo.add(new PepshellProtein("gi|00000021"));
        FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, proteinsToAddSequencesTo);
        assertThat(proteinsToAddSequencesTo.get(0).getProteinSequence(), is(equalTo("MSPILGYWKIKGLVQPTRLLLEYLEEKYEEHLYERDEGDKWRNKKFELGLEFPNLPYYIDGDVKLTQSMAIIRYIADKHNMLGGCPKERAEISMLEGAVLDIRYGVSRIAYSKDFETLKVDFLSKLPEMLKMFEDRLCHKTYLNGDHVTHPDFMLYDALDVVLYMDPMCLDAFPKLVCFKKRIEAIPQIDKYLKSSKYIAWPLQGWQATFGGGDHPPKSDLEVLFQGPLGSPNSRVDAALSGGGGGGAEPGQALFNGDMEPEAGAGAGAAASSAADPAIPEEVWNIKQMIKLTQEHIEALLDKFGGEHNPPSIYLEAYEEYTSKLDALQQREQQLLESLGNGTDFSVSSSASMDTVTSSSSSSLSVLPSSLSVFQNPTDVARSNPKSPQKPIVRVFLPNKQRTVVPARCGVTVRDSLKKALMMRGLIPECCAVYRIQDGEKKPIGWDTDISWLTGEELHVEVLENVPLTTHNFVRKTFFTLAFCDFCRKLLFQGFRCQTCGYKFHQRCSTEVPLMCVNYDQLDLLFVSKFFEHHPIPQEEASLAETALTSGSSPSAPASDSIGPQILTSPSPSKSIPIPQPFRPADEDHRNQFGQRDRSSSAPNVHINTIEPVNIDDLIRDQGFRGDGGSTTGLSATPPASLPGSLTNVKALQKSPGPQRERKSSSSSEDRNRMKTLGRRDSSDDWEIPDGQITVGQRIGSGSFGTVYKGKWHGDVAVKMLNVTAPTPQQLQAFKNEVGVLRKTRHVNILLFMGYSTKPQLAIVTQWCEGSSLYHHLHIIETKFEMIKLIDIARQTAQGMDYLHAKSIIHRDLKSNNIFLHEDLTVKIGDFGLATVKSRWSGSHQFEQLSGSILWMAPEVIRMQDKNPYSFQSDVYAFGIVLYELMTGQLPYSNINNRDQIIFMVGRGYLSPDLSKVRSNCPKAMKRLMAECLKKKRDERPLFPQILASIELLARSLPKIHRSASEPSLNRAGFQTEDFSLYACASPKTPIQAGGYGAFPVH")));
    }

    /**
     * Test of setProjectProteinsToMultipleFastaFileProteins method, of class
     * FastaDAO.
     */
    @Test
    public void testSetExperimentProteinsToMultipleFastaFileProteins() throws Exception {
        System.out.println("setProjectProteinsToMultipleFastaFileProteins");
        List<File> fastaFiles = new ArrayList<>();
        fastaFiles.add(testFasta);
        fastaFiles.add(testFasta2);
        Experiment experimentToAddProteinsTo = new Experiment("test experiment");
        FastaDAO.setExperimentProteinsToMultipleFastaFileProteins(fastaFiles, experimentToAddProteinsTo);
        assertThat(experimentToAddProteinsTo.getProteins().size(), is(3));
        assertThat(experimentToAddProteinsTo.getProteins().get(0).getVisibleAccession(), is("gi|00000021"));
        assertThat(experimentToAddProteinsTo.getProteins().get(1).getVisibleAccession(), is("gi|00000022"));
    }
}
