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

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
class HDXFileValidator implements FileValidatorInterface {


    @Override
    public boolean validateFile(File aFile) {
        return false;
    }

    @Override
    public boolean canValidateFile(File aFile) {
        return aFile.getName().contains(".hdx") || (aFile instanceof AnnotatedFile && canValidateFile((AnnotatedFile)aFile));
    }

    @Override
    public boolean canValidateFile(AnnotatedFile annotatedFile) {
       return false;
    }


    @Override
    public boolean validateFile(AnnotatedFile annotatedFile) {
        return false;
    }
}
