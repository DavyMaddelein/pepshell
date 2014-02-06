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
    private List<Peptide> listOfPeptides = new ArrayList<Peptide>();

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

    public void addPeptide(Peptide aPeptide) {
        listOfPeptides.add(aPeptide);
    }

    public void setAlignmentPositions(int startingAlignmentPosition, int lastAlignmentPosition) {
        this.startingAlignmentPosition = startingAlignmentPosition;
        this.endAlignmentPostition = lastAlignmentPosition;
    }

    @Override
    public String toString() {
        return this.getShortestPeptide().getSequence();
    }

    void addPeptides(List<Peptide> peptideList) {
        for (Peptide aPeptide : peptideList) {
            if (!listOfPeptides.contains(aPeptide)) {
                //todo fill this up
            } else {
                listOfPeptides.get(listOfPeptides.indexOf(aPeptide)).incrementTimesFound();
            }
        }

    }
}
