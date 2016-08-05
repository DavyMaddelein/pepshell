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

package com.compomics.pepshell.controllers.dataimport.filehandlers;

import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.FileBasedExperiment;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;


/**
 * common interface that all file parsers in PepShell should implement
 * Created by Davy Maddelein on 02/12/2014.
 */
public interface FileParserInterface {


    /**
     * parses an experiment file
     * @param aFile file to try and parse
     * @return the {@link Experiment} representation of the file
     * @throws CouldNotParseException if the file could not be parsed for whatever reason
     */
    Experiment parseExperimentFile(FileBasedExperiment aFile) throws CouldNotParseException;

    /**
     *
     * @param annotatedFile file with annotations about its buildup
     * @return the {@link Experiment} representation of the file
     * @throws CouldNotParseException if the file could not be parsed for whatever reason
     */
    Experiment parseExperimentFile(AnnotatedFile annotatedFile) throws CouldNotParseException;

}
