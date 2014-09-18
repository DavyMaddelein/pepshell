package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy
 */
public class PeptideGroup {

    private int startingAlignmentPosition;
    private int endAlignmentPostition;
    private int shortestPeptideIndex = -1;
    private final List<Peptide> listOfPeptides = new ArrayList<>();

    public PeptideGroup() {
        super();
    }

    public Peptide getShortestPeptide() {
        return listOfPeptides.get(shortestPeptideIndex);
    }

    public void setShortestPeptideIndex(int peptideIndex) {
        this.shortestPeptideIndex = peptideIndex;
    }

    public void setStartingAlignmentPostion(int startingAlignmentPosition) {
        this.startingAlignmentPosition = startingAlignmentPosition;
    }

    public void setEndAlignmentPosition(int endAlignmentPosition) {
        this.endAlignmentPostition = endAlignmentPosition;
    }

    public int getStartingAlignmentPosition() {
        return this.startingAlignmentPosition;
    }

    public int getEndAlignmentPosition() {
        return this.endAlignmentPostition;
    }

    public List<Peptide> getPeptideList() {
        return Collections.unmodifiableList(listOfPeptides);
    }

    public PeptideGroup addPeptide(Peptide aPeptide) {
        if (!listOfPeptides.contains(aPeptide)) {
            listOfPeptides.add(aPeptide);
        } else {
            listOfPeptides.get(listOfPeptides.indexOf(aPeptide)).incrementTimesFound();
        }
        return this;
    }

    public void setAlignmentPositions(int startingAlignmentPosition, int lastAlignmentPosition) {
        this.startingAlignmentPosition = startingAlignmentPosition;
        this.endAlignmentPostition = lastAlignmentPosition;
    }

    @Override
    public String toString() {
        return this.getShortestPeptide().getSequence();
    }

    public PeptideGroup addPeptides(List<Peptide> peptideList) {
        for (Peptide aPeptide : peptideList) {
            if (!listOfPeptides.contains(aPeptide)) {
                listOfPeptides.add(aPeptide);
            } else {
                listOfPeptides.get(listOfPeptides.indexOf(aPeptide)).incrementTimesFound();
            }
        }
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.startingAlignmentPosition;
        hash = 61 * hash + this.endAlignmentPostition;
        hash = 61 * hash + (this.listOfPeptides != null ? this.listOfPeptides.size() : 0);
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
        final PeptideGroup other = (PeptideGroup) obj;
        if (this.startingAlignmentPosition != other.getStartingAlignmentPosition()) {
            return false;
        }
        if (this.endAlignmentPostition != other.getEndAlignmentPosition()) {
            return false;
        }
        return other.getPeptideList() == null || other.getPeptideList().equals(this.getPeptideList());
    }

}
