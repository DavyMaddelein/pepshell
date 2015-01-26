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
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * an Iterator to parse a fasta a sequence at a time, to avoid loading everything into memory
 * Created by Davy Maddelein on 07/01/2015.
 * @param <T> a Pepshell compatible {@link Protein}
 */
public class FastaIterator<T extends Protein> implements Iterator<Protein> {

    private LineNumberReader lineReader;

    public FastaIterator(File aFastaFile) throws IOException {
        try {
            lineReader = new LineNumberReader(new FileReader(aFastaFile));
        } catch (IOException ioe) {
            throw new FastaCouldNotBeReadException(ioe.getMessage(), aFastaFile.getName());
        }
    }


    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        boolean returnvalue;
        try {
            lineReader.mark(Integer.MAX_VALUE);
            String line = lineReader.readLine();
            if (line != null && line.contains(">")) {
                returnvalue = true;
            } else {
                lineReader.close();
                returnvalue = false;
            }
            lineReader.reset();
        } catch (IOException e) {
            returnvalue = false;
            FaultBarrier.getInstance().handleException(e);
        }
        return returnvalue;
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
            String header = "";
            String name = "";
            StringBuilder sequence = new StringBuilder();
            while (fastaLine != null) {
                lineReader.mark(0);
                if (fastaLine.contains(">")) {
                    if (!header.isEmpty() && sequence.length() != 0) {
                        aParsedProtein = (T) new Protein(header, sequence.toString());
                        aParsedProtein.setProteinName(name);
                        lineReader.reset();
                        break;
                    }
                    header = fastaLine.substring(fastaLine.indexOf("|") + 1, fastaLine.indexOf("|", fastaLine.indexOf("|") + 1));
                    name = fastaLine.substring(fastaLine.lastIndexOf("|") + 1, fastaLine.length() - 1);
                    if (name.isEmpty()) {
                        name = header;
                    }
                } else {
                    sequence.append(fastaLine);
                }
                fastaLine = lineReader.readLine();
            }
        } catch (IOException e) {
            NoSuchElementException ex = new NoSuchElementException();
            ex.addSuppressed(ex);
            try {
                lineReader.close();
            } catch (IOException ioe) {

            }
            throw ex;
        }
        return aParsedProtein;
    }
}
