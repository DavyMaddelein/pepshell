package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.Peptide;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class ComparePeptides implements Comparator<Peptide>,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Peptide firstPeptide, Peptide secondPeptide) {
        return firstPeptide.toString().compareTo(firstPeptide.toString());

    }
}
