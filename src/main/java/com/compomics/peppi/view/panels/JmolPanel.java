package com.compomics.peppi.view.panels;

import com.compomics.peppi.FaultBarrier;
import com.compomics.peppi.controllers.DAO.PDBDAO;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.ConversionException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashSet;
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
        pdbViewPanel1 = new com.compomics.peppi.view.panels.PdbViewPanel();

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
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pdbViewPanel1Layout.setVerticalGroup(
            pdbViewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pdbViewPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
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
                .addGap(18, 18, 18)
                .addComponent(pdbViewPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PDBFileComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDBFileComboBoxActionPerformed
        try {
            pdbViewPanel1.setPdbFile((String) PDBFileComboBox.getSelectedItem());
        } catch (IOException ioe) {
            if (PDBFileComboBox.getSelectedItem() != "no pdb accessions found for protein") {
                JOptionPane.showMessageDialog(this, "the PDB file for accession: " + (String) PDBFileComboBox.getSelectedItem() + " could not be retrieved");
                faultBarrier.handleException(ioe);
            }
        }
    }//GEN-LAST:event_PDBFileComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PDBFileComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JProgressBar jProgressBar1;
    private com.compomics.peppi.view.panels.PdbViewPanel pdbViewPanel1;
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
