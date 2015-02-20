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

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.objectcontrollers.ProteinController;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Davy Maddelein
 */
public class JmolPanel extends javax.swing.JPanel {

    private PepshellProtein presentedPepshellProtein;

    /**
     * Creates new form PDBPanel
     */
    public JmolPanel() {
        initComponents();
        this.jProgressBar1.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        PDBFileComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pdbViewPanel1 = new com.compomics.pepshell.view.dataviewing.PdbViewPanel();
        sequenceCoveragePanel = new com.compomics.pepshell.view.dataviewing.SequenceCoveragePanel();
        PDBInfoText = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        showPDBSequenceCheckBox = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(500, 500));

        PDBFileComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"2L05"}));
        PDBFileComboBox.setMaximumSize(new java.awt.Dimension(90, 25));
        PDBFileComboBox.setMinimumSize(new java.awt.Dimension(90, 25));
        PDBFileComboBox.setPreferredSize(new java.awt.Dimension(90, 25));
        PDBFileComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDBFileComboBoxActionPerformed(evt);
            }
        });

        jButton1.setText("save PDB...");
        jButton1.setMaximumSize(new java.awt.Dimension(80, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(80, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout pdbViewPanel1Layout = new javax.swing.GroupLayout(pdbViewPanel1);
        pdbViewPanel1.setLayout(pdbViewPanel1Layout);
        pdbViewPanel1Layout.setHorizontalGroup(
                pdbViewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 696, Short.MAX_VALUE)
        );
        pdbViewPanel1Layout.setVerticalGroup(
                pdbViewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 506, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(pdbViewPanel1);

        sequenceCoveragePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("sequence coverage"));
        sequenceCoveragePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sequenceCoveragePanelMouseDragged(evt);
            }
        });

        jButton2.setText("save image");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        showPDBSequenceCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        showPDBSequenceCheckBox.setText("show pdb sequence");
        showPDBSequenceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPDBSequenceCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sequenceCoveragePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(PDBInfoText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(showPDBSequenceCheckBox)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jButton2))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PDBInfoText, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(1, 1, 1)
                                .addComponent(sequenceCoveragePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showPDBSequenceCheckBox)
                                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PDBFileComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDBFileComboBoxActionPerformed
        if (PDBFileComboBox.getSelectedItem() != null && presentedPepshellProtein != null) {
            try {
                //if loading in pdb files becomes extraordinarily slow, this is probably the first place to look to speed it up, especially the loading into memory, then parsing sequence and then translating, all on the fly --> cache that stuff
                pdbViewPanel1.setPdbFile(PDBFileComboBox.getSelectedItem().toString());
                PDBInfoText.setText(ProgramVariables.STRUCTUREDATASOURCE.getPDBDataForPDBName(PDBFileComboBox.getSelectedItem().toString()));
                //temp
                try {
                    if (showPDBSequenceCheckBox.isSelected()) {
                        sequenceCoveragePanel.showProteinCoverage(ProteinController.fromThreeLetterToOneLetterAminoAcids(PDBDAO.getSequenceFromPdbFile(PDBDAO.getPdbFileInMem(PDBFileComboBox.getSelectedItem().toString()))), presentedPepshellProtein);
                    } else {
                        sequenceCoveragePanel.showProteinCoverage(presentedPepshellProtein.getProteinSequence(), presentedPepshellProtein);
                    }
                } catch (Exception e) {
                    FaultBarrier.getInstance().handleException(e);
                }
                sequenceCoveragePanel.setInteractionCoverage(ProgramVariables.STRUCTUREDATASOURCE.getInteractionPartnersForPDBName(PDBFileComboBox.getSelectedItem().toString()));
                for (PeptideGroup aGroup : presentedPepshellProtein.getPeptideGroups()) {
                    pdbViewPanel1.executeScript("select " + aGroup.getShortestPeptide().getBeginningProteinMatch() + "-" + aGroup.getShortestPeptide().getEndProteinMatch() + "; colour green");
                }
            } catch (IOException ioe) {
                if (PDBFileComboBox.getSelectedItem() != "no pdb accessions found for protein") {
                    JOptionPane.showMessageDialog(this, "the PDB file for accession: " + PDBFileComboBox.getSelectedItem().toString() + " could not be retrieved");
                    FaultBarrier.getInstance().handleException(ioe);
                }
            }

        }
    }//GEN-LAST:event_PDBFileComboBoxActionPerformed

    private void sequenceCoveragePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sequenceCoveragePanelMouseDragged
        // TODO add your handling code here:
        if (presentedPepshellProtein != null) {
            int start = ((SequenceCoveragePanel) evt.getComponent()).getTextSelectionStart();
            int stop = ((SequenceCoveragePanel) evt.getComponent()).getTextSelectionEnd();
            pdbViewPanel1.executeScript("select " + start + "-" + stop + "; colour red");
            for (InteractionPartner aPartner : (List<InteractionPartner>) ProgramVariables.STRUCTUREDATASOURCE.getInteractionPartnersForRange(presentedPepshellProtein, start, stop)) {
                pdbViewPanel1.executeScript("select " + aPartner.getInteractorLocation() + "; colour red");
            }
        }
    }//GEN-LAST:event_sequenceCoveragePanelMouseDragged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(this);
        chooser.setVisible(true);
        if (chooser.getSelectedFile() != null) {
            pdbViewPanel1.getViewer().evalString("write Image 1920 1080 PNG " + chooser.getSelectedFile().getAbsolutePath() + PDBFileComboBox.getSelectedItem().toString() + ".png");
        } else {
            FaultBarrier.getInstance().handleException(new FileNotFoundException());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void showPDBSequenceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPDBSequenceCheckBoxActionPerformed
        // TODO add your handling code here:
        sequenceCoveragePanel.repaint();
    }//GEN-LAST:event_showPDBSequenceCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PDBFileComboBox;
    private javax.swing.JLabel PDBInfoText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.compomics.pepshell.view.dataviewing.PdbViewPanel pdbViewPanel1;
    private com.compomics.pepshell.view.dataviewing.SequenceCoveragePanel sequenceCoveragePanel;
    private javax.swing.JCheckBox showPDBSequenceCheckBox;
    // End of variables declaration//GEN-END:variables

    public void preparePDBPanelForProtein(PepshellProtein pepshellProtein) throws ConversionException {
        try {
            PDBFileComboBox.setModel(new DefaultComboBoxModel(PDBDAO.getInstance().getPDBInfoForProtein(pepshellProtein).toArray()));
        } catch (MalformedURLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "there has been a problem trying to retrieve the pdb file accessions for this pepshellProtein: " + pepshellProtein.getVisibleAccession());
            FaultBarrier.getInstance().handleException(ex);
        }

    }

    public void setPDBProtein(PepshellProtein pepshellProtein) throws MalformedURLException, ConversionException, SQLException {
        if (pepshellProtein != null) {
            presentedPepshellProtein = pepshellProtein;
            sequenceCoveragePanel.showProteinCoverage(pepshellProtein.getProteinSequence(), pepshellProtein);
            jProgressBar1.setVisible(true);
            jProgressBar1.setIndeterminate(true);
            jProgressBar1.setString("fetching pdb files");
            //todo remove this and actually multithread it
            if (pepshellProtein.getPdbFilesInfo().isEmpty()) {
                pepshellProtein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(pepshellProtein));
            }
            PDBFileComboBox.removeAllItems();
            if (pepshellProtein.getPdbFilesInfo().isEmpty()) {
                PDBFileComboBox.addItem("no pdb accessions found for pepshellProtein");
                PDBFileComboBox.addItem("some test accessions");
                PDBFileComboBox.addItem("1GZX");
                PDBFileComboBox.addItem("3Q4C");
                PDBFileComboBox.addItem("3C4C");
            } else {
                for (PdbInfo aPDBFileAccession : pepshellProtein.getPdbFilesInfo()) {
                    PDBFileComboBox.addItem(aPDBFileAccession);
                }
            }
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setVisible(false);
        }
    }
}
