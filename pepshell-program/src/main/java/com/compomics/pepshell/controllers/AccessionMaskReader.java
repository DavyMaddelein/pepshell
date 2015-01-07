package com.compomics.pepshell.controllers;

import com.compomics.pepshell.model.Protein;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Davy Maddelein
 */
public class AccessionMaskReader {

    public static Map<Protein, String> parseAccessionFile(File selectedFile) throws FileNotFoundException, IOException {
        Map<Protein, String> accessionMasks = new HashMap<>();
        if (selectedFile != null && selectedFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] splitLines = line.split("=");
                    accessionMasks.put(new Protein(splitLines[0]), splitLines[1]);
                }
            }
        }
        return accessionMasks;
    }

}
