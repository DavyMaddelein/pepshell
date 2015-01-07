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
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by svend on 02/12/2014.
 */
public class AbstractFileParser implements FileParserInterface {

    @Override
    public Experiment parseExperimentFile(FileBasedExperiment aFile) throws CouldNotParseException {
        LineNumberReader lineReader;
        try {
            lineReader = new LineNumberReader(new FileReader(aFile.getExperimentFile()));
        } catch (FileNotFoundException ex) {
            CouldNotParseException e = new CouldNotParseException("file could not be found");
            throw e;
        }
        String line;
        if (aFile.getExperimentFile().getAnnotations().fileHasHeaders()) {
            try {
                //we don't need headers
                line = lineReader.readLine();
            } catch (IOException ex) {
                CouldNotParseException e = new CouldNotParseException("file could not be found");
                throw e;

            }
        }
        try {
            line = lineReader.readLine();
            String[] columns;
            while (line != null) {
                columns = line.split(aFile.getExperimentFile().getAnnotations().getValueSeparator());
                Protein lineProtein = new Protein(columns[aFile.getExperimentFile().getAnnotations().getProteinAccessionColumn() - 1]);
                Peptide linePeptide;
                if (aFile.getExperimentFile().getAnnotations().experimentHasRatio()) {
                    linePeptide = new QuantedPeptide(columns[aFile.getExperimentFile().getAnnotations().getPeptideSequence() - 1]);
                    ((QuantedPeptide) linePeptide).setRatio(Double.parseDouble(columns[aFile.getExperimentFile().getAnnotations().getRatioColumn() - 1]));
                    if (((QuantedPeptide) linePeptide).getRatio() > aFile.getMaxRatio()) {
                        aFile.setMaxRatio(((QuantedPeptide) linePeptide).getRatio());
                    }
                } else {
                    linePeptide = new Peptide(columns[aFile.getExperimentFile().getAnnotations().getPeptideSequence() - 1]);
                }
                if (aFile.getExperimentFile().getAnnotations().experimentHasIntensityValues()) {
                    linePeptide.setTotalSpectrumIntensity(aFile.getExperimentFile().getAnnotations().getIntensityColumn() - 1);
                    if (linePeptide.getTotalSpectrumIntensity() > aFile.getMaxIntensity()) {
                        aFile.setMaxIntensity(linePeptide.getTotalSpectrumIntensity());
                    }
                    if (linePeptide.getTotalSpectrumIntensity() < aFile.getMinIntensity()) {
                        aFile.setMinIntensity(linePeptide.getTotalSpectrumIntensity());
                    }
                }
                lineProtein.addPeptideGroup(new PeptideGroup(linePeptide));
                aFile.addProtein(lineProtein);
                line = lineReader.readLine();
            }

        } catch (IOException | CalculationException ex) {
            Logger.getLogger(AbstractFileParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aFile;
    }

    @Override
    public Experiment parseExperimentFile(AnnotatedFile annotatedFile) throws CouldNotParseException {
        return null;
    }
}
