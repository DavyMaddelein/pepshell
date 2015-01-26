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
public class FileValidatorFactory {

    private static FileValidatorFactory instance = new FileValidatorFactory();

    HDXFileValidator hdxFileValidator = new HDXFileValidator();

    public static FileValidatorFactory getInstance() {
        return instance;
    }

    private FileValidatorFactory() {
    }

    public <T extends File> boolean validate(T fileToValidate) throws CannotValidateException {
        boolean validated = false;
        if (fileToValidate == null || !fileToValidate.exists()) {
            throw new CannotValidateException("no file was given or the file could not be found");
        }
        if (fileToValidate.getName().contains(".hdx")) {

            validated = new HDXFileValidator().validateFile(fileToValidate);

        } else {
            if (fileToValidate instanceof AnnotatedFile) {
                validated = new AbstractFileValidator().validateFile((AnnotatedFile) fileToValidate);

            } else {
                validated = new AbstractFileValidator().validateFile(fileToValidate);

            }
        }
        return validated;
    }
}
