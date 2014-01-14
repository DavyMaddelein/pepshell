package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.Experiment;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class CompareProjects implements Comparator<Experiment> {

    public int compare(Experiment firstProject, Experiment secondProject) {
        return firstProject.toString().compareTo(secondProject.toString());
    }

    
}
