package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Davy
 */
public class SequenceCoveragePanel extends javax.swing.JPanel {

    int[] coverage;
    boolean isOriginalCcoverage = false;
    String originalProteinSequence;
    Iterator<PeptideGroup> originalPeptideGroupIterator;

    /**
     * Creates new form ProteinBarPanel
     */
    public SequenceCoveragePanel() {
        initComponents();
        this.setBackground(Color.white);
    }

    public void showProteinCoverage(String proteinSequence, Iterator<PeptideGroup> peptideGroup) {
        this.showProteinCoverage(proteinSequence, peptideGroup, false);
    }

    public void showProteinCoverage(String proteinSequence, Iterator<PeptideGroup> peptideGroups, boolean setAsDefaultCoverage) {
        isOriginalCcoverage = false;
        if (setAsDefaultCoverage) {
            originalProteinSequence = proteinSequence;
            originalPeptideGroupIterator = peptideGroups;
            isOriginalCcoverage = true;
        }
        List<Integer> peptideStarts = new ArrayList<Integer>();
        List<Integer> peptideEnds = new ArrayList<Integer>();
        coverage = new int[proteinSequence.length() + 1];
        Peptide peptide;
        while (peptideGroups.hasNext()) {
            PeptideGroup peptideGroup = peptideGroups.next();
            peptide = peptideGroup.getShortestPeptide();
            int tempPeptideStart = proteinSequence.indexOf(peptide.getSequence());
            int tempPeptideEnd = tempPeptideStart + peptide.getSequence().length();
            tempPeptideStart = tempPeptideStart + 1;

            for (int j = tempPeptideStart; j <= tempPeptideEnd; j++) {
                coverage[j]++;
            }
            peptideGroup.setAlignmentPositions(tempPeptideStart, tempPeptideEnd);
            peptideStarts.add(tempPeptideStart);
            peptideEnds.add(tempPeptideEnd);
        }
        formatProteinSequence(proteinCoveragePane, proteinSequence, peptideStarts, peptideEnds, coverage);
    }

    public static double formatProteinSequence(JEditorPane editorPane, String cleanSequence, List<Integer> selectedPeptideStart, List<Integer> selectedPeptideEnd, int[] coverage) {

        if (cleanSequence.length() != coverage.length - 1) {
            throw new IllegalArgumentException("The length of the coverage map has to be equal to the length of the sequence + 1!");
        }

        String sequenceTable = "", currentCellSequence = "";
        boolean selectedPeptide = false, coveredPeptide;
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

        List<Integer> referenceMarkers = new ArrayList<Integer>();

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

            if (previousAminoAcidWasSelected && !selectedPeptide) {
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
                } else {
                    if (i % 10 == 1) {
                        currentCellSequence += "<span style=\"color:#9C9C9C\">" + cleanSequence.charAt(i - 1);
                    } else {
                        if (previousAminoAcidWasCovered) {
                            currentCellSequence += "<span style=\"color:#9C9C9C\">" + cleanSequence.charAt(i - 1);
                        } else {
                            currentCellSequence += cleanSequence.charAt(i - 1);
                        }
                    }
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
        if (originalProteinSequence != null && originalPeptideGroupIterator != null && !isOriginalCcoverage) {
            showProteinCoverage(originalProteinSequence, originalPeptideGroupIterator, true);
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
