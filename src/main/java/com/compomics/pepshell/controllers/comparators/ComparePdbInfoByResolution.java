package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.PdbInfo;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class ComparePdbInfoByResolution implements Comparator<PdbInfo>,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(PdbInfo o1, PdbInfo o2) {
        if (o1.getResolution() == o2.getResolution()) {
            return 0;
        } else if (o1.getResolution() > o2.getResolution()) {
            return 1;
        } else {
            return -1;
        }
    }

}
