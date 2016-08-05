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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class AnalysisGroup {

    private final String analysisName;
    private final List<Experiment> groupedExperiments = new ArrayList<>();

    public AnalysisGroup(String analysisName) {
        super();
        this.analysisName = analysisName;
    }

    public String getName() {
        return analysisName;
    }

    @Override
    public String toString() {
        return analysisName;
    }

    public List<Experiment> getExperiments() {
        return Collections.unmodifiableList(groupedExperiments);
    }

    public void addExperiment(Experiment anExperiment) {
        groupedExperiments.add(anExperiment);
    }

}
