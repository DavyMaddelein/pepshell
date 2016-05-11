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

package com.compomics.pepshell.controllers;

import java.util.ArrayList;
import java.util.List;
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
public class AccessionConverterTest {

    public AccessionConverterTest() {
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
     * Test of spToUniProt method, of class AccessionConverter.
     */
    @Test
    public void testSpToUniProt() throws Exception {
        System.out.println("spToUniProt");
        String aSpAccession = "";
        List<String> expResult = new ArrayList<>();
        List<String> result = AccessionConverter.spToUniProt(aSpAccession);
        assertEquals(expResult, result);
    }

    /**
     * Test of refSeqToSp method, of class AccessionConverter.
     */
    @Test
    public void testRefSeqToSp() throws Exception {
        System.out.println("refSeqToSp");
        String aNcbiAccession = "NP_001119584.1.";
        List<String> expResult = null;
        List<String> result = AccessionConverter.refSeqToSp(aNcbiAccession);
        assertEquals(expResult, result);
    }

    /**
     * Test of spToRefSeq method, of class AccessionConverter.
     */
    @Test
    public void testSpToRefSeq() throws Exception {
        System.out.println("spToRefSeq");
        String aSpAccession = "Q9UDR5";
        List<String> expResult = new ArrayList<>();
        expResult.add("NM_005763");
        List<String> result = AccessionConverter.spToRefSeq(aSpAccession);
        assertEquals(expResult, result);
    }

    /**
     * Test of uniProtToSp method, of class AccessionConverter.
     */
    @Test
    public void testUniProtToSp() {
        System.out.println("uniProtToSp");
        String aUniprotAccession = "P04637";
        List<String> expResult = new ArrayList<>();
        List<String> result = AccessionConverter.uniProtToSp(aUniprotAccession);
        assertEquals(expResult, result);
    }

    /**
     * Test of uniProtToRefSeq method, of class AccessionConverter.
     */
    @Test
    public void testUniProtToRefSeq() {
        System.out.println("uniProtToRefSeq");
        String aUniprotAccession = "P04637";
        List<String> expResult = new ArrayList<>();
        List<String> result = AccessionConverter.uniProtToRefSeq(aUniprotAccession);
        assertEquals(expResult, result);
    }

    /**
     * Test of GIToUniprot method, of class AccessionConverter.
     */
    @Test
    public void testGIToUniprot() throws Exception {
        System.out.println("GIToUniprot");
        String GINumber = "114326272";
        String expResult = "";
        String result = AccessionConverter.GIToUniprot(GINumber);
        assertEquals(expResult, result);
    }

    /**
     * Test of toUniprot method, of class AccessionConverter.
     */
    @Test
    public void testToUniprot() throws Exception {
        System.out.println("toUniprot");
        String accession = "";
        String expResult = "P04637";
        String result = AccessionConverter.toUniprot(accession);
        assertEquals(expResult, result);
        accession = "";
        result = AccessionConverter.toUniprot(accession);
        assertEquals(expResult, result);
        accession = "";
        result = AccessionConverter.toUniprot(accession);
        assertEquals(expResult, result);
    }

}
