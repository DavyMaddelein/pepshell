package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class WebDAO {

    public static String fetchSequence(String proteinAccession) throws IOException, ConversionException {
        String uniprotProteinAccession = proteinAccession;
        if (proteinAccession.contains("gi") || proteinAccession.contains("|")) {
            uniprotProteinAccession = AccessionConverter.GIToUniprot(proteinAccession);
        }
        return UniprotDAO.fetchSequenceFromUniprot(uniprotProteinAccession);
    }
}
