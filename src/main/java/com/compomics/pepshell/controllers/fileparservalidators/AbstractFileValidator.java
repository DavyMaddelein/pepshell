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

package com.compomics.pepshell.controllers.fileparservalidators;

import com.compomics.pepshell.model.SeparatedvalueExperimentMetaData;
import com.compomics.pepshell.model.exceptions.CannotValidateException;

import java.io.EOFException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */

/**
 * a validator for the generic file option. This class serves as the template to
 * base on when building more specialized validators.
 * <p/>
 * <p/>
 * It has two methods, a validate file without metadata, which will always
 * return false as any context is lacking, so we can't make any assumptions
 * about the file.
 * <p/>
 * the second one receives metadata which helps the validator to check if the
 * file is handleable by the accompanying parser. The impetus to check the
 * legality of the file rests solely on the validator, this also implies if the
 * file even exists or not.
 * <p/>
 * This class will assume that the files it receives are unzipped, readable text
 * files, if this is not the case, the validator will return false. If the
 * metadata does not correspond to the layout of the file, a false is returned.
 *
 * @author Davy Maddelein
 */
public class AbstractFileValidator implements FileValidatorInterface {

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
     * @param fileToValidate The file to validate
     * @param metaData       The extra data needed to correctly interpret the file for
     *                       validation
     * @return true if validated, false otherwise
     */
    @Override
    public boolean validateFile(File fileToValidate, SeparatedvalueExperimentMetaData metaData) throws CannotValidateException {
        boolean validated = false;
        //some pre checks to make sure there are no nasty surprises

        if (fileToValidate == null || !fileToValidate.exists()) {
            throw new CannotValidateException("the file does not appear to exist");
        }

        if (metaData == null) {
            throw new CannotValidateException("there was a problem getting the metadata, please contact the developer");
        }

        if (metaData.getValueSeparator() == null || metaData.getValueSeparator().isEmpty()) {
            throw new CannotValidateException("no value separator was added");
        }

        try {
            LineNumberReader lineReader = new LineNumberReader(new FileReader(fileToValidate));
            String line = "";
            if (metaData.fileHasHeaders()) {
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
            String[] splitValues = line.split(metaData.getValueSeparator());
            int allExperimentalColumns = splitValues.length - metaData.getFirstExperimentColumn() - metaData.getLastExperimentColumn();
            if (metaData.hasMultipleExperimentsPerFile()) {
                if (allExperimentalColumns < 0 || allExperimentalColumns % metaData.getColumnsPerExperiment() != 0) {
                    throw new CannotValidateException("the number of columns annotated as experiments, do not match the number of total experiment columns found\nline was " + lineReader.getLineNumber());
                }
            }
            //from here on we can start validating if the columns contain the correct type of data
            while (line != null) {

                if (!validated) {
                    break;
                }
                line = lineReader.readLine();
            }
        } catch (IOException e) {
            throw new CannotValidateException("the file could not be read");
        }
        return validated;
    }
}
