package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.HydrophobicityMaps;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.FreeEnergyProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophobicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SecondaryStructureProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class ReferenceExperimentPanel extends javax.swing.JPanel {

    private int horizontalOffset = 115;
    private int verticalOffset = 50;
    private Protein protein;
    private DrawModeInterface peptideDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface proteinDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface secondaryDrawMode = new HydrophobicityProteinDrawMode();

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public ReferenceExperimentPanel() {
        initComponents();
    }

    public ReferenceExperimentPanel(Experiment project) {
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

        quantCheckBox.setForeground(new java.awt.Color(0, 255, 255));
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
                .addContainerGap(686, Short.MAX_VALUE))
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
        }
        if (protein != null) {
            try {
                proteinDrawMode.drawProteinAndPeptidesOfProtein(protein, g, horizontalOffset, verticalOffset, ProgramVariables.SCALE, ProgramVariables.VERTICALSIZE);
                //((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
                if (protein.getDomains().isEmpty()) {
                    ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein);
                }
                
                secondaryDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset +25, ProgramVariables.SCALE, ProgramVariables.VERTICALSIZE);
                //((DrawableProtein) protein).drawAminoAcidsOfSequence(horizontalOffset, verticalOffset + 25, g, gradientMap);
            } catch (UndrawableException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
    }
}
