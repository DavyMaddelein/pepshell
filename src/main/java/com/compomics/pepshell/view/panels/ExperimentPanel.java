package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.WebDAO;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;
import java.util.Iterator;

/**
 *
 * @author Davy
 */
public class ExperimentPanel extends javax.swing.JPanel {

    private int horizontalOffset = 115;
    private int verticalOffset = 20;
    private Protein protein;
    private DrawModeInterface peptideDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface proteinDrawMode = new StandardPeptideProteinDrawMode();
    private Experiment experiment;
    private String experimentName;

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public ExperimentPanel() {
        initComponents();
    }

    public ExperimentPanel(Experiment experiment) {
        initComponents();
        this.experiment = experiment;
        experimentName = experiment.getExperimentName();
        projectNameLabel.setText(experiment.getExperimentName());
    }

    public void setProtein(Protein protein) {
        try {
            this.protein = experiment.get(protein);
        } catch (IndexOutOfBoundsException e) {
            this.protein = null;
        }
        this.revalidate();
        this.repaint();
    }

    public void setXoffset(int anXoffset) {
        horizontalOffset = anXoffset;
    }

    public String getProjectNameForPanel() {
        return projectNameLabel.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectNameLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 45));
        setMinimumSize(new java.awt.Dimension(1000, 45));
        setPreferredSize(new java.awt.Dimension(1000, 45));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        projectNameLabel.setMaximumSize(new java.awt.Dimension(1600, 300));
        projectNameLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        projectNameLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (protein != null) {
            Iterator<PeptideGroup> peptideGroupIter = protein.getPeptideGroupsForProtein();
            while (peptideGroupIter.hasNext()) {
                PeptideGroup peptideGroup = peptideGroupIter.next();
                if (evt.getX() >= (int) Math.ceil((double) horizontalOffset + peptideGroup.getStartingAlignmentPosition() / ProgramVariables.SCALE) && evt.getX() <= (int) Math.ceil((double) horizontalOffset + peptideGroup.getEndAlignmentPosition() / ProgramVariables.SCALE)) {
                    ((InfoPanel) this.getParent().getParent().getParent().getParent()).setSequenceCoverage(protein.getProteinSequence(), peptideGroup);
                }
            }
        }
    }//GEN-LAST:event_formMouseMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel projectNameLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (protein != null) {
            projectNameLabel.setText(experimentName);
            if (protein.getProteinSequence().isEmpty()) {
                try {
                    protein.setSequence(WebDAO.fetchSequence(protein.getProteinAccession()));
                } catch (Exception e) {
                }
            }
            try {
                proteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, ProgramVariables.SCALE, ProgramVariables.VERTICALSIZE); //((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
            } catch (UndrawableException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }

            //peptideDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, proteinBarSize, proteinBarHeight);
            Iterator<PeptideGroup> peptideGroups = protein.getPeptideGroupsForProtein();
            while (peptideGroups.hasNext()) {
                PeptideGroup aPeptideGroup = peptideGroups.next();
                try {
                    peptideDrawMode.drawPeptide(aPeptideGroup.getShortestPeptide(), g, horizontalOffset, verticalOffset, (int) Math.ceil(ProgramVariables.SCALE), ProgramVariables.VERTICALSIZE);
                } catch (UndrawableException ex) {
                }
            }
        } else {
            projectNameLabel.setText(experimentName + ": protein not found");
        }
    }

    void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    void setYOffset(double height) {
        verticalOffset = (int) ((int) verticalOffset + height);
    }
}
