package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class AnalysisGroup extends ArrayList<Experiment> {

    private String analysisName;

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

    public List<Protein> getAnalysisGroupProteins() {
        List<Protein> allAnalysisGroupProteins = new ArrayList<Protein>();
        for (Experiment project : this) {
            for (Protein protein : project.getProteins()) {
                if (allAnalysisGroupProteins.contains(protein)) {
                    allAnalysisGroupProteins.get(allAnalysisGroupProteins.indexOf(protein)).addAll(protein.getPeptideGroupsForProtein());
                } else {
                    allAnalysisGroupProteins.add(protein);
                }
            }
        }
        return allAnalysisGroupProteins;
    }
}
