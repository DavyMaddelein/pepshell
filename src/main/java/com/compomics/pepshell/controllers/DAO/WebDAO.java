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
