package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.comparators.ComparePdbInfoByResolution;
import com.compomics.pepshell.controllers.objectcontrollers.ProteinController;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class JmolPanel extends javax.swing.JPanel {

    private FaultBarrier faultBarrier;
    private Protein presentedProtein;

    /**
     * Creates new form PDBPanel
     */
    public JmolPanel() {
        this.faultBarrier = FaultBarrier.getInstance();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        PDBFileComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pdbViewPanel1 = new com.compomics.pepshell.view.panels.PdbViewPanel();
        sequenceCoveragePanel = new com.compomics.pepshell.view.panels.SequenceCoveragePanel();
        PDBInfoText = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(500, 500));

        PDBFileComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2L05" }));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sequenceCoveragePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(PDBInfoText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sequenceCoveragePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PDBFileComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDBFileComboBoxActionPerformed
        if (PDBFileComboBox.getSelectedItem() != null && presentedProtein != null) {
            try {
                //if loading in pdb files becomes extraordinarily slow, this is probably the first place to look to speed it up, especially the loading into memory, then parsing sequence and then translating, all on the fly --> cache that stuff
                pdbViewPanel1.setPdbFile(PDBFileComboBox.getSelectedItem().toString());
                PDBInfoText.setText(ProgramVariables.STRUCTUREDATASOURCE.getPDBDataForPDBName(PDBFileComboBox.getSelectedItem().toString()));
                sequenceCoveragePanel.showProteinCoverage(
                        ProteinController.fromThreeLetterToOneLetterAminoAcids(PDBDAO.getSequenceFromPdbFile(PDBDAO.getPdbFileInMem(PDBFileComboBox.getSelectedItem().toString()))),
                        presentedProtein);
                sequenceCoveragePanel.setInteractionCoverage(ProgramVariables.STRUCTUREDATASOURCE.getInteractionPartnersForPDBName(PDBFileComboBox.getSelectedItem().toString()));
                for (PeptideGroup aGroup : presentedProtein.getPeptideGroupsForProtein()) {
                    pdbViewPanel1.executeScript("select residue " + aGroup.getShortestPeptide().getBeginningProteinMatch() + " " + aGroup.getShortestPeptide().getEndProteinMatch() + "; colour green");
                }
            } catch (IOException ioe) {
                if (PDBFileComboBox.getSelectedItem() != "no pdb accessions found for protein") {
                    JOptionPane.showMessageDialog(this, "the PDB file for accession: " + PDBFileComboBox.getSelectedItem().toString() + " could not be retrieved");
                    faultBarrier.handleException(ioe);
                }
            }
        }
    }//GEN-LAST:event_PDBFileComboBoxActionPerformed

    private void sequenceCoveragePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sequenceCoveragePanelMouseDragged
        // TODO add your handling code here:
        if (presentedProtein != null) {
            int start = ((SequenceCoveragePanel) evt.getComponent()).getTextSelectionStart();
            int stop = ((SequenceCoveragePanel) evt.getComponent()).getTextSelectionEnd();
            pdbViewPanel1.executeScript("select residue " + start + " " + stop + "; colour green");
            for (InteractionPartner aPartner : ProgramVariables.STRUCTUREDATASOURCE.getInteractionPartnersForRange(presentedProtein, start, stop)) {
                pdbViewPanel1.executeScript("select residue " + aPartner.getInteractorLocation() + "; colour red");
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
            faultBarrier.handleException(new FileNotFoundException());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PDBFileComboBox;
    private javax.swing.JLabel PDBInfoText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.compomics.pepshell.view.panels.PdbViewPanel pdbViewPanel1;
    private com.compomics.pepshell.view.panels.SequenceCoveragePanel sequenceCoveragePanel;
    // End of variables declaration//GEN-END:variables

    public void preparePDBPanelForProtein(Protein protein) throws ConversionException {
        try {
            PDBFileComboBox.setModel(new DefaultComboBoxModel(PDBDAO.getInstance().getPDBInfoForProtein(protein).toArray()));
        } catch (MalformedURLException ex) {
            faultBarrier.handleException(ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "there has been a problem trying to retrieve the pdb file accessions for this protein: " + protein.getProteinAccession());
            faultBarrier.handleException(ex);
        }

    }

    public void setPDBProtein(Protein protein) throws MalformedURLException, ConversionException, SQLException {
        if (protein != null) {
            presentedProtein = protein;
            sequenceCoveragePanel.showProteinCoverage(protein.getProteinSequence(), protein);
            jProgressBar1.setVisible(true);
            jProgressBar1.setIndeterminate(true);
            jProgressBar1.setString("fetching pdb files");
            //todo remove this and actually multithread it
            if (protein.getPdbFilesInfo().isEmpty()) {
                protein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(protein, new ComparePdbInfoByResolution()));
            }
            PDBFileComboBox.removeAllItems();
            if (protein.getPdbFilesInfo().isEmpty()) {
                PDBFileComboBox.addItem("no pdb accessions found for protein");
                PDBFileComboBox.addItem("some test accessions");
                PDBFileComboBox.addItem("1GZX");
                PDBFileComboBox.addItem("3Q4C");
                PDBFileComboBox.addItem("3C4C");
            } else {
                for (PdbInfo aPDBFileAccession : protein.getPdbFilesInfo()) {
                    PDBFileComboBox.addItem(aPDBFileAccession);
                }
            }
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setVisible(false);
        }
    }
}
