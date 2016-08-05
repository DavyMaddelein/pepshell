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

package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.DAO.Iterators.FastaIterator;
import com.compomics.pepshell.controllers.properties.DataRetrievalProperties;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.enums.DataRetrievalPropertyEnum;
import com.compomics.pepshell.model.protein.Proteases;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.exceptions.AggregateFastaReadingException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * handles the extraction of data from fasta based files
 * @author Davy Maddelein
 */
public class FastaDAO {

    /**
     * sets the {@code PepshellProtein}s in a project to the projects contained in the
     * fasta file
     *
     * @param fastaFile                 fasta file to read {@code PepshellProtein}s from
     * @param experimentToAddProteinsTo the project to add the {@code PepshellProtein}s
     *                                  to
     * @throws FastaCouldNotBeReadException is thrown when there is an error
     *                                      reading and parsing from the fasta file
     * @throws FileNotFoundException        if the fasta file could not be found
     * @throws IOException                  if there was a problem with the file reader
     */
    public static void setExperimentProteinsToFastaFileProteins(File fastaFile, Experiment experimentToAddProteinsTo) throws IOException {
        FastaIterator<PepshellProtein> iterator = new FastaIterator<>(fastaFile);
        while (iterator.hasNext()){
            experimentToAddProteinsTo.addProtein(iterator.next());
        }
    }

    /**
     * maps the sequences parsed from a fasta to a list of proteins based
     * 
     * @param <T> a protein object compatible with pepshell
     * @param fastaFile the fasta to add the sequences from to the proteins of the passed experiment
     * @param listOfProteinsToMap the experiment to add sequences to
     * @throws FastaCouldNotBeReadException if the fasta could not be read properly
     * @throws FileNotFoundException if the file was not found
     * @throws IOException if there was a problem parsing the fasta file
     */
    public static <T extends PepshellProtein> void mapFastaSequencesToProteinAccessions(File fastaFile, List<? extends T> listOfProteinsToMap) throws IOException {
        FastaIterator<T> iterator = new FastaIterator<>(fastaFile);
        while (iterator.hasNext()) {
            T aParsedProtein = iterator.next();
            int index = listOfProteinsToMap.indexOf(aParsedProtein);
            //if(listOfProteinsToMap.stream().anyMatch(aParsedProtein))
            if (index > -1) {
                listOfProteinsToMap.get(index).setSequence(aParsedProtein.getProteinSequence());
                listOfProteinsToMap.get(index).setProteinName(aParsedProtein.getProteinName());
            }
        }
    }

    /**
     * sets the Proteins in a project to the projects contained in the List of
     * fasta files
     *
     * @param fastaFiles                the fasta files to read the Proteins from
     * @param experimentToAddProteinsTo the experiment to add the Proteins to
     * @throws AggregateFastaReadingException if one or more
     *                                        {@link FastaCouldNotBeReadException}s occurred
     */
    //TODO perhaps make multithreaded?
    public static void setExperimentProteinsToMultipleFastaFileProteins(List<? extends File> fastaFiles, Experiment experimentToAddProteinsTo) throws AggregateFastaReadingException {
        AggregateFastaReadingException arex = new AggregateFastaReadingException("one or more fasta files could not be read");
        fastaFiles.iterator().forEachRemaining((aFastaFile) ->{
            try {
                new FastaIterator<>(aFastaFile).forEachRemaining(experimentToAddProteinsTo::addProtein);
            } catch (FastaCouldNotBeReadException ex) {
                arex.addFastaReadingException(ex);
            }
        });
        if (!arex.getExceptionList().isEmpty()) {
            throw arex;
        }
    }

    /**
     * parses the given fasta file for the protein sequences
     *
     * @param sequence the sequence to digest with the preferred enzyme
     */
    private static List<PeptideGroup> digestProteinSequence(String sequence) {
        List<String> possiblePeptides = Proteases.getProteaseMap().get(DataRetrievalProperties.getInstance().getProperty(DataRetrievalPropertyEnum.PREFERREDENZYME.getKey())).digest(sequence);
        return possiblePeptides.stream().parallel().map((aPeptideSequence) -> new PeptideGroup(new Peptide(aPeptideSequence))).collect(Collectors.toList());
}
}
