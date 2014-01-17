package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class PeptideGroup<T extends Peptide> extends ArrayList<T> {

    private int startingAlignmentPosition;
    private int endAlignmentPostition;
    private int shortestPeptideIndex = -1;

    public PeptideGroup() {
        super();
    }

    public T getShortestPeptide() {
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
    public final boolean add(T e) {
        boolean added = false;
        if (!this.contains(e)) {
            added = super.add(e);
        }
        return added;
    }
}