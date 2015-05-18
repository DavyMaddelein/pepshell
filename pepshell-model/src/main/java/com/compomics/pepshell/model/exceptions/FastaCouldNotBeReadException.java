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
