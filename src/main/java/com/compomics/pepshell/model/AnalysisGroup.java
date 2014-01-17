package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Davy
 */
public class AnalysisGroup<T extends Experiment> extends ArrayList<T> {

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

    public Experiment getAnalysisGroupProteins() {
        Experiment allAnalysisGroupProteins = new Experiment(-1, "combined experiments of analysisgroup " + this.analysisName);
        for (T experiment : this) {
            Iterator<Protein> proteinIterator = experiment.iterator();
            while (proteinIterator.hasNext()) {
                Protein protein = proteinIterator.next();
                allAnalysisGroupProteins.add(protein);

            }
        }
        return allAnalysisGroupProteins;
    }
}
