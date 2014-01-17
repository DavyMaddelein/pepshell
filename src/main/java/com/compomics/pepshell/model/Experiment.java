package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Experiment <T extends Protein> extends ArrayList<T> {

    private int experimentId;
    private String experimentName;

    public Experiment(int experimentId, String experimentName) {
        this.experimentId = experimentId;
        this.experimentName = experimentName;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public void setProteins(List<T> fetchedProteins) {
        this.addAll(fetchedProteins);
    }

    public String getExperimentName() {
        return experimentName;
    }

    @Override
    public String toString() {
        return experimentName;
    }

    @Override
    public final boolean add(T e) {
        boolean added = false;
        if (this.contains(e)) {
            this.get(this.indexOf(e)).addAll(e);
        } else {
            added = super.add(e);
        }
        return added;
    }

    public T get(T aProtein) {
        T toReturnProtein = null;
        for (T proteinInList : this) {
            if (proteinInList.getProteinAccession().equalsIgnoreCase(aProtein.getProteinAccession())) {
                toReturnProtein = proteinInList;
                break;
            }
        }
        if (toReturnProtein == null) {
            throw new IndexOutOfBoundsException("could not find protein in the experiment");
        }
        return toReturnProtein;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.experimentId;
        hash = 61 * hash + (this.experimentName != null ? this.experimentName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Experiment other = (Experiment) obj;
        if (this.experimentId != other.experimentId) {
            return false;
        }
        if ((this.experimentName == null) ? (other.experimentName != null) : !this.experimentName.equals(other.experimentName)) {
            return false;
        }
        return true;
    }
}
