package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class PeptideGroup extends ArrayList<Peptide> {

    private int startingAlignmentPosition;
    private int endAlignmentPostition;
    private int shortestPeptideIndex = -1;

    public PeptideGroup() {
        super();
    }

    public PeptideGroup(List<String> listOfPotentialPeptides) {
        super();
        this.add(listOfPotentialPeptides);
    }

    public Peptide getShortestPeptide() {
        return this.get(shortestPeptideIndex);
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

    public void setAlignmentPositions(int startingAlignmentPosition, int lastAlignmentPosition) {
        this.startingAlignmentPosition = startingAlignmentPosition;
        this.endAlignmentPostition = lastAlignmentPosition;
    }

    @Override
    public String toString() {
        return this.getShortestPeptide().getSequence();
    }

    @Override
    public final boolean add(Peptide e) {
        boolean added = false;
        if (!this.contains(e)) {
            added = super.add(e);
        }
        return added;
    }

    public final boolean add(List<String> peptideSequences) {
        for (String aPeptideSequence : peptideSequences) {
            this.add(new Peptide(aPeptideSequence));
        }
        return true;
    }
}