/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.controllers;

import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.ProteinInfo;

/**
 *
 * @author Davy
 */
public class ProteinInfoFetcher {

    public ProteinInfoFetcher(Protein protein) {


    }

    public ProteinInfo fetchProteinInfoFromUniprot(String accession){
        ProteinInfo protInfo = new ProteinInfo();
        
        
        //protInfo.setSequence(sequence);
        return protInfo;
    }

    
}
