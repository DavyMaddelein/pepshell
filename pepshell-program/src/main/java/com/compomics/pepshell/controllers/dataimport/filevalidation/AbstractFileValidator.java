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
import com.compomics.pepshell.model.SeparatedvalueExperimentMetaData;
import com.compomics.pepshell.model.exceptions.CannotValidateException;
import com.compomics.pepshell.model.exceptions.ValidationException;

import java.io.EOFException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
/**
 * a validator for the generic file option. This class serves as the template to
 * base on when building more specialized validators.
 * <p/>
 * <p/>
 * It has three methods for validation, a validate file without metadata, which
 * will always return false as any context is lacking, so we can't make any
 * assumptions about the file.
 * <p/>
 * The second one receives metadata which helps the validator to check if the
 * file is handle-able by the accompanying parser. The impetus to check the
 * legality of the file rests solely on the validator, this also implies if the
 * file even exists or not.
 * <p/>
 * The third one is a convenience method where the file and the annotations are
 * aggregated in a single object
 * <p/>
 * This class will assume that the files it receives are unzipped, readable text
 * files, if this is not the case, the validator will return false. If the
 * metadata does not correspond to the layout of the file, a false is returned.
 *
 * @author Davy Maddelein
 */
public class AbstractFileValidator implements FileValidatorInterface {

    private Pattern AminoAcidMatcherPattern = Pattern.compile("[XZBDHJUW]");

    /**
     * validate the file assuming it is a vanilla version of the layout of the
     * parser
     *
     * @param aFile The file to validate
     * @return always false as we cannot make any assumptions here
     */
    @Override
    public boolean validateFile(File aFile) throws CannotValidateException {
        return false;
    }

    /**
     * validate the file aided by extra annotations about the file
     *
     * @param annotatedFile The file to validate
     * @return true if validated, false otherwise
     */
    @Override
    public boolean validateFile(AnnotatedFile annotatedFile) throws CannotValidateException {
        //some pre checks to make sure there are no nasty surprises
        boolean validated = true;

        if (annotatedFile == null || !annotatedFile.exists()) {
            throw new CannotValidateException("the file does not appear to exist");
        }

        if (annotatedFile.getAnnotations() == null) {
            throw new CannotValidateException("there was a problem getting the metadata, please contact the developer");
        }

        if (annotatedFile.getAnnotations().getValueSeparator() == null || annotatedFile.getAnnotations().getValueSeparator().isEmpty()) {
            throw new CannotValidateException("no value separator was added");
        }

        try {
            LineNumberReader lineReader = new LineNumberReader(new FileReader(annotatedFile));
            String line;
            if (annotatedFile.getAnnotations().fileHasHeaders()) {
                //we don't need headers
                line = lineReader.readLine();
            }
            try {
                line = lineReader.readLine();
                if (line == null) {
                    throw new CannotValidateException("it appears that the file is empty");
                }
            } catch (EOFException eof) {
                throw new CannotValidateException("reached end of file when expecting data");
            }
            String[] splitValues = line.split(annotatedFile.getAnnotations().getValueSeparator());
            int allExperimentalColumns = splitValues.length - annotatedFile.getAnnotations().getFirstExperimentColumn() - annotatedFile.getAnnotations().getLastExperimentColumn();
            if (annotatedFile.getAnnotations().hasMultipleExperimentsPerFile()) {
                try {
                    if (allExperimentalColumns < 0 || allExperimentalColumns % annotatedFile.getAnnotations().getColumnsPerExperiment() != 0) {
                        throw new CannotValidateException("the number of columns annotated as experiments, do not match the number of total experiment columns found\nline was " + lineReader.getLineNumber());
                    }
                } catch (ArithmeticException e) {
                    throw new CannotValidateException("file was annotated as multiple experiment but did not have the number of columns annotated per experiment");
                }
            }
            //from here on we can start validating if the columns contain the correct type of data
            while (line != null) {
                if (annotatedFile.getAnnotations().hasMultipleExperimentsPerFile()) {
                    for (int i = annotatedFile.getAnnotations().getFirstExperimentColumn(); i < annotatedFile.getAnnotations().getLastExperimentColumn(); i += annotatedFile.getAnnotations().getColumnsPerExperiment()) {

                        validated = validateExperiment(Arrays.copyOfRange(splitValues, i, i + annotatedFile.getAnnotations().getColumnsPerExperiment()), annotatedFile.getAnnotations());
                        if (!validated) {
                            break;
                        }
                    }
                } else {
                    validated = validateExperiment(splitValues, annotatedFile.getAnnotations());
                }

                if (!validated) {
                    break;
                }
                line = lineReader.readLine();
            }

        } catch (IOException e) {
            throw new CannotValidateException("the file could not be read");
        } catch (Exception e) {
            CannotValidateException exception = new CannotValidateException("validation failed");
            exception.addSuppressed(e);
            throw exception;
        }
        return validated;
    }

    @Override
    public boolean canValidateFile(File aFile) {
        return aFile instanceof AnnotatedFile && ((AnnotatedFile) aFile).getAnnotations() instanceof SeparatedvalueExperimentMetaData;
    }

    /**
     * Validates the separated experiment columns with the added file metadata
     *
     * note that the validation happens on a 0 base array while the column
     * number is expected to be given as 1 based input
     *
     * @param experimentColumns the columns to validate
     * @param metaData the metadata to use while validating
     * @return true if validated, false otherwise if silently failed validation
     * @throws ValidationException if the validation failure needs more
     * explanation
     */
    private boolean validateExperiment(String[] experimentColumns, SeparatedvalueExperimentMetaData metaData) throws ValidationException {
        boolean validated = true;
        if (experimentColumns[metaData.getProteinAccessionColumn() - 1].length() == 0) {
            validated = false;
        }
        if (validated && experimentColumns[metaData.getPeptideSequence() - 1].length() == 0) {
            validated = false;
        } else {
        }
        if (validated && metaData.experimentHasIntensityValues()) {

            //intensity checks
            if (metaData.splitPeptidesOnIntensity()) {
                try {
                    if (experimentColumns[metaData.getLightIntensityColumn() - 1].length() == 0) {
                        validated = false;
                    } else {
                        new Double(experimentColumns[metaData.getLightIntensityColumn() - 1]);
                    }
                    if (validated && experimentColumns[metaData.getHeavyIntensityColumn() - 1].length() == 0) {
                        validated = false;
                    } else {
                        new Double(experimentColumns[metaData.getHeavyIntensityColumn() - 1]);
                    }
                } catch (NumberFormatException nfe) {
                    throw new ValidationException("intensity value at column " + metaData.getIntensityColumn() + " was not a number");
                }
                // single intensity value check
            } else {
                try {
                    if (experimentColumns[metaData.getIntensityColumn() - 1].length() == 0) {
                        validated = false;
                    } else {
                        new Double(experimentColumns[metaData.getIntensityColumn() - 1]);
                    }
                } catch (NumberFormatException nfe) {
                    throw new ValidationException("intensity value at column " + metaData.getIntensityColumn() + " was not a number");
                }
            }
            if (validated && metaData.experimentHasRatio()) {
                try {
                    if (experimentColumns[metaData.getRatioColumn() - 1].length() == 0) {
                        validated = false;
                    } else {
                        new Double(experimentColumns[metaData.getRatioColumn() - 1].length());
                    }
                } catch (NumberFormatException nfe) {
                    throw new ValidationException("ratio value at column" + metaData.getRatioColumn() + " was not a number");

                }

            }
        }
        return validated;
    }

    @Override
    public boolean canValidateFile(AnnotatedFile anAnnotatedFile) {
        return true;
    }
}
