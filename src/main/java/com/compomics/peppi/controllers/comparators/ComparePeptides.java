package com.compomics.peppi.controllers.comparators;

import com.compomics.peppi.model.Peptide;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class ComparePeptides implements Comparator<Peptide> {

    public int compare(Peptide firstPeptide, Peptide secondPeptide) {
        return firstPeptide.toString().compareTo(firstPeptide.toString());

    }
}
