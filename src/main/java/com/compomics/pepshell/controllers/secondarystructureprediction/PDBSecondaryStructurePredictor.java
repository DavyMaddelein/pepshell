package com.compomics.pepshell.controllers.secondarystructureprediction;

import com.compomics.pepshell.controllers.DAO.PDBDAO;
import java.io.IOException;

/**
 *
 * @author Davy
 */
public class PDBSecondaryStructurePredictor extends SecondaryStructurePrediction {

    @Override
    public String getPrediction(String pdbAccession) throws IOException {
        StringBuilder secondaryStructure = new StringBuilder();
        String pdbFile = PDBDAO.getPdbFileInMem(pdbAccession);
        String[] pdbLines = pdbFile.split("\n");
        for (String aPdbLine : pdbLines) {
            if (aPdbLine.startsWith("HELIX")) {
                aPdbLine.split(" ");
            }
            if (aPdbLine.startsWith("SHEET")) {
                aPdbLine.split(" ");
            }
        }
        return secondaryStructure.toString();
    }

}
