package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.comparators.ComparePdbInfoByResolution;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.DomainProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.FreeEnergyProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophobicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SecondaryStructureProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SolventAccessibleProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class ReferenceExperimentPanel extends javax.swing.JPanel {

    private int horizontalOffset = this.getX() + 15;
    private int verticalOffset = 50;
    private Protein protein;
    private DrawModeInterface peptideDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface proteinDrawMode = new StandardPeptideProteinDrawMode();
    private DrawModeInterface secondaryDrawMode = new HydrophobicityProteinDrawMode();
    private DrawModeInterface domainDrawMode = new DomainProteinDrawMode();
    private Experiment referenceExperiment;

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public ReferenceExperimentPanel() {
        initComponents();
        pdbSelectionComboBox.setVisible(false);
        this.addMouseListener(new popupMenuListener());
    }

    public ReferenceExperimentPanel(Experiment project) {
        this();
        projectNameLabel.setText(project.getExperimentName());
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
        if (pdbSelectionComboBox.isVisible()) {
            if (protein.getPdbFilesInfo().isEmpty()) {
                protein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(protein, new ComparePdbInfoByResolution()));
            }
            for (PdbInfo info : protein.getPdbFilesInfo()) {
                pdbSelectionComboBox.addItem(info);
            }
        }
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
        pdbSelectionComboBox = new javax.swing.JComboBox();

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

        drawModeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "hydrophobicity", "secondary structure", "free energy", "solvent accessibility" }));
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

        pdbSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pdbSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdbSelectionComboBoxActionPerformed(evt);
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
                        .addComponent(pdbSelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantCheckBox)
                        .addGap(0, 627, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantCheckBox)
                    .addComponent(pdbSelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
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
            case 2:
                secondaryDrawMode = new FreeEnergyProteinDrawMode();
                pdbSelectionComboBox.setVisible(true);
                break;
            case 3:
                secondaryDrawMode = new SolventAccessibleProteinDrawMode();
                pdbSelectionComboBox.setVisible(true);
                break;
            default:
                secondaryDrawMode = new HydrophobicityProteinDrawMode();
                break;
        }
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_drawModeChooserActionPerformed

    private void quantCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantCheckBoxActionPerformed
        // TODO add your handling code here:
        if (quantCheckBox.isSelected()) {
            peptideDrawMode = new QuantedPeptideDrawMode<QuantedPeptide>();
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

    private void pdbSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdbSelectionComboBoxActionPerformed
        // TODO add your handling code here:

        //(String) pdbSelectionComboBox.getSelectedItem();
    }//GEN-LAST:event_pdbSelectionComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeNameOption;
    private javax.swing.JComboBox drawModeChooser;
    private javax.swing.JPopupMenu experimentPopupMenu;
    private javax.swing.JMenuItem exportImageToPDFOption;
    private javax.swing.JComboBox pdbSelectionComboBox;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JCheckBox quantCheckBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (protein != null) {
            try {
                verticalOffset = this.projectNameLabel.getY() + 20;
                int scaledHorizontalBarSize = (int) Math.ceil(protein.getProteinSequence().length() * ProgramVariables.SCALE);

                proteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset + 25, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                for (PeptideGroup aGroup : protein.getPeptideGroupsForProtein()) {
                    peptideDrawMode.drawPeptide(aGroup.getShortestPeptide(), g, horizontalOffset, verticalOffset + 25, ProgramVariables.VERTICALSIZE);
                }

                secondaryDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset + 50, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                if (secondaryDrawMode instanceof GradientDrawModeInterface) {
                    ((GradientDrawModeInterface) secondaryDrawMode).drawColorLegend(horizontalOffset + scaledHorizontalBarSize + 15, verticalOffset + 50, g);
                }
                try {
                    if (protein.getDomains().isEmpty()) {
                        ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein);
                    }
                    domainDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                } catch (DataRetrievalException e) {

                }
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
