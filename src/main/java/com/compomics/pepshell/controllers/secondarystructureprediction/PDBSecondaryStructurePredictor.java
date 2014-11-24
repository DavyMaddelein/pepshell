package com.compomics.pepshell.controllers.secondarystructureprediction;

import com.compomics.pepshell.controllers.DAO.PDBDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class PDBSecondaryStructurePredictor extends SecondaryStructurePrediction {

    @Override
    public List<String> getPrediction(String pdbAccession) throws IOException {
        List<String> secondaryStructure = new ArrayList<>();
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
        return secondaryStructure;
    }

}
