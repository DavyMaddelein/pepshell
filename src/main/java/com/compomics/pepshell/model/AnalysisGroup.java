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

    public Experiment getAnalysisGroupProteins() {
        Experiment allAnalysisGroupProteins = new Experiment(-1, "combined experiments of analysisgroup " + this.analysisName);
        for (Experiment experiment : groupedExperiments) {
            for (Protein protein : experiment.getProteins()) {
                allAnalysisGroupProteins.addProtein(protein);

            }
        }
        return allAnalysisGroupProteins;
    }
}
