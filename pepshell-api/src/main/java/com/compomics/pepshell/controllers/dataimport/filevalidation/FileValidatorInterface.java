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
import com.compomics.pepshell.model.exceptions.CannotValidateException;

import java.io.File;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
public interface  FileValidatorInterface {

    /**
     * checks if a file is a valid input for parsing
     * @param aFile The file to validate
     * @return a boolean, true if validated, false otherwise
     * @throws com.compomics.pepshell.model.exceptions.CannotValidateException if the validation cannot be executed for any reason
     */
    boolean validateFile(File aFile) throws CannotValidateException;

    /**
     * checks if a file is a valid input given the extra annotations on how to parse the file
     * @param annotatedFile The file to validate
     * @return a boolean, true if validated, false otherwise
     * @throws com.compomics.pepshell.model.exceptions.CannotValidateException if the validation cannot be executed for any reason
     */

    boolean validateFile(AnnotatedFile annotatedFile) throws CannotValidateException;

    boolean canValidateFile(File aFile);

    boolean canValidateFile(AnnotatedFile annotatedFile);

}
