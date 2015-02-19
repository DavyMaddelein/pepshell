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
import com.compomics.pepshell.model.SeparatedValueExperimentMetadata;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;

/**
 * Created by Davy Maddelein on 02/12/2014.
 */
public class FileParserFactory {

    private HDXFileParser hdxFileParser = new HDXFileParser();
    private AbstractFileParser genericFileParser = new AbstractFileParser();

    private static FileParserFactory instance;


    private FileParserFactory() {
    }

    public static FileParserFactory getInstance() {
        if (instance == null) {
            instance = new FileParserFactory();
        }
        return instance;
    }

    /**
     * guesses the correct parser for an experiment file and tries to convert it into an experiment object
     * @param aFile the file to parse
     * @return the object representation of the passed experiment file
     * @throws CouldNotParseException if there was a problem with parsing the file
     */
    public Experiment parseExperimentFile(FileBasedExperiment aFile) throws CouldNotParseException {
        //we can only assume what file it is based on the file type
        Experiment parsedExperiment;
        if (aFile.getExperimentFile().getName().contains(".hdx")) {
            parsedExperiment = hdxFileParser.parseExperimentFile(aFile);
        } else {
            parsedExperiment = genericFileParser.parseExperimentFile(aFile);
        }
        return parsedExperiment;
    }

    /**
     * parses a file with the given annotations to the layout of the experiment contained in the file
     * @param anAnnotatedFile the file to parse
     * @return the object representation of the experiments
     * @throws CouldNotParseException if there was a problem with parsing the file
     */
    public Experiment parseExperimentFile(AnnotatedFile anAnnotatedFile) throws CouldNotParseException {
        Experiment parsedExperiment = new Experiment(anAnnotatedFile.getName());
        if(anAnnotatedFile.isValidated()){
            if(anAnnotatedFile.getAnnotations() instanceof SeparatedValueExperimentMetadata){
                parsedExperiment = genericFileParser.parseExperimentFile(anAnnotatedFile);
            }
        }
        return parsedExperiment;
    }
}
