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
package com.compomics.pepshell.controllers.dataimport.filevalidation;

import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.HDXMetaDataAnnotations;
import java.io.File;

import com.compomics.pepshell.model.enums.DataSourceEnum;
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
public class HDXFileValidatorTest {
    
    public HDXFileValidatorTest() {
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
     * Test of validateFile method, of class HDXFileValidator.
     */
    @Test
    public void testValidateFile_File() {
        System.out.println("validateFile");
        File aFile = null;
        HDXFileValidator instance = new HDXFileValidator();
        boolean expResult = false;
        boolean result = instance.validateFile(aFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateFile method, of class HDXFileValidator.
     */
//    @Test
//    public void testValidateFile_File_ExperimentMetaData() {
//        System.out.println("validateFile");
//        AnnotatedFile aFile = null;
//        aFile.addAnnotationsToFile(new HDXMetaDataAnnotations(DataSourceEnum.FILE));
//        HDXFileValidator instance = new HDXFileValidator();
//        boolean expResult = false;
//        boolean result = instance.validateFile(aFile);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
