package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.FreeEnergyProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophobicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SecondaryStructureProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class ReferenceExperimentPanel extends javax.swing.JPanel {

    private int horizontalOffset = this.getX() + 15;
    private int verticalOffset = 50;
    private Protein<PeptideGroup<Peptide>> protein;
    private DrawModeInterface peptideDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface proteinDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface secondaryDrawMode = new HydrophobicityProteinDrawMode();
    private boolean nameChanged = false;
    private Experiment referenceExperiment;

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public ReferenceExperimentPanel() {
        initComponents();
        this.addMouseListener(new popupMenuListener());
    }

    public ReferenceExperimentPanel(Experiment project) {
        this();
        projectNameLabel.setText(project.getExperimentName());
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public void setReferenceExperiment(Experiment reference) {
        this.referenceExperiment = reference;
        projectNameLabel.setText(referenceExperiment.getExperimentName());
    }

    public String getProjectNameForPanel() {
        return projectNameLabel.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        experimentPopupMenu = new javax.swing.JPopupMenu();
        changeNameOption = new javax.swing.JMenuItem();
        exportImageToPDFOption = new javax.swing.JMenuItem();
        projectNameLabel = new javax.swing.JLabel();
        drawModeChooser = new javax.swing.JComboBox();
        quantCheckBox = new javax.swing.JCheckBox();

        changeNameOption.setText("change name of the selected experiment");
        changeNameOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeNameOptionActionPerformed(evt);
            }
        });
        experimentPopupMenu.add(changeNameOption);

        exportImageToPDFOption.setText("export this image to PDF");
        experimentPopupMenu.add(exportImageToPDFOption);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setName("[1000, 80]"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 80));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        projectNameLabel.setMaximumSize(new java.awt.Dimension(1600, 300));
        projectNameLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        projectNameLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        drawModeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "hydrophobicity", "secondary structure", "free residue energy" }));
        drawModeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawModeChooserActionPerformed(evt);
            }
        });

        quantCheckBox.setText("show quantitfied range");
        quantCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantCheckBox)
                        .addGap(0, 683, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantCheckBox))
                .addGap(6, 6, 6)
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        //todo actually take a look at this
        /*if (evt.getY() >= verticalOffset + 5 && evt.getY() <= verticalOffset + proteinBarSize) {
         System.out.println(evt.getY());
         System.out.println(evt.getX());
         for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
         if (evt.getX() >= horizontalOffset + peptideGroup.getStartingAlignmentPosition() && evt.getX() <= horizontalOffset + peptideGroup.getEndAlignmentPosition()) {
         ProteinInfoFrame frame = new ProteinInfoFrame(protein, peptideGroup);
         }
         }
         }
         */
    }//GEN-LAST:event_formMouseMoved

    private void drawModeChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawModeChooserActionPerformed
        // TODO add your handling code here:
        switch (drawModeChooser.getSelectedIndex()) {
            case 0:
                secondaryDrawMode = new HydrophobicityProteinDrawMode();
                break;
            case 1:
                secondaryDrawMode = new SecondaryStructureProteinDrawMode();
                break;
            default:
                secondaryDrawMode = new FreeEnergyProteinDrawMode();
                break;
        }
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_drawModeChooserActionPerformed

    private void quantCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantCheckBoxActionPerformed
        // TODO add your handling code here:
        if (quantCheckBox.isSelected()) {
            peptideDrawMode = new QuantedPeptideDrawMode();
            //((DrawableProtein) protein).drawAllPeptideGroups(horizontalOffset, verticalOffset + 25, g);
        } else {
            peptideDrawMode = new StandardPeptideProteinDrawMode();
        }
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_quantCheckBoxActionPerformed

    private void changeNameOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeNameOptionActionPerformed
        // TODO add your handling code here:
        projectNameLabel.setText(JOptionPane.showInputDialog("change name of " + projectNameLabel.getText()));

    }//GEN-LAST:event_changeNameOptionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeNameOption;
    private javax.swing.JComboBox drawModeChooser;
    private javax.swing.JPopupMenu experimentPopupMenu;
    private javax.swing.JMenuItem exportImageToPDFOption;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JCheckBox quantCheckBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (protein != null) {
            try {
                int scaledHorizontalBarSize = (int) Math.ceil(protein.getProteinSequence().length() * ProgramVariables.SCALE);

                proteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                for (PeptideGroup<Peptide> aGroup : protein) {
                    peptideDrawMode.drawPeptide(aGroup.getShortestPeptide(), g, horizontalOffset, verticalOffset, ProgramVariables.VERTICALSIZE);
                }
//((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
                if (protein.getDomains().isEmpty()) {
                    ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein);
                }
                secondaryDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset + 25, (int) Math.ceil(protein.getProteinSequence().length() * ProgramVariables.SCALE), ProgramVariables.VERTICALSIZE);
                //((DrawableProtein) protein).drawAminoAcidsOfSequence(horizontalOffset, verticalOffset + 25, g, gradientMap);
            } catch (UndrawableException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
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
