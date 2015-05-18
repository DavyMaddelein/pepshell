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

package com.compomics.pepshell.view.dataviewing;

import com.compomics.pepshell.model.*;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Davy Maddelein
 */
public class SequenceCoveragePanel extends javax.swing.JPanel {

    private int[] coverage;
    private boolean isOriginalCcoverage = false;
    private PepshellProtein originalProtein;
    private int[] domains;

    /**
     * Creates new form ProteinBarPanel
     */
    public SequenceCoveragePanel() {
        initComponents();
        this.setBackground(Color.white);
    }

    public void showProteinCoverage(String proteinSequence, PepshellProtein protein) throws ArrayIndexOutOfBoundsException {
        this.showProteinCoverage(proteinSequence, protein, false);
    }

    public void showProteinCoverage(String proteinSequence, PepshellProtein protein, boolean setAsDefaultCoverage) throws ArrayIndexOutOfBoundsException {
        isOriginalCcoverage = false;
        if (setAsDefaultCoverage) {
            originalProtein = protein;
            isOriginalCcoverage = true;
        }
        List<Integer> peptideStarts = new ArrayList<>();
        List<Integer> peptideEnds = new ArrayList<>();
        coverage = new int[proteinSequence.length() + 1];
        domains = new int[proteinSequence.length() + 1];
        PeptideInterface peptide;
        for (PeptideGroup peptideGroup : protein.getPeptideGroups()) {
            peptide = peptideGroup.getRepresentativePeptide();
            int tempPeptideStart = proteinSequence.indexOf(peptide.getSequence());
            int tempPeptideEnd = tempPeptideStart + peptide.getSequence().length();
            tempPeptideStart += 1;

            for (int j = tempPeptideStart; j <= tempPeptideEnd; j++) {
                coverage[j]++;
            }
            peptideGroup.setAlignmentPositions(tempPeptideStart, tempPeptideEnd);
            peptideStarts.add(tempPeptideStart);
            peptideEnds.add(tempPeptideEnd);
        }
        for (FeatureWithLocation proteinDomains : protein.getDomains()) {
            for (int j = proteinDomains.getStartPosition(); j <= proteinDomains.getEndPosition(); j++) {
                domains[j]++;
            }
        }
        formatProteinSequence(proteinCoveragePane, proteinSequence, peptideStarts, peptideEnds, coverage, domains);
    }

