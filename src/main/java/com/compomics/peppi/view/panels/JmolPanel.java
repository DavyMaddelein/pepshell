package com.compomics.peppi.view.panels;

import com.compomics.peppi.FaultBarrier;
import com.compomics.peppi.controllers.DAO.PDBDAO;
import com.compomics.peppi.controllers.properties.PDBProperties;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.enums.PDBPropertyEnum;
import com.compomics.peppi.model.exceptions.ConversionException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.jmol.api.JmolViewer;
import org.biojava.bio.structure.io.PDBFileReader;
import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolAdapter;

/**
 *
 * @author Davy
 */
public class JmolPanel extends javax.swing.JPanel {

    private JmolViewer viewer;
    private FaultBarrier faultBarrier;
    private PDBFileReader pdbr = new PDBFileReader();
    private JmolAdapter jMolAdapter = new SmarterJmolAdapter();

    /**
     * Creates new form PDBPanel
     */
    public JmolPanel() {
        this.faultBarrier = FaultBarrier.getInstance();
        initComponents();
        if (PDBProperties.getInstance().getProperty(PDBPropertyEnum.FILELOCATION.getValue()).isEmpty()) {
            pdbr.setPath(System.getProperty("java.io.tmpdir"));
        } else {
            pdbr.setPath(PDBProperties.getInstance().getProperty(PDBPropertyEnum.FILELOCATION.getValue()));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        PDBFileComboBox = new javax.swing.JComboBox();
        pdbViewPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        PDBFileComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2L05", "3C4C", "3D4Q", "3IDP", "3II5", "3NY5", "3OG7", "3PPJ", "3PPK", "3PRF", "3PRI" }));
        PDBFileComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDBFileComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pdbViewPanelLayout = new javax.swing.GroupLayout(pdbViewPanel);
        pdbViewPanel.setLayout(pdbViewPanelLayout);
        pdbViewPanelLayout.setHorizontalGroup(
            pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        pdbViewPanelLayout.setVerticalGroup(
            pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jButton1.setText("Save PDB file ...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(pdbViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PDBFileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
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
                .addGap(17, 17, 17)
                .addComponent(pdbViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PDBFileComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDBFileComboBoxActionPerformed
        try {
            viewer = JmolViewer.allocateViewer(pdbViewPanel, jMolAdapter);
            //if (offlineMode){
            //viewer.openFile(PDBProperties.getInstance().getProperty(PDBPropertyEnum.FILELOCATION.getValue())+"/"+((String)PDBFileComboBox.getSelectedItem()));
            //} else{
            viewer.openStringInline(PDBDAO.getPdbFileInMem(((String) PDBFileComboBox.getSelectedItem())));
            //}
            viewer.renderScreenImage(pdbViewPanel.getGraphics(), pdbViewPanel.getSize(), pdbViewPanel.getGraphics().getClipBounds(new Rectangle()));
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, "the PDB file for accession :" + (String) PDBFileComboBox.getSelectedItem() + " could not be retrieved");
            faultBarrier.handleException(ioe);
        }
    }//GEN-LAST:event_PDBFileComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PDBFileComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JPanel pdbViewPanel;
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

    public void setPDBProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        Set<String> pdbAccessions = PDBDAO.getPDBFileAccessionsForProtein(protein);
        PDBFileComboBox.removeAllItems();
        if (pdbAccessions.isEmpty()) {
            PDBFileComboBox.addItem("no pdb accessions found for protein");
        } else {
            for (String aPDBFileAccession : pdbAccessions) {
                PDBFileComboBox.addItem(aPDBFileAccession);
            }
        }
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (viewer != null) {
            viewer.refresh(WIDTH, TOOL_TIP_TEXT_KEY);
            //viewer.renderScreenImage(pdbViewPanel.getGraphics(), pdbViewPanel.getSize(), pdbViewPanel.getGraphics().getClipBounds());
        }
    }
}
