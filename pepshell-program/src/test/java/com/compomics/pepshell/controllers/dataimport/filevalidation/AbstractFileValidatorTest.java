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
import com.compomics.pepshell.model.SeparatedValueExperimentMetadata;
import com.compomics.pepshell.model.enums.DataSourceEnum;
import com.compomics.pepshell.model.exceptions.CannotValidateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Davy Maddelein
 */
public class AbstractFileValidatorTest {

    private final File tinytestFile;
    private final File tinyMultipleTestFile;
    private static File emptyFile;

    public AbstractFileValidatorTest() {
        tinytestFile = new File(ClassLoader.getSystemClassLoader().getResource("tinytestfile.tsv").getPath());
        tinyMultipleTestFile = new File(ClassLoader.getSystemClassLoader().getResource("tinymultipleexperimenttestfile.tsv").getPath());
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        Path testDir = Files.createTempDirectory("pepshelltestfiles");
        if (!testDir.toFile().exists()) {
            Files.createDirectory(testDir);
        }
        emptyFile = Files.createTempFile(testDir,"emptyfile","txt").toFile();
    }

    //<editor-fold desc="single experiment tests">

    /**
     * Test of validateFile method, of class AbstractFileValidator.
     */
    @Test
    public void testValidateFile_File() throws Exception {
        System.out.println("validateGenericFile");
        File aFile = tinytestFile;
        AbstractFileValidator instance = new AbstractFileValidator();
        boolean expResult = false;
        boolean result = instance.validateFile(aFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of validateFile method, of class AbstractFileValidator.
     */
    @Test
    public void testValidateFileWithExperimentMetaData() throws Exception {
        System.out.println("validateGenericFileWithMetadata");
        AnnotatedFile aFile = new AnnotatedFile(tinytestFile.getAbsolutePath());
        SeparatedValueExperimentMetadata metaData = new SeparatedValueExperimentMetadata(DataSourceEnum.FILE);
        metaData.setHasHeaders(true).
                setProteinAccessionColumn(1).
                setPeptidesequenceColumn(2).
                setRatioColumn(5).
                setLightIntensityColumn(3).
                setHeavyIntensityColumn(4).setValueSeparator("\t");
        AbstractFileValidator instance = new AbstractFileValidator();
        boolean expResult = true;
        boolean result = instance.validateFile(aFile.addAnnotationsToFile(metaData));
        assertEquals(expResult, result);
    }

    //</editor-fold>

    //<editor-fold desc="edge cases">

    @Test(expected = CannotValidateException.class)
    public void testValidateFileWithFakeFile() throws CannotValidateException {
        AnnotatedFile aFile = null;
        AbstractFileValidator instance = new AbstractFileValidator();
        boolean result = instance.validateFile(aFile);
        boolean expResult = false;
        assertThat(result, is(expResult));
        instance.validateFile(aFile.addAnnotationsToFile(new SeparatedValueExperimentMetadata(DataSourceEnum.FILE)));
    }

    @Test(expected = CannotValidateException.class)
    public void testValidateFileWithInvalidMetaData() throws CannotValidateException {
        AnnotatedFile aFile = new AnnotatedFile(tinytestFile.getAbsolutePath());
        AbstractFileValidator instance = new AbstractFileValidator();
        boolean expResult = instance.validateFile(aFile.addAnnotationsToFile(new SeparatedValueExperimentMetadata(DataSourceEnum.FILE)) );
        //should never get here
        assertThat(expResult, is(false));
    }

    @Test(expected = CannotValidateException.class)
    public void testValidateFileWithConflictingMetaData() throws CannotValidateException {
        AnnotatedFile aFile = new AnnotatedFile(tinytestFile.getAbsolutePath());
        AbstractFileValidator instance = new AbstractFileValidator();
        SeparatedValueExperimentMetadata metaData = new SeparatedValueExperimentMetadata(DataSourceEnum.FILE);
        metaData.setHasHeaders(true);
        metaData.setHeavyIntensityColumn(4);
        metaData.setLightIntensityColumn(3);
        metaData.setIntensityColumn(4);
        boolean expResult = instance.validateFile(aFile);
        //should never get here
        assertThat(expResult, is(true));
    }

    @Test(expected = CannotValidateException.class)
    public void testValidateFileWithNullMetaData() throws CannotValidateException {
        AnnotatedFile aFile = new AnnotatedFile(tinytestFile.getAbsolutePath());
        AbstractFileValidator instance = new AbstractFileValidator();
        boolean expResult = instance.validateFile(aFile);
        //should never get here
        assertThat(expResult, is(true));
    }
    //</editor-fold>

    //<editor-fold desc="multiple experiments"

    /**
     * Test of validateFile method, of class AbstractFileValidator.
     */
    @Test
    public void testValidateMultipleExperimentFileWithExperimentMetaData() throws Exception {
        System.out.println("validateGenericFileWithMetadata");
        AnnotatedFile aFile = new AnnotatedFile(tinyMultipleTestFile.getAbsolutePath());
        SeparatedValueExperimentMetadata metaData = new SeparatedValueExperimentMetadata(DataSourceEnum.FILE);
        metaData.setHasHeaders(true).
                setProteinAccessionColumn(1).
                setPeptidesequenceColumn(2).
                setRatioColumn(5).
                setLightIntensityColumn(3).
                setHeavyIntensityColumn(4).setValueSeparator("\t").
                setFirstExperimentColumn(2).setLastExperimentColumn(13);
        AbstractFileValidator instance = new AbstractFileValidator();
        boolean expResult = true;
        boolean result = instance.validateFile(aFile.addAnnotationsToFile(metaData));
        assertEquals(expResult, result);
    }

    //</editor-fold>

    @Test(expected = com.compomics.pepshell.model.exceptions.CannotValidateException.class)
    public void testValidateMultipleExperimentFileWithFalseMetaData() throws Exception {
        AnnotatedFile aFile = new AnnotatedFile(emptyFile.getAbsolutePath());
        aFile.addAnnotationsToFile(new SeparatedValueExperimentMetadata(DataSourceEnum.FILE));
        aFile.getAnnotations().setHasHeaders(true).setValueSeparator("\t");
        AbstractFileValidator instance = new AbstractFileValidator();
        instance.validateFile(aFile);

    }

}
