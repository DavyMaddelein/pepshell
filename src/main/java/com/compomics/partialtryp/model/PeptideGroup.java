package com.compomics.partialtryp.model;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class PeptideGroup extends ArrayList<Peptide> {

    private int startingAlignmentPosition;
    private int endAlignmentPostition;
    private int shortestPeptideIndex = -1;
    private static final long serialVersionUID = 1L;

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
}
