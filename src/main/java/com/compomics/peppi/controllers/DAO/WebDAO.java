/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.controllers.AccessionConverter;
import com.compomics.peppi.model.exceptions.ConversionException;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class WebDAO {
    
    public static String fetchSequence(String proteinAccession) throws IOException, ConversionException {
        String uniprotProteinAccession = proteinAccession;
        if (proteinAccession.contains("gi") || proteinAccession.contains("|")){
            uniprotProteinAccession = AccessionConverter.GIToUniprot(proteinAccession);
        }
        return UniprotDAO.fetchSequenceFromUniprot(uniprotProteinAccession);
        
    }
    
}
