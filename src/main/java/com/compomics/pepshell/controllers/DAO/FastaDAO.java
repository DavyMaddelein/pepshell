package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.properties.ViewProperties;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Proteases;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.enums.ViewPropertyEnum;
import com.compomics.pepshell.model.exceptions.AggregateFastaReadingException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class FastaDAO {

    /**
     * sets the {@code Protein}s in a project to the projects contained in the
     * fasta file
     *
     * @param fastaFile fasta file to read {@code Protein}s from
     * @param projectToAddProteinsTo the project to add the {@code Protein}s to
     * @throws FastaCouldNotBeReadException is thrown when there is an error
     * reading and parsing from the fasta file
     * @throws FileNotFoundException if the fasta file could not be found
     * @throws IOException if there was a problem with the file reader
     */
    public static void setProjectProteinsToFastaFileProteins(File fastaFile, Experiment projectToAddProteinsTo) throws FastaCouldNotBeReadException, FileNotFoundException, IOException {
        projectToAddProteinsTo.setProteins(getListOfProteinsFromFastaFile(fastaFile));
    }

    public static <T extends Protein> void mapFastaSequencesToProteinAccessions(File fastaFile, List<T> experimentToAddSequencesTo) throws FastaCouldNotBeReadException, FileNotFoundException, IOException {

        for (Protein aParsedProtein : getListOfProteinsFromFastaFile(fastaFile)) {
            Iterator<T> proteinIterator = experimentToAddSequencesTo.iterator();
            while (proteinIterator.hasNext()) {
                T experimentProtein = proteinIterator.next();
                if (experimentProtein.getProteinAccession().equalsIgnoreCase(aParsedProtein.getProteinAccession())) {
                    experimentProtein.setSequence(aParsedProtein.getProteinSequence());
                    experimentProtein.setProteinName(aParsedProtein.getProteinName());
                }
            }
        }
    }

    /**
     * sets the {@code Protein}s in a project to the projects contained in the
     * {@code List} of fasta files
     *
     * @param fastaFiles the fasta files to read the {@code Protein}s from
     * @param projectToAddProteinsTo the project to add the {@code Protein}s to
     * @throws IOException if there was a problem with the file reader
     * @throws FileNotFoundException if the fasta file could not be found
     * @throws AggregateFastaReadingException if one or more
     * {@code FastaCouldNotBeReadException}s occurred
     */
    //TODO perhaps make multithreaded?
    public static void setProjectProteinsToMultipleFastaFileProteins(List<File> fastaFiles, Experiment projectToAddProteinsTo) throws IOException, FileNotFoundException, AggregateFastaReadingException {
        Set<Protein> uniqueProteinsToAdd = new HashSet<Protein>();
        AggregateFastaReadingException arex = new AggregateFastaReadingException("one or more fasta files could not be read");
        for (File aFastaFile : fastaFiles) {
            try {
                uniqueProteinsToAdd.addAll(getListOfProteinsFromFastaFile(aFastaFile));
            } catch (FastaCouldNotBeReadException ex) {
                arex.addFastaReadingException(ex);
            }
        }
        if (!arex.getExceptionList().isEmpty()) {
            throw arex;
        }
        projectToAddProteinsTo.addAll(uniqueProteinsToAdd);
    }

    /**
     * parses a fasta files and returns a {@code Set} of the parsed
     * {@code Protein}s
     *
     * @param fastaFile the fasta file to read from
     * @return a {@code Set} of {@code Protein}s parsed from the fasta file
     * @throws FastaCouldNotBeReadException if there was a problem parsing the
     * fasta file
     * @throws FileNotFoundException if the fasta file could not be found
     * @throws IOException if there was a problem with the file reader
     */
    private static List<Protein> getListOfProteinsFromFastaFile(File fastaFile) throws FastaCouldNotBeReadException, FileNotFoundException, IOException {
        return getListOfProteinsFromFastaFile(fastaFile, false);
    }

    private static List<Protein> getListOfProteinsFromFastaFile(File fastaFile, boolean digest) throws FastaCouldNotBeReadException, FileNotFoundException, IOException {
        LineNumberReader lineReader = null;
        List<Protein> parsedProteins = new ArrayList<Protein>();
        try {
            lineReader = new LineNumberReader(new FileReader(fastaFile));
            String header = "";
            String name = "";
            StringBuilder sequence = new StringBuilder();
            String fastaLine;
            while ((fastaLine = lineReader.readLine()) != null) {
                if (fastaLine.contains(">")) {
                    if (!header.isEmpty() && sequence.length() != 0) {
                        Protein aParsedProtein = new Protein(header, sequence.toString());
                        aParsedProtein.setProteinName(name);
                        parsedProteins.add(aParsedProtein);
                        sequence = new StringBuilder();
                    }
                    header = fastaLine.substring(fastaLine.indexOf(">") + 1, fastaLine.indexOf("|", fastaLine.indexOf("|") + 1));
                    name = fastaLine.substring(fastaLine.lastIndexOf("|") + 1, fastaLine.length() - 1);
                    if (name.isEmpty()) {
                        name = header;
                    }
                } else {
                    sequence.append(fastaLine);
                }
            }
            if (!header.isEmpty() && sequence.length() != 0) {
                parsedProteins.add(new Protein(header, sequence.toString()));
            }
            if (digest) {
                for (Protein aProtein : parsedProteins) {
                    List<String> possiblePeptides = Proteases.getProteaseMap().get(ViewProperties.getInstance().getProperty(ViewPropertyEnum.PREFERREDENZYME.getKey())).digest(aProtein.getProteinSequence());
                    for (String aPeptideSequence : possiblePeptides) {
                        aProtein.add(new PeptideGroup().add(new Peptide(aPeptideSequence)));
                    }
                }
            }
            return parsedProteins;
        } catch (IOException ioe) {
            throw new FastaCouldNotBeReadException(ioe.getMessage(), fastaFile.getName());
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }
        }
    }
}