/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
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
