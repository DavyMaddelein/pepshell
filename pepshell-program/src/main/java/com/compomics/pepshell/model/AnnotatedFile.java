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

package com.compomics.pepshell.model;

import com.compomics.pepshell.model.exceptions.ExperimentMetaData;

import java.io.File;
import java.net.URI;

/**
 * Created by Davy Maddelein on 26/11/2014.
 */
public class AnnotatedFile extends File {

    private SeparatedvalueExperimentMetaData annotations;
    private boolean validationState = false;

    public AnnotatedFile(String parent, String child) {
        super(parent, child);
    }

    public AnnotatedFile(File parent, String child) {
        super(parent, child);
    }

    public AnnotatedFile(URI uri) {
        super(uri);
    }

    public AnnotatedFile(String pathname) {
        super(pathname);
    }

    public AnnotatedFile addAnnotationsToFile(SeparatedvalueExperimentMetaData annotation) {
        if (annotations == null) {
            annotations = annotation;
        } else {
            annotations.mergeAnnotations(annotation);
        }
        return this;
    }

    public void setValidationState(boolean validation) {
        validationState = validation;

    }

    public SeparatedvalueExperimentMetaData getAnnotations() {
        return annotations;
    }

    public boolean isValidated() {
        return validationState;
    }
}
