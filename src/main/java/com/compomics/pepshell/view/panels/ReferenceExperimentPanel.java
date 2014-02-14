package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.FreeEnergyProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophobicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SecondaryStructureProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.SolventAccessibleProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class ReferenceExperimentPanel extends javax.swing.JPanel {

    public static final String NO_PDB_FILES_FOUND = "no PDBs found";

    private Experiment referenceExperiment;

    /**
     * Creates new form ReferenceExperimentPanel
     */
    public ReferenceExperimentPanel() {
        initComponents();

        pdbSelectionComboBox.setEnabled(false);
        //this.addMouseListener(new popupMenuListener());
    }

    public ReferenceExperimentPanel(Experiment project) {
        this();
        projectNameLabel.setText(project.getExperimentName());
    }

    public void updateProtein(Protein protein) {
        pdbSelectionComboBox.removeAllItems();

        if (protein.getPdbFileNames().isEmpty()) {
            //retrieve the PDB info objects and add them to the protein
            protein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPDBInfoForProtein(protein));
        }
        //check after retrieval if any PDB info is available
        if (protein.getPdbFileNames().isEmpty()) {
            //add a default item
            pdbSelectionComboBox.addItem(NO_PDB_FILES_FOUND);
        } else {
            for (PdbInfo pdbInfo : protein.getPdbFileNames()) {
                pdbSelectionComboBox.addItem(pdbInfo);
            }
        }

        //update the draw panel
        referenceProteinDrawPanel.updateProtein(protein);
    }

    public void setReferenceExperiment(Experiment reference) {
        this.referenceExperiment = reference;
        projectNameLabel.setText(referenceExperiment.getExperimentName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        experimentPopupMenu = new javax.swing.JPopupMenu();
        changeNameOption = new javax.swing.JMenuItem();
        exportImageToPDFOption = new javax.swing.JMenuItem();
        optionsPanel = new javax.swing.JPanel();
        quantCheckBox = new javax.swing.JCheckBox();
        drawModeChooser = new javax.swing.JComboBox();
        pdbSelectionComboBox = new javax.swing.JComboBox();
        projectNameLabel = new javax.swing.JLabel();
        referenceProteinDrawPanel = new com.compomics.pepshell.view.panels.ReferenceProteinDrawPanel();

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
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 180));

        optionsPanel.setOpaque(false);

        quantCheckBox.setText("show quantitfied range");
        quantCheckBox.setOpaque(false);
        quantCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantCheckBoxActionPerformed(evt);
            }
        });

        drawModeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "hydrophobicity", "secondary structure", "free energy", "solvent accessibility" }));
        drawModeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawModeChooserActionPerformed(evt);
            }
        });

        pdbSelectionComboBox.setMinimumSize(new java.awt.Dimension(122, 20));
        pdbSelectionComboBox.setPreferredSize(new java.awt.Dimension(122, 20));
        pdbSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdbSelectionComboBoxActionPerformed(evt);
            }
        });

        projectNameLabel.setMaximumSize(new java.awt.Dimension(1600, 300));
        projectNameLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        projectNameLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pdbSelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantCheckBox)
                        .addGap(0, 579, Short.MAX_VALUE))
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantCheckBox)
                    .addComponent(pdbSelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout referenceProteinDrawPanelLayout = new javax.swing.GroupLayout(referenceProteinDrawPanel);
        referenceProteinDrawPanel.setLayout(referenceProteinDrawPanelLayout);
        referenceProteinDrawPanelLayout.setHorizontalGroup(
            referenceProteinDrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        referenceProteinDrawPanelLayout.setVerticalGroup(
            referenceProteinDrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(referenceProteinDrawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(referenceProteinDrawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void drawModeChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawModeChooserActionPerformed
        DrawModeInterface secondaryDrawMode;
        String pdbAccession = null;

        switch (drawModeChooser.getSelectedIndex()) {
            case 0:
                secondaryDrawMode = new HydrophobicityProteinDrawMode();
                break;
            case 1:
                secondaryDrawMode = new SecondaryStructureProteinDrawMode();
                break;
            case 2:
                secondaryDrawMode = new FreeEnergyProteinDrawMode();
                pdbSelectionComboBox.setEnabled(true);
                pdbAccession = (String) pdbSelectionComboBox.getItemAt(0);
                break;
            case 3:
                secondaryDrawMode = new SolventAccessibleProteinDrawMode();
                pdbSelectionComboBox.setEnabled(true);
                pdbAccession = (String) pdbSelectionComboBox.getItemAt(0);
                break;
            default:
                secondaryDrawMode = new HydrophobicityProteinDrawMode();
                break;
        }

        referenceProteinDrawPanel.updateSecondaryDrawMode(secondaryDrawMode, pdbAccession);
    }//GEN-LAST:event_drawModeChooserActionPerformed

    private void quantCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantCheckBoxActionPerformed
        DrawModeInterface peptideDrawMode;

        if (quantCheckBox.isSelected()) {
            peptideDrawMode = new QuantedPeptideDrawMode<QuantedPeptide>();
        } else {
            peptideDrawMode = new StandardPeptideProteinDrawMode();
        }

        referenceProteinDrawPanel.updatePeptideDrawMode(peptideDrawMode);
    }//GEN-LAST:event_quantCheckBoxActionPerformed

    private void changeNameOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeNameOptionActionPerformed
        // TODO add your handling code here:
        projectNameLabel.setText(JOptionPane.showInputDialog("change name of " + projectNameLabel.getText()));

    }//GEN-LAST:event_changeNameOptionActionPerformed

    private void pdbSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdbSelectionComboBoxActionPerformed
        String pdbAccession = (String) pdbSelectionComboBox.getSelectedItem();

        referenceProteinDrawPanel.updatePdbAccession(pdbAccession);
    }//GEN-LAST:event_pdbSelectionComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeNameOption;
    private javax.swing.JComboBox drawModeChooser;
    private javax.swing.JPopupMenu experimentPopupMenu;
    private javax.swing.JMenuItem exportImageToPDFOption;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JComboBox pdbSelectionComboBox;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JCheckBox quantCheckBox;
    private com.compomics.pepshell.view.panels.ReferenceProteinDrawPanel referenceProteinDrawPanel;
    // End of variables declaration//GEN-END:variables

}
