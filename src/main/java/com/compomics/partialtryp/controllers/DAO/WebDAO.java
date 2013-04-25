/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.controllers.DAO;

import com.compomics.partialtryp.controllers.AccessionConverter;
import com.compomics.partialtryp.model.exceptions.ConversionException;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class WebDAO {
    
    public static String fetchSequence(String proteinAccession) throws IOException, ConversionException {
        if (proteinAccession.contains("gi") || proteinAccession.contains("|")){
            return UniprotDAO.fetchSequenceFromUniprot(AccessionConverter.GIToUniprot(proteinAccession));
        }
        else {
            return UniprotDAO.fetchSequenceFromUniprot(proteinAccession);
        }
        
    }
    
}
