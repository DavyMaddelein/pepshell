package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Davy
 */
public class AnalysisGroup {

    private String analysisName;
    private List<Experiment> groupedExperiments = new ArrayList<Experiment>();

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
            Iterator<Protein> proteinIterator = experiment.getProteins().iterator();
            while (proteinIterator.hasNext()) {
                Protein protein = proteinIterator.next();
                allAnalysisGroupProteins.addProtein(protein);

            }
        }
        return allAnalysisGroupProteins;
    }
}
