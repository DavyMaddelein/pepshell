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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class ExperimentPanel extends javax.swing.JPanel {

    private int horizontalOffset = this.getX() + 15;
    private int verticalOffset = 25;
    private Protein protein;
    private DrawModeInterface peptideDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface proteinDrawMode = new StandardPeptideProteinDrawMode();
    private Experiment experiment;
    private String experimentName;
    private boolean nameChanged = false;

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public ExperimentPanel() {
        initComponents();
        this.addMouseListener(new popupMenuListener());
    }

    public ExperimentPanel(Experiment experiment) {
        this();
        this.experiment = experiment;
        experimentName = experiment.getExperimentName();
        projectNameLabel.setText(experiment.getExperimentName());
    }

    public void setProtein(Protein protein) {
        try {
            this.protein = experiment.getProteins().get(experiment.getProteins().indexOf(protein));
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

        experimentPopupMenu = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        projectNameLabel = new javax.swing.JLabel();

        jMenuItem1.setText("change name of selected experiment");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        experimentPopupMenu.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        experimentPopupMenu.add(jMenuItem2);

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 65));
        setMinimumSize(new java.awt.Dimension(1000, 65));
        setPreferredSize(new java.awt.Dimension(1000, 65));
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
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        //replace with a hashset and call contains
        if (protein != null) {
            Iterator<PeptideGroup> peptideGroupIter = protein.getPeptideGroupsForProtein().iterator();
            while (peptideGroupIter.hasNext()) {
                PeptideGroup peptideGroup = peptideGroupIter.next();
                if (evt.getX() >= (int) Math.ceil((double) horizontalOffset + peptideGroup.getStartingAlignmentPosition() * ProgramVariables.SCALE) && evt.getX() <= (int) Math.ceil((double) horizontalOffset + peptideGroup.getEndAlignmentPosition() * ProgramVariables.SCALE)) {
                    ((ProteinDetailPanel) this.getParent().getParent().getParent().getParent()).setSequenceCoverage(protein.getProteinSequence(), peptideGroup);
                }
            }
        }
    }//GEN-LAST:event_formMouseMoved

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        projectNameLabel.setText(JOptionPane.showInputDialog("change experiment name to " + projectNameLabel.getText()));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu experimentPopupMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JLabel projectNameLabel;
    // End of variables declaration//GEN-END:variables
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (protein != null) {
            if (!nameChanged) {
                projectNameLabel.setText(experimentName);
            }
            if (protein.getProteinSequence().isEmpty()) {
                try {
                    protein.setSequence(WebDAO.fetchSequence(protein.getProteinAccession()));
                } catch (Exception e) {
                }
            }
            try {
                int horizontalBarSize = (int) Math.ceil(protein.getProteinSequence().length() * ProgramVariables.SCALE);
                proteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset + 5,horizontalBarSize, ProgramVariables.VERTICALSIZE); //((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
            } catch (UndrawableException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }

            //peptideDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, proteinBarSize, proteinBarHeight);
            Iterator<PeptideGroup> peptideGroups = protein.getPeptideGroupsForProtein().iterator();
            while (peptideGroups.hasNext()) {
                PeptideGroup aPeptideGroup = peptideGroups.next();
                try {
                    peptideDrawMode.drawPeptide(aPeptideGroup.getShortestPeptide(), g, horizontalOffset, verticalOffset + 5, ProgramVariables.VERTICALSIZE);
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

    private class popupMenuListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                experimentPopupMenu.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }

    }
}
