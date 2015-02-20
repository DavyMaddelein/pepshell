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
package com.compomics.pepshell.controllers.DAO.Iterators;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * an Iterator to parse a fasta one sequence at a time, to avoid loading
 * everything into memory Created by Davy Maddelein on 07/01/2015.
 *
 * @param <T> a Pepshell compatible {@link com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein}
 */
public class FastaIterator<T extends PepshellProtein> implements Iterator<PepshellProtein> {

    private BufferedReader lineReader;
    private String header = "";


    /**
     * a fasta iterator for parsing a fasta file
     *
     * @param aFastaFile the file to parse
     * @throws FastaCouldNotBeReadException if the file does not adhere to the definition of a fasta
     */
    public FastaIterator(File aFastaFile) throws FastaCouldNotBeReadException {
        try {
            lineReader = new BufferedReader(new FileReader(aFastaFile));
        } catch (IOException ioe) {
            throw new FastaCouldNotBeReadException(ioe.getMessage(), aFastaFile.getName());
        }
    }

    /**
     * a fasta iterator for parsing a stream of characters in fasta format, reaching the end of the iterator will close the stream
     *
     * @param streamIn the stream to parse
     */
    public FastaIterator(InputStream streamIn) {
        lineReader = new BufferedReader(new InputStreamReader(streamIn));
    }

    /**
     * a fasta iterator for parsing a reader reading in stream of data in fasta format, reaching the end of the reader will close the reader
     *
     * @param reader
     */
    public FastaIterator(BufferedReader reader) {
        lineReader = reader;

    }

    /**
     * not the way I wanted to do it, see mark and reset in buffered readers but this works too
     * <p/>
     * Returns {@code true} if the iteration has more elements. (In other words,
     * returns {@code true} if {@link #next} would return an element rather than
     * throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        boolean hasMore;
        try {
            String line = header;
            if (line != null) {
                if (line.isEmpty()) {
                    line = lineReader.readLine();
                }
                if (line.startsWith(">")) {
                    header = line;
                    hasMore = true;
                } else {
                    throw new CouldNotParseException("file contains non fasta lines");
                }
            } else {
                lineReader.close();
                hasMore = false;
            }
        } catch (IOException | NullPointerException | CouldNotParseException e) {
            hasMore = false;
            FaultBarrier.getInstance().handleException(e, true);
        }
        return hasMore;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() throws NoSuchElementException {
        T aParsedProtein = null;
        try {
            String fastaLine = lineReader.readLine();
            String name = "";
            StringBuilder sequence = new StringBuilder();
            while (fastaLine != null) {
                if (fastaLine.contains(">")) {
                    break;
                } else {
                    sequence.append(fastaLine);
                }
                fastaLine = lineReader.readLine();
            }

            if (!header.isEmpty() && sequence.length() != 0) {
                name = header.substring(1, header.indexOf("|", header.indexOf("|") + 1));
                if (name.isEmpty()) {
                    name = header;
                }
                aParsedProtein = (T) new PepshellProtein(name, sequence.toString());
                header = fastaLine;
            }
        } catch (IOException e) {
            NoSuchElementException ex = new NoSuchElementException();
            ex.addSuppressed(ex);
            try {
                lineReader.close();
            } catch (IOException ioe) {
                FaultBarrier.getInstance().handleExceptionAndSendMail(ioe);
            }
            throw ex;
        }

        return aParsedProtein;
    }
}
