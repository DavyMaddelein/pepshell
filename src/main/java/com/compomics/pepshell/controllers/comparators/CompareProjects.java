package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.Experiment;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class CompareProjects implements Comparator<Experiment>,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Experiment firstProject, Experiment secondProject) {
        return firstProject.toString().compareTo(secondProject.toString());
    }

    
}
