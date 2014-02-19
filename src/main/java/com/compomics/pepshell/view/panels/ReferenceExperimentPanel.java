package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.LinkDb;
import com.compomics.pepshell.controllers.comparators.ComparePdbInfoByResolution;
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
import javax.swing.BorderFactory;

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

        referenceProteinScrollPane.getViewport().setOpaque(false);
        referenceProteinScrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        pdbSelectionComboBox.setEnabled(false);
    }

    public ReferenceExperimentPanel(Experiment project) {
        this();
        projectNameLabel.setText(project.getExperimentName());
    }

    public void updateProtein(Protein protein) {
        pdbSelectionComboBox.removeAllItems();

        if (protein.getPdbFilesInfo().isEmpty()) {
            //retrieve the PDB info objects and add them to the protein
            protein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(protein, new ComparePdbInfoByResolution()));
        }
        //check after retrieval if any PDB info is available
        if (protein.getPdbFilesInfo().isEmpty()) {
            //add a default item
            pdbSelectionComboBox.addItem(NO_PDB_FILES_FOUND);
        } else {
            for (PdbInfo pdbInfo : protein.getPdbFilesInfo()) {
                pdbSelectionComboBox.addItem(pdbInfo);
            }
        }

        //set selected item
        pdbSelectionComboBox.setSelectedIndex(0);

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
        referenceProteinScrollPane = new javax.swing.JScrollPane();
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
        setName(""); // NOI18N
        setOpaque(false);
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
        drawModeChooser.setPreferredSize(new java.awt.Dimension(122, 25));
        drawModeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawModeChooserActionPerformed(evt);
            }
        });

        pdbSelectionComboBox.setMinimumSize(new java.awt.Dimension(122, 25));
        pdbSelectionComboBox.setPreferredSize(new java.awt.Dimension(122, 25));
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
                        .addComponent(drawModeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pdbSelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantCheckBox)
                        .addGap(0, 515, Short.MAX_VALUE))
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        referenceProteinScrollPane.setBorder(null);
        referenceProteinScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        referenceProteinScrollPane.setOpaque(false);

        javax.swing.GroupLayout referenceProteinDrawPanelLayout = new javax.swing.GroupLayout(referenceProteinDrawPanel);
        referenceProteinDrawPanel.setLayout(referenceProteinDrawPanelLayout);
        referenceProteinDrawPanelLayout.setHorizontalGroup(
            referenceProteinDrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        referenceProteinDrawPanelLayout.setVerticalGroup(
            referenceProteinDrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        referenceProteinScrollPane.setViewportView(referenceProteinDrawPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(referenceProteinScrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(referenceProteinScrollPane))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void drawModeChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawModeChooserActionPerformed
        if (referenceProteinDrawPanel.getProtein() != null) {
            DrawModeInterface secondaryDrawMode;
            String pdbAccession = null;

            //disable PDB selection combobox
            pdbSelectionComboBox.setEnabled(false);

            switch (drawModeChooser.getSelectedIndex()) {
                case 0:
                    secondaryDrawMode = new HydrophobicityProteinDrawMode();
                    break;
                case 1:
                    secondaryDrawMode = new SecondaryStructureProteinDrawMode();
                    if (ProgramVariables.STRUCTUREDATASOURCE instanceof LinkDb) {
                        pdbSelectionComboBox.setEnabled(true);
                        pdbAccession = pdbSelectionComboBox.getSelectedItem().toString();
                    }
                    break;
                case 2:
                    secondaryDrawMode = new FreeEnergyProteinDrawMode();
                    pdbSelectionComboBox.setEnabled(true);
                    pdbAccession = pdbSelectionComboBox.getSelectedItem().toString();
                    break;
                case 3:
                    secondaryDrawMode = new SolventAccessibleProteinDrawMode();
                    pdbSelectionComboBox.setEnabled(true);
                    pdbAccession = pdbSelectionComboBox.getSelectedItem().toString();
                    break;
                default:
                    secondaryDrawMode = new HydrophobicityProteinDrawMode();
                    break;
            }

            referenceProteinDrawPanel.updateSecondaryDrawMode(secondaryDrawMode, pdbAccession);
        } else {
           JOptionPane.showMessageDialog(this.getParent(), "Please select a protein from the list.", "Draw mode selection", JOptionPane.INFORMATION_MESSAGE); 
        }
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
        if (pdbSelectionComboBox.getSelectedItem() != null) {
            String pdbAccession = pdbSelectionComboBox.getSelectedItem().toString();

            referenceProteinDrawPanel.updatePdbAccession(pdbAccession);
        }
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
    private javax.swing.JScrollPane referenceProteinScrollPane;
    // End of variables declaration//GEN-END:variables

}
