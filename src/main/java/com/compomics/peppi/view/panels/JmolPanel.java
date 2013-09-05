package com.compomics.peppi.view.panels;

import com.compomics.peppi.FaultBarrier;
import com.compomics.peppi.controllers.DAO.PDBDAO;
import com.compomics.peppi.controllers.DAO.URLController;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.exceptions.ConversionException;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.jmol.api.JmolViewer;

/**
 *
 * @author Davy
 */
public class JmolPanel extends javax.swing.JPanel {

    private JmolViewer viewer;
    private FaultBarrier faultBarrier;
    
    /**
     * Creates new form PDBPanel
     */
    public JmolPanel() {
        this.faultBarrier = FaultBarrier.getInstance();
        initComponents();
        viewer = JmolViewer.allocateViewer(this, null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        PDBFileComboBox = new javax.swing.JComboBox();

        PDBFileComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDBFileComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                    .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PDBFileComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDBFileComboBoxActionPerformed
       try{
        viewer.openStringInline(URLController.readUrl("http://www.rcsb.org/pdb/files/" + (String)PDBFileComboBox.getSelectedItem()));
        viewer.renderScreenImage(this.getGraphics(), this.getSize(), this.getGraphics().getClipBounds());
       } catch (IOException ioe) {
           JOptionPane.showMessageDialog(this, "the PDB file for accession :" + (String)PDBFileComboBox.getSelectedItem() + " could not be downloaded");
           faultBarrier.handleException(ioe);
       }
    }//GEN-LAST:event_PDBFileComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PDBFileComboBox;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

    
    public void preparePDBPanelForProtein(Protein protein) throws ConversionException {
        try {
            PDBFileComboBox.setModel(new DefaultComboBoxModel(PDBDAO.getPDBFileAccessionsForProtein(protein).toArray()));
        } catch (MalformedURLException ex) {
            faultBarrier.handleException(ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "there has been a problem trying to retrieve the pdb file accessions for this protein: "+protein.getProteinAccession());
            faultBarrier.handleException(ex);
        }
    }
}
