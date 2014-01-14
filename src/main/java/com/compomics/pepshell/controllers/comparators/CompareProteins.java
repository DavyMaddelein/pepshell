package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.Protein;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class CompareProteins implements Comparator<Protein> {

    public int compare(Protein firstProtein, Protein secondProtein) {
        return firstProtein.toString().compareTo(secondProtein.toString());
    }

    
    
}
