package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;

/**
 *
 * @author Niels Hulstaert
 * @param <T>
 * @param <U>
 */
public interface PdbGradientDrawModeInterface<T extends Protein, U extends Peptide> extends GradientDrawModeInterface<T, U> {
    
    /**
     * Set the PDB accesson
     * 
     * @param pdbAccession the PDB accession
     */
    void setPdbAccession(String pdbAccession);
    
}