    private static double formatProteinSequence(JEditorPane editorPane, String cleanSequence, List<Integer> selectedPeptideStart, List<Integer> selectedPeptideEnd, int[] coverage, int[] domains) {

        if (cleanSequence.length() != coverage.length - 1) {
            throw new IllegalArgumentException("The length of the coverage map has to be equal to the length of the sequence + 1!");
        }

        String sequenceTable = "", currentCellSequence = "";
        boolean selectedPeptide = false, coveredPeptide, domainCovered = false;
        double sequenceCoverage = 0;

        // see how many amino acids we have room for
        int fontWidth = editorPane.getGraphics().getFontMetrics().stringWidth("X");

        // hardcoding needed to due issues with other look and feels
        if (!UIManager.getLookAndFeel().getName().equalsIgnoreCase("Nimbus")) {
            fontWidth = 8; // 8 is to represent the default average font width in html

            // @TODO: find a way of removing this hardcoding...
        }

        int indexWidth = 200;

        if (cleanSequence.length() > 999) {
            indexWidth += 50;
        }

        double temp = (editorPane.getParent().getWidth() - indexWidth) / (double) fontWidth;
        int numberOfAminoAcidsPerRow = (int) (temp / 10);
        numberOfAminoAcidsPerRow *= 10;

        List<Integer> referenceMarkers = new ArrayList<>();

        boolean previousAminoAcidWasCovered = false;
        boolean previousAminoAcidWasSelected = false;

        // iterate the coverage table and create the formatted sequence string
        for (int i = 1; i < coverage.length; i++) {

            // add residue number and line break
            if (i % numberOfAminoAcidsPerRow == 1 || i == 1) {

                sequenceTable += "</tr><tr><td><font rgb(255,255,255)><a name=\"" + i + "\">" + i + "</a></font></td>";
                referenceMarkers.add(i);
            }

            // check if the current residues is covered
            if (coverage[i] > 0) {
                sequenceCoverage++;
                coveredPeptide = true;
            } else {
                coveredPeptide = false;
            }

            domainCovered = domains[i] > 0;

            // check if the current residue is contained in the selected peptide
            for (int possibleStart : selectedPeptideStart) {
                if (i == possibleStart) {
                    selectedPeptide = true;
                }
            }
            for (int possibleEnd : selectedPeptideEnd) {
                if (i == possibleEnd) {
                    selectedPeptide = false;
                }
            }

            if (previousAminoAcidWasSelected && (!selectedPeptide && !domainCovered)) {
                currentCellSequence += "</b></span>";
            }

            // highlight the covered and selected peptides
            if (selectedPeptide) {
                if (i % 10 == 1) {
                    currentCellSequence += "<span style=\"background:#7E83C7\"><b>" + cleanSequence.charAt(i - 1);
                } else {
                    if (previousAminoAcidWasSelected) {
                        currentCellSequence += cleanSequence.charAt(i - 1);
                    } else {
                        currentCellSequence += "</span><span style=\"background:#7E83C7\"><b>" + cleanSequence.charAt(i - 1);
                    }
                }

                previousAminoAcidWasSelected = true;

            } else if (domains[i] > 0) {
                if (i % 10 == 1) {
                    currentCellSequence += "<span style=\"background:#FF530D\">" + cleanSequence.charAt(i - 1);
                } else {
                    if (previousAminoAcidWasSelected) {
                        currentCellSequence += cleanSequence.charAt(i - 1);
                    } else {
                        currentCellSequence += "</span><span style=\"background:#FF530D\">" + cleanSequence.charAt(i - 1);
                    }
                }
                previousAminoAcidWasSelected = true;
                domainCovered = true;
            } else {
                previousAminoAcidWasSelected = false;

                if (coveredPeptide) {
                    if (i % 10 == 1) {
                        currentCellSequence += cleanSequence.charAt(i - 1);
                    } else {
                        if (previousAminoAcidWasCovered) {
                            currentCellSequence += cleanSequence.charAt(i - 1);
                        } else {
                            currentCellSequence += "<b></span>" + cleanSequence.charAt(i - 1);
                        }
                    }
                } else if (domainCovered) {
                    if (i % 10 == 1) {
                        currentCellSequence += "<span style=\"color:#9C9C9C opacity: 0.17\">" + cleanSequence.charAt(i - 1);
                    } else {
                        if (previousAminoAcidWasCovered) {
                            currentCellSequence += "<span style=\"color:#9C9C9C opacity: 0.17\">" + cleanSequence.charAt(i - 1);
                        } else {
                            currentCellSequence += cleanSequence.charAt(i - 1);
                        }
                    }
                } else {
                    currentCellSequence += cleanSequence.charAt(i - 1);
                }
            }

            // add the sequence to the formatted sequence
            if (i % 10 == 0) {
                if (previousAminoAcidWasCovered && !previousAminoAcidWasSelected) {
                    sequenceTable += "<td><tt>" + currentCellSequence + "</tt></td>";
                } else {
                    sequenceTable += "<td><tt>" + currentCellSequence + "</span></tt></td>";
                }

                currentCellSequence = "";
            }

            previousAminoAcidWasCovered = coveredPeptide;
        }

        // add remaining tags and complete the formatted sequence
        sequenceTable += "<td><tt>" + currentCellSequence + "</tt></td></table>";
        String formattedSequence = "<html><body><table cellspacing='2'>" + sequenceTable + "</html></body>";
        // display the formatted sequence
        editorPane.setText(formattedSequence);

        // make sure that the currently selected peptide is visible
        if (selectedPeptideStart.size() > 0 && selectedPeptideStart.get(0) != -1) {

            boolean referenceMarkerFound = false;

            for (int i = 0; i < referenceMarkers.size() - 1 && !referenceMarkerFound; i++) {
                if (selectedPeptideStart.get(0) >= referenceMarkers.get(i) && selectedPeptideStart.get(0) < referenceMarkers.get(i + 1)) {

                    final JEditorPane tempEditorPane = editorPane;
                    final String referenceMarker = referenceMarkers.get(i).toString();

                    // invoke later to give time for components to update
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            tempEditorPane.scrollToReference(referenceMarker);
                        }
                    });

                    referenceMarkerFound = true;
                }
            }

            if (!referenceMarkerFound) {

                final JEditorPane tempEditorPane = editorPane;
                final String referenceMarker = referenceMarkers.get(referenceMarkers.size() - 1).toString();

                // invoke later to give time for components to update
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        tempEditorPane.scrollToReference(referenceMarker);
                    }
                });
            }

        } else {
            editorPane.setCaretPosition(0);
        }

        return (sequenceCoverage / cleanSequence.length()) * 100;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        proteinCoveragePane = new javax.swing.JEditorPane();

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        proteinCoveragePane.setEditable(false);
        proteinCoveragePane.setBorder(null);
        proteinCoveragePane.setContentType("text/html"); // NOI18N
        jScrollPane2.setViewportView(proteinCoveragePane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JEditorPane proteinCoveragePane;
    // End of variables declaration//GEN-END:variables

    boolean isOriginalCoverage() {
        return isOriginalCcoverage;
    }

    void setOriginalCoverage() {
        if (originalProtein != null && !isOriginalCcoverage) {
            showProteinCoverage(originalProtein.getProteinSequence(), originalProtein, true);
        }
    }

    //to remove the formatting
    public int getTextSelectionStart() {
        return this.proteinCoveragePane.getSelectionStart() - (int) Math.floor(this.proteinCoveragePane.getSelectionStart() / 10);
    }

    public int getTextSelectionEnd() {
        return this.proteinCoveragePane.getSelectionEnd() - (int) Math.floor(this.proteinCoveragePane.getSelectionEnd() / 10);
    }

    void setInteractionCoverage(List<InteractionPartner> interactionPartnersForPDBName) {
        for (InteractionPartner aPartner : interactionPartnersForPDBName) {
            System.out.println(aPartner.getInteractorLocation() + " " + aPartner.getComplementLocation() + " " + aPartner.getInteractionType());
        }
    }
}
