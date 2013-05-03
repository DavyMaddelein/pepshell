/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Project {

    private int projectId;
    private String projectName;
    private List<Protein> projectProteins = new ArrayList<Protein>();

    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProteins(List<Protein> fetchedProteins) {
        this.projectProteins = fetchedProteins;
    }

    public List<Protein> getProteins() {
        return this.projectProteins;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public String toString() {
        return projectName;
    }
}
