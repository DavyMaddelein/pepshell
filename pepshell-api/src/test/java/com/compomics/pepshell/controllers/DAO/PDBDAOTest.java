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

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;

import java.io.File;
import java.util.Set;
import static org.hamcrest.CoreMatchers.*;
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
public class PDBDAOTest {

    public PDBDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class PDBDAO.
     */
    public void testGetInstance() throws InstantiationException, IllegalAccessException {
        System.out.println("getInstance");
        PDBDAO expResult = PDBDAO.getInstance();
        PDBDAO result = PDBDAO.getInstance();
        assertEquals(expResult, result);
    }

    @Test(expected = InstantiationException.class)
    public void testIllegalGetInstance() throws InstantiationException, IllegalAccessException {
        System.out.println("getReflectiveInstance");
        PDBDAO pdbdao = PDBDAO.class.newInstance();
    }

    /**
     * Test of getPDBInfoForProtein method, of class PDBDAO.
     */
    @Test
    public void testGetPDBInfoForProtein() throws Exception {
        System.out.println("getPDBInfoForProtein");
        PepshellProtein pepshellProtein = new PepshellProtein("P04637");
        PDBDAO instance = PDBDAO.getInstance();
        Set<PdbInfo> result = instance.getPDBInfoForProtein(pepshellProtein);
        assertThat(143, is(equalTo((result.size()))));
    }

    @Test
    public void testGetFakePDBInfoForProtein() throws Exception {
        System.out.println("getPDBInfoForProtein");
        PepshellProtein pepshellProtein = new PepshellProtein("not a pepshellProtein accession");
        PDBDAO instance = PDBDAO.getInstance();
        Set<PdbInfo> result = instance.getPDBInfoForProtein(pepshellProtein);
        assertThat(result.isEmpty(), is(true));
    }

    /**
     * Test of getPdbFile method, of class PDBDAO.
     */
    @Test
    public void testGetPdbFile() throws Exception {
        System.out.println("getPdbFile");
        String aPdbAccession = "5aba";
        File expResult = null;
        File result = PDBDAO.getPdbFile(aPdbAccession);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPdbFileInMem method, of class PDBDAO.
     */
    @Test
    public void testGetPdbFileInMem() throws Exception {
        System.out.println("getPdbFileInMem");
        String aPdbAccession = "5aba";
        String expResult = "";
        String result = PDBDAO.getPdbFileInMem(aPdbAccession);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPdbHeaderForPdbAccession method, of class PDBDAO.
     */
    @Test
    public void testGetPdbInfoForPdbAccession() throws Exception {
        System.out.println("getPdbHeaderForPdbAccession");
        String pdbAccession = "";
        PdbInfo expResult = null;
        PdbInfo result = PDBDAO.getPdbHeaderForPdbAccession(pdbAccession);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSequenceFromPdbFile method, of class PDBDAO.
     */
    @Test
    public void testGetSequenceFromPdbFile() {
        System.out.println("getSequenceFromPdbFile");
        String pdbFileInMem = "";
        String expResult = "";
        String result = PDBDAO.getSequenceFromPdbFile(pdbFileInMem);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
