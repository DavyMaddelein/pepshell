package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.HydrophobicityMaps;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.drawable.DrawableProtein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Peptides.StandardPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.DomainProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophilicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SecondaryStructureProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;

/**
 *
 * @author Davy
 */
public class PeptidesProteinsOverlapPanel extends javax.swing.JPanel {

    private int horizontalOffset = 115;
    private int verticalOffset = 50;
    private int proteinBarSize = 1000;
    private int proteinBarHeight = 15;
    private Protein protein;
    private StandardPeptideProteinDrawMode secondaryDrawMode = new StandardPeptideDrawMode();
    
    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public PeptidesProteinsOverlapPanel() {
        initComponents();
    }

    public PeptidesProteinsOverlapPanel(Experiment project) {
        initComponents();
        projectNameLabel.setText(project.getExperimentName());
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public String getProjectNameForPanel() {
        return projectNameLabel.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectNameLabel = new javax.swing.JLabel();
        drawModeChooser = new javax.swing.JComboBox();
        quantCheckBox = new javax.swing.JCheckBox();

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

        drawModeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "hydrophobicity", "secondary structure" }));
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
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantCheckBox)))
                .addContainerGap(691, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantCheckBox))
                .addGap(6, 6, 6)
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
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
                secondaryDrawMode = new HydrophilicityProteinDrawMode();
                break;
            case 1:
                secondaryDrawMode = new SecondaryStructureProteinDrawMode();
                break;
            default:
                secondaryDrawMode = new StandardPeptideProteinDrawMode();
                break;
        }
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_drawModeChooserActionPerformed

    private void quantCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantCheckBoxActionPerformed
        // TODO add your handling code here:
        if (quantCheckBox.isSelected()) {
            secondaryDrawMode = new QuantedPeptideDrawMode();
        } else if (!quantCheckBox.isSelected()) {
            secondaryDrawMode = new StandardPeptideProteinDrawMode();
        }
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_quantCheckBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox drawModeChooser;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JCheckBox quantCheckBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!projectNameLabel.getText().isEmpty()) {
            horizontalOffset = this.getX() + projectNameLabel.getX() + projectNameLabel.getWidth() + 25;
            verticalOffset = this.getY() + 65;
            proteinBarSize = this.getWidth() - 10;
        }
        if (protein != null) {
            if (protein instanceof DrawableProtein) {
                try {
                    ((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
                    ((DrawableProtein) protein).drawDomains(horizontalOffset, verticalOffset + 25, g);
                    ((DrawableProtein) protein).drawAllPeptideGroups(horizontalOffset, verticalOffset + 25, g);
                    ((DrawableProtein) protein).drawAminoAcidsOfSequence(horizontalOffset, verticalOffset + 25, g, HydrophobicityMaps.hydrophobicityMapPh7);
                } catch (UndrawableException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            }
            //proteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, proteinBarSize, proteinBarHeight);
//            domainProteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset - 25, proteinBarSize, proteinBarHeight);
            secondaryDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset + 25, proteinBarSize, proteinBarHeight);
        }
    }
}
