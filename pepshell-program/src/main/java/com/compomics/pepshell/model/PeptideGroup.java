/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class PeptideGroup {

    private int startingAlignmentPosition;
    private int endAlignmentPostition;
    private int shortestPeptideIndex = -1;
    private final List<Peptide> listOfPeptides = new ArrayList<>();

    public PeptideGroup(Peptide aPeptide) {
        listOfPeptides.add(aPeptide);
        shortestPeptideIndex = 0;
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
        return listOfPeptides;
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
        hash = 61 * hash + (this.listOfPeptides != null ? this.listOfPeptides.hashCode() : 0);
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
        return this.startingAlignmentPosition == other.getStartingAlignmentPosition() && this.endAlignmentPostition == other.getEndAlignmentPosition() && (other.getPeptideList() == null || other.getPeptideList().equals(this.getPeptideList()));
    }

}
