package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class QuantedExperiment extends Experiment {

    private int highestRatioInProject;
    private int lowestRatioInProject;

    public QuantedExperiment(int projectId, String projectName) {
        super(projectId, projectName);
    }

    public QuantedExperiment(Experiment project) {
        super(project.getExperimentId(),project.getExperimentName());
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
