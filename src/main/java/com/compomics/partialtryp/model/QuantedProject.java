package com.compomics.partialtryp.model;

/**
 *
 * @author Davy
 */
public class QuantedProject extends Project {

    private int highestRatioInProject;
    private int lowestRatioInProject;

    public QuantedProject(int projectId, String projectName) {
        super(projectId, projectName);
    }

    public int getHighestRatioInProject() {
        return highestRatioInProject;
    }

    public void setHighestRatioInProject(int highestRatioInProject) {
        this.highestRatioInProject = highestRatioInProject;
    }

    public int getLowestRatioInProject() {
        return lowestRatioInProject;
    }

    public void setLowestRatioInProject(int lowestRatioInProject) {
        this.lowestRatioInProject = lowestRatioInProject;
    }
}
