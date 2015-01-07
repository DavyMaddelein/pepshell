/*
 * Copyright 2014 svend.
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

import com.compomics.pepshell.controllers.dataimport.filevalidation.FileValidatorFactory;
import com.compomics.pepshell.model.AnnotatedFile;


import com.compomics.pepshell.model.exceptions.CannotValidateException;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author svend
 */
public class FileValidatorFactoryTest {
    
    public FileValidatorFactoryTest() {
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
     * Test of getInstance method, of class FileValidatorFactory.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        FileValidatorFactory expResult = null;
        FileValidatorFactory result = FileValidatorFactory.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFileToValidate method, of class FileValidatorFactory.
     */
    @Test
    public void testAddFileToValidate() throws Exception{
        System.out.println("addFileToValidate");
        AnnotatedFile aFile = null;
        FileValidatorFactory instance = FileValidatorFactory.getInstance();
        instance.validate(aFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMetaDataForValidation method, of class FileValidatorFactory.
     */
    @Test
    public void testAddMetaDataForValidation() throws CannotValidateException {
        System.out.println("addMetaDataForValidation");
        AnnotatedFile aFile = null;
        FileValidatorFactory instance = FileValidatorFactory.getInstance();
        instance.validate(aFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validate method, of class FileValidatorFactory.
     */
    @Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        FileValidatorFactory instance = null;
        boolean expResult = false;
        boolean result = instance.validate(new File(""));
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
