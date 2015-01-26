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

import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.IOException;

/**
 *
 * @author Davy Maddelein
 */
public class WebDAO {

    public static String fetchSequence(String proteinAccession) throws IOException, ConversionException {
        String uniprotProteinAccession = proteinAccession;
        uniprotProteinAccession = AccessionConverter.toUniprot(proteinAccession);
        return UniprotDAO.fetchSequenceFromUniprot(uniprotProteinAccession);
    }
}
