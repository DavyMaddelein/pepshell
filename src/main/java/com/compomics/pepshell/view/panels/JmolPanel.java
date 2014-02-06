package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.objectcontrollers.ProteinController;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
        sequenceCoveragePanel1 = new com.compomics.pepshell.view.panels.SequenceCoveragePanel();
        PDBInfoText = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        PDBFileComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2L05" }));
        PDBFileComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDBFileComboBoxActionPerformed(evt);
            }
        });

        jButton1.setText("Save PDB file ...");

        javax.swing.GroupLayout pdbViewPanel1Layout = new javax.swing.GroupLayout(pdbViewPanel1);
        pdbViewPanel1.setLayout(pdbViewPanel1Layout);
        pdbViewPanel1Layout.setHorizontalGroup(
            pdbViewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );
        pdbViewPanel1Layout.setVerticalGroup(
            pdbViewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(pdbViewPanel1);

        sequenceCoveragePanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sequenceCoveragePanel1MouseDragged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sequenceCoveragePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(PDBInfoText))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(4, 4, 4)
                .addComponent(PDBInfoText)
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sequenceCoveragePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PDBFileComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDBFileComboBoxActionPerformed
        try {
            pdbViewPanel1.setPdbFile((String) PDBFileComboBox.getSelectedItem());
            PDBInfoText.setText(ProgramVariables.STRUCTUREDATASOURCE.getPDBDataForPDBName((String) PDBFileComboBox.getSelectedItem()));
            sequenceCoveragePanel1.showProteinCoverage(
                    ProteinController.fromThreeLetterToOneLetterAminoAcids(PDBDAO.getSequenceFromPdbFile(PDBDAO.getPdbFileInMem((String) PDBFileComboBox.getSelectedItem()))),
                    new ArrayList<PeptideGroup>().iterator());
            sequenceCoveragePanel1.setInteractionCoverage(ProgramVariables.STRUCTUREDATASOURCE.getInteractionPartnersForPDBName((String) PDBFileComboBox.getSelectedItem()));
        } catch (IOException ioe) {
            if (PDBFileComboBox.getSelectedItem() != "no pdb accessions found for protein") {
                JOptionPane.showMessageDialog(this, "the PDB file for accession: " + (String) PDBFileComboBox.getSelectedItem() + " could not be retrieved");
                faultBarrier.handleException(ioe);
            }
        }
    }//GEN-LAST:event_PDBFileComboBoxActionPerformed

    private void sequenceCoveragePanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sequenceCoveragePanel1MouseDragged
        // TODO add your handling code here:
        if (presentedProtein != null) {
            int start = ((SequenceCoveragePanel) evt.getComponent()).getTextSelectionStart();
            int stop = ((SequenceCoveragePanel) evt.getComponent()).getTextSelectionEnd();
            for (Iterator<InteractionPartner> it = ProgramVariables.STRUCTUREDATASOURCE.getInteractionPartnersForRange(presentedProtein, start, stop).iterator(); it.hasNext();) {
                InteractionPartner aPartner = it.next();
            }
        }
    }//GEN-LAST:event_sequenceCoveragePanel1MouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PDBFileComboBox;
    private javax.swing.JLabel PDBInfoText;
    private javax.swing.JButton jButton1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.compomics.pepshell.view.panels.PdbViewPanel pdbViewPanel1;
    private com.compomics.pepshell.view.panels.SequenceCoveragePanel sequenceCoveragePanel1;
    // End of variables declaration//GEN-END:variables

    public void preparePDBPanelForProtein(Protein protein) throws ConversionException {
        try {
            PDBFileComboBox.setModel(new DefaultComboBoxModel(PDBDAO.getPDBFileAccessionsForProtein(protein).toArray()));
        } catch (MalformedURLException ex) {
            faultBarrier.handleException(ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "there has been a problem trying to retrieve the pdb file accessions for this protein: " + protein.getProteinAccession());
            faultBarrier.handleException(ex);
        }

    }

    public void setPDBProtein(final Protein protein) throws MalformedURLException, ConversionException, SQLException {
        final Set<String> pdbAccessions = new HashSet<String>();
        sequenceCoveragePanel1.showProteinCoverage(protein.getProteinSequence(), protein.getPeptideGroupsForProtein().iterator());
        jProgressBar1.setIndeterminate(true);
        jProgressBar1.setString("fetching pdb files");
        new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        pdbAccessions.addAll(PDBDAO.getPDBFileAccessionsForProtein(protein));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(JmolPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ConversionException ex) {
                        Logger.getLogger(JmolPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ioe) {
                    try {
                        // try offline repository if any
                        pdbAccessions.addAll(PDBDAO.getPdbFileForProteinFromRepository(protein));
                    } catch (SQLException ex) {
                        Logger.getLogger(JmolPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        ;
        }.run();
        PDBFileComboBox.removeAllItems();
        if (pdbAccessions.isEmpty()) {
            PDBFileComboBox.addItem("no pdb accessions found for protein");
            PDBFileComboBox.addItem("some test accessions");
            PDBFileComboBox.addItem("1GZX");
            PDBFileComboBox.addItem("3Q4C");
            PDBFileComboBox.addItem("3C4C");
        } else {
            for (String aPDBFileAccession : pdbAccessions) {
                PDBFileComboBox.addItem(aPDBFileAccession);
            }
        }
        jProgressBar1.setIndeterminate(false);
    }
}
