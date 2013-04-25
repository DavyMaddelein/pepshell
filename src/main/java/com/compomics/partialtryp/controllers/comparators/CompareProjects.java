/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.controllers.comparators;

import com.compomics.partialtryp.model.Project;
import java.util.Comparator;

/**
 *
 * @author Davy
 */
public class CompareProjects implements Comparator<Project> {

    public int compare(Project firstProject, Project secondProject) {
        return firstProject.toString().compareTo(secondProject.toString());
    }

    
}
