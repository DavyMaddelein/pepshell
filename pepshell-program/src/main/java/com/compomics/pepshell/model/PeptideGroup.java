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

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.util.ArrayList;
import java.util.List;

/**
 * grouping for peptides to keep same location peptides together and not clashing.
 * @author Davy Maddelein
 */
public class PeptideGroup {

    private int startingAlignmentPosition;
    private int endAlignmentPostition;
    private int shortestPeptideIndex = -1;
    private final List<PeptideInterface> listOfPeptides = new ArrayList<>();

    public PeptideGroup(PeptideInterface aPeptide) {
        listOfPeptides.add(aPeptide);
        shortestPeptideIndex = 0;
    }

    public PeptideInterface getShortestPeptide() {
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
        if (this.startingAlignmentPosition == -1 && this.getShortestPeptide() != null) {
            return this.getShortestPeptide().getBeginningProteinMatch();
        }
        return this.startingAlignmentPosition;
    }

    public int getEndAlignmentPosition() {
        if (this.endAlignmentPostition == -1 && this.getShortestPeptide() != null) {
            return this.getShortestPeptide().getEndProteinMatch();
        }
        return this.endAlignmentPostition;
    }

    public List<PeptideInterface> getPeptideList() {
        return listOfPeptides;
    }

    public PeptideGroup addPeptide(PeptideInterface aPeptide) {
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

    public PeptideGroup addPeptides(List<PeptideInterface> peptideList) {
        peptideList.stream().forEach((aPeptide) -> {
            if (!listOfPeptides.contains(aPeptide)) {
                listOfPeptides.add(aPeptide);
            } else {
                PeptideInterface OGPeptide = listOfPeptides.get(listOfPeptides.indexOf(aPeptide));
                if (!aPeptide.getTotalSpectrumIntensities().isEmpty()) {
                    OGPeptide.getTotalSpectrumIntensities().addAll(aPeptide.getTotalSpectrumIntensities());
                }
                try {
                    if (aPeptide instanceof QuantedPeptide && OGPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() > 0.0) {
                        ((QuantedPeptide) aPeptide).getHeavy().addAll(((QuantedPeptide) aPeptide).getHeavy());
                        ((QuantedPeptide) aPeptide).getLight().addAll(((QuantedPeptide) aPeptide).getLight());

                    }
                } catch (CalculationException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
                listOfPeptides.get(listOfPeptides.indexOf(aPeptide)).incrementTimesFound();
            }
        });
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
