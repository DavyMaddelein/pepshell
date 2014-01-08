package com.compomics.peppi.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class Project {
//todo extends hashset
    private int projectId;
    private String projectName;
    private Set<Protein> projectProteins = new HashSet<Protein>();

    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProteins(Set<Protein> fetchedProteins) {
        this.projectProteins = fetchedProteins;
    }

    public Set<Protein> getProteins() {
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
