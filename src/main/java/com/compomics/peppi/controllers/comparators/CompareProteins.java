/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.controllers.comparators;

import com.compomics.peppi.model.Protein;
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
