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

import com.compomics.pepshell.controllers.datamanagment.FlyweightProtein;
import com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager;
import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.FileBasedExperiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.exceptions.AggregatedException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * generic separated value style file parser
 * Created by Davy Maddelein on 02/12/2014.
 */
public class AbstractFileParser implements FileParserInterface {

    @Override
    public Experiment parseExperimentFile(FileBasedExperiment aFile) throws CouldNotParseException {
        //we can't make any guesses about how the file is built up
        if (aFile.getExperimentFile().getAnnotations() == null) {
            throw new CouldNotParseException("annotations missing from file");
        }
        return parseExperimentFile(aFile.getExperimentFile());
    }

    @Override
    public Experiment parseExperimentFile(AnnotatedFile annotatedFile) throws CouldNotParseException {

        Experiment experiment = new Experiment(annotatedFile.getName());

        try (LineNumberReader lineReader = new LineNumberReader(new FileReader(annotatedFile))) {

            String line;

            if (annotatedFile.getAnnotations().fileHasHeaders()) {
                //we don't need headers, these should be defined in the annotations
                //todo check here if file actually has headers or not
                lineReader.readLine();
            }


            String[] columns;
            AggregatedException exceptionList = null;
            while ((line = lineReader.readLine()) != null) {
                try {
                    columns = line.split(annotatedFile.getAnnotations().getValueSeparator());

                    //check if we can build peptides
                    if (columns[annotatedFile.getAnnotations().getPeptideSequenceColumn() - 1].trim().length() == 0) {
                        throw new CouldNotParseException("missing sequence value at line " + lineReader.getLineNumber());
                    } else if (columns[annotatedFile.getAnnotations().getProteinAccessionColumn() - 1].trim().length() == 0) {
                        throw new CouldNotParseException("missing accession value at line " + lineReader.getLineNumber());
                    }

                    Peptide linePeptide;

                    //check if file has quants
                    if (annotatedFile.getAnnotations().experimentHasRatio()) {
                        linePeptide = new QuantedPeptide(columns[annotatedFile.getAnnotations().getPeptideSequenceColumn() - 1]);
                        ((QuantedPeptide) linePeptide).setRatio(Double.parseDouble(columns[annotatedFile.getAnnotations().getRatioColumn() - 1]));
                    } else {
                        linePeptide = new Peptide(columns[annotatedFile.getAnnotations().getPeptideSequenceColumn() - 1]);
                    }

                    //add locations on protein to peptide
                    if (annotatedFile.getAnnotations().experimentHasPeptideLocationValues()) {
                        linePeptide.setBeginningProteinMatch(Integer.parseInt(columns[annotatedFile.getAnnotations().getPeptideStartColumn() - 1]));
                        linePeptide.setEndProteinMatch(Integer.parseInt(columns[annotatedFile.getAnnotations().getPeptideEndColumn() - 1]));
                    }

                    //add intentisities
                    if (annotatedFile.getAnnotations().experimentHasIntensityValues()) {
                        Double intensityValue = Double.parseDouble(columns[annotatedFile.getAnnotations().getIntensityColumn() - 1]);
                        linePeptide.addTotalSpectrumIntensity(intensityValue);

                    }
                    //create groups and push to a storage
                    PeptideGroup peptideGroup = (new PeptideGroup(linePeptide));
                    PepshellProtein proteinToAdd = new PepshellProtein(columns[annotatedFile.getAnnotations().getProteinAccessionColumn() - 1]);

                    ProteinStoreManager.getInstance().addToStore(proteinToAdd);
                    proteinToAdd = new FlyweightProtein(proteinToAdd.getOriginalAccession(), proteinToAdd.getExtraIdentifier());
                    proteinToAdd.addPeptideGroup(peptideGroup);
                    // TODO: 5/23/2016 check if this needs to be chanced with the storage
                    experiment.addProtein(proteinToAdd);

                } catch (Exception ex) {
                    //todo replace with correct exceptions
                    if (exceptionList == null) {
                        exceptionList = new AggregatedException("could not parse certain lines");
                    }
                    exceptionList.addException(ex);
                }
            }
            //TODO do something with the aggregated exception
        } catch (IOException e) {
            CouldNotParseException ex = new CouldNotParseException("file could not be found");
            ex.initCause(e);
            throw ex;
        }
        return experiment;
    }
}
