package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class ProgressMessage {

    private int progressPercentage;
    private final String taskName;

    public ProgressMessage(String aTaskName) {
        this.taskName = aTaskName;
    }

    public ProgressMessage(String aTaskName, int aPercentage) {
        this.taskName = aTaskName;
        this.progressPercentage = aPercentage;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getTaskName() {
        return taskName;
    }

}
