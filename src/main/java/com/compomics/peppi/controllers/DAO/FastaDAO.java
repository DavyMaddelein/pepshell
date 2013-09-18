package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.AggregateFastaReadingException;
import com.compomics.peppi.model.exceptions.FastaCouldNotBeReadException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class FastaDAO {

    /**
     * sets the {@code Protein}s in a project to the projects contained in the fasta file
     * @param fastaFile fasta file to read {@code Protein}s from
     * @param projectToAddProteinsTo the project to add the {@code Protein}s to
     * @throws FastaCouldNotBeReadException is thrown when there is an error reading and parsing from the fasta file
     * @throws FileNotFoundException if the fasta file could not be found
     * @throws IOException if there was a problem with the file reader
     */
    public static void setProjectProteinsToFastaFileProteins(File fastaFile, Project projectToAddProteinsTo) throws FastaCouldNotBeReadException, FileNotFoundException, IOException {
        projectToAddProteinsTo.setProteins(getListOfProteinsFromFastaFile(fastaFile));
    }
/**
 * sets the {@code Protein}s in a project to the projects contained in the {@code List} of fasta files
 * @param fastaFiles the fasta files to read the {@code Protein}s from
 * @param projectToAddProteinsTo the project to add the {@code Protein}s to
 * @throws IOException if there was a problem with the file reader
 * @throws FileNotFoundException if the fasta file could not be found
 * @throws AggregateFastaReadingException if one or more {@code FastaCouldNotBeReadException}s occurred
 */
    //TODO perhaps make multithreaded?
    public static void setProjectProteinsToMultipleFastaFileProteins(List<File> fastaFiles, Project projectToAddProteinsTo) throws IOException, FileNotFoundException,AggregateFastaReadingException {
        Set<Protein> uniqueProteinsToAdd = new HashSet<Protein>();
        AggregateFastaReadingException arex = new AggregateFastaReadingException("one or more fasta files could not be read");
        for (File aFastaFile : fastaFiles) {
            try {
                uniqueProteinsToAdd.addAll(getListOfProteinsFromFastaFile(aFastaFile));
            } catch (FastaCouldNotBeReadException ex) {
                arex.addFastaReadingException(ex);
            }
        } if (!arex.getExceptionList().isEmpty()){
            throw arex;
        }
        projectToAddProteinsTo.getProteins().addAll(uniqueProteinsToAdd);
    }

    /**
     * parses a fasta files and returns a {@code Set} of the parsed {@code Protein}s
     * @param fastaFile the fasta file to read from
     * @return a {@code Set} of {@code Protein}s parsed from the fasta file
     * @throws FastaCouldNotBeReadException if there was a problem parsing the fasta file
     * @throws FileNotFoundException if the fasta file could not be found
     * @throws IOException if there was a problem with the file reader
     */
    private static Set<Protein> getListOfProteinsFromFastaFile(File fastaFile) throws FastaCouldNotBeReadException, FileNotFoundException, IOException {
        LineNumberReader lineReader = null;
        Set<Protein> parsedProteins = new HashSet<Protein>();
        try {
            lineReader = new LineNumberReader(new FileReader(fastaFile));
            String header = "";
            StringBuilder sequence = new StringBuilder();
            String fastaLine;
            while ((fastaLine = lineReader.readLine()) != null) {
                if (fastaLine.contains(">")) {
                    if (!header.isEmpty() && sequence.length() != 0) {
                        parsedProteins.add(new Protein(header, sequence.toString()));
                        sequence = new StringBuilder();
                    }
                    header = fastaLine.substring(fastaLine.indexOf(">") + 1, fastaLine.indexOf("|", fastaLine.indexOf("|") + 1));
                } else {
                    sequence.append(fastaLine);
                }
            }
            if (!header.isEmpty() && sequence.length() != 0) {
                parsedProteins.add(new Protein(header, sequence.toString()));
            }
            return parsedProteins;
        } catch (IOException ioe) {
            throw new FastaCouldNotBeReadException(ioe.getMessage(),fastaFile.getName());
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }
        }
    }
}