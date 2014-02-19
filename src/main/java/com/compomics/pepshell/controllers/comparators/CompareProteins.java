package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.Protein;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class CompareProteins implements Comparator<Protein>,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Protein firstProtein, Protein secondProtein) {
        return firstProtein.toString().compareTo(secondProtein.toString());
    }

    
    
}
