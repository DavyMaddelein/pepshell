package com.compomics.pepshell.model.exceptions;

import java.io.IOException;

/**
 *
 * @author Davy Maddelein
 */
public class FastaCouldNotBeReadException extends IOException {

    private String fastaFileName;
/**
 * constructs a {@code FastaCouldNotBeReadException} with null as it's error detail message
 * @param fastaFileName the file name of the fasta
 */
    public FastaCouldNotBeReadException(String fastaFileName) {
        super();
        this.fastaFileName = fastaFileName;
    }

    /**
     * constructs a {@code FastaCouldNotBeReadException}with the specified detail message.The message can be retrieved later by the Throwable.getMessage() method of class java.lang.Throwable.
     * @param message the detail message
     * @param fastaFileName the file name of the fasta
     */
    public FastaCouldNotBeReadException(String message, String fastaFileName) {
        super(message);
        this.fastaFileName = fastaFileName;
    }

    /**
     * get the file name of the fasta that failed to be read
     * @return the fasta file name
     */
    public String getFailedFastaFileName() {
        return this.fastaFileName;
    }
}
