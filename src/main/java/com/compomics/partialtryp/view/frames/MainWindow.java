package com.compomics.partialtryp.view.frames;

import com.compomics.partialtryp.view.frames.ProteinInfoFrame;
import com.compomics.partialtryp.FaultBarrier;
import com.compomics.partialtryp.controllers.DAO.MsLimsDAO;
import com.compomics.partialtryp.controllers.DAO.PDBDAO;
import com.compomics.partialtryp.controllers.DAO.WebDAO;
import com.compomics.partialtryp.controllers.objectcontrollers.ProteinController;
import com.compomics.partialtryp.model.Project;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.exceptions.ConversionException;
import com.compomics.partialtryp.view.panels.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class MainWindow extends javax.swing.JFrame implements Observer {

    private Set<Project> toCompareProjects;
    private Project referenceProject;
    private FaultBarrier faultBarrier;

    MainWindow(Project toCompareWithProject, Set<Project> toCompareProjects) {
        this.faultBarrier = FaultBarrier.getInstance();
        this.toCompareProjects = toCompareProjects;
        this.referenceProject = toCompareWithProject;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        faultBarrier.addObserver(this);
        try {
            MsLimsDAO.fetchProteins(referenceProject);
            for (Project aProjectToCompareWith : toCompareProjects) {
                MsLimsDAO.fetchProteins(aProjectToCompareWith);
            }
            //todo filter proteins on found
        } catch (SQLException ex) {
            faultBarrier.handleError(ex);
        } catch (URISyntaxException ex) {
            faultBarrier.handleError(ex);
        } catch (MalformedURLException ex) {
            faultBarrier.handleError(ex);
        } catch (IOException ex) {
            faultBarrier.handleError(ex);
            JOptionPane.showMessageDialog(null, "there has been a connection error while retrieving the protein sequence:\n" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
        jScrollPane1.setViewportView(proteinList);
        proteinList.setListData((referenceProject.getProteins().toArray()));
        jScrollPane2.setViewportView(pdbProteinList);
        pdbProteinList.setListData(referenceProject.getProteins().toArray());
    }

    public void update(Observable faultBarrier, Object o1) {
        tabsPane.setFont(new Font(null, Font.BOLD, 12));
        tabsPane.setIconAt(tabsPane.getTabCount() - 1, UIManager.getIcon("OptionPane.warningIcon"));
        //TODO show in error pane
        //o1.printStackTrace();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabsPane = new javax.swing.JTabbedPane();
        mainViewPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        proteinList = new javax.swing.JList();
        accessionLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        infoPanel1 = new InfoPanel(toCompareProjects);
        pDBPanel1 = new com.compomics.partialtryp.view.panels.JmolPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pdbProteinList = new javax.swing.JList();
        pdbComboBox = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        exportPanel = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        exportButton = new javax.swing.JButton();
        jCheckBox7 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        errorReporterPanel1 = new com.compomics.partialtryp.view.panels.ErrorReporterPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comparison window");

        tabsPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsPaneStateChanged(evt);
            }
        });

        mainViewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        proteinList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        proteinList.setMaximumSize(new java.awt.Dimension(99, 90));
        proteinList.setMinimumSize(new java.awt.Dimension(99, 90));
        proteinList.setPreferredSize(null);
        proteinList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proteinListMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                proteinListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(proteinList);

        accessionLabel.setText("found reference proteins");

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSeparator1.setMinimumSize(new java.awt.Dimension(50, 3));
        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 3));

        javax.swing.GroupLayout mainViewPanelLayout = new javax.swing.GroupLayout(mainViewPanel);
        mainViewPanel.setLayout(mainViewPanelLayout);
        mainViewPanelLayout.setHorizontalGroup(
            mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainViewPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(accessionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(infoPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainViewPanelLayout.setVerticalGroup(
            mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainViewPanelLayout.createSequentialGroup()
                        .addComponent(accessionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainViewPanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(infoPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabsPane.addTab("Protein Comparison", mainViewPanel);

        pDBPanel1.setMinimumSize(new java.awt.Dimension(50, 3));
        pDBPanel1.setPreferredSize(new java.awt.Dimension(50, 3));

        jLabel2.setText("found reference proteins");

        pdbProteinList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pdbProteinListMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(pdbProteinList);

        jSeparator3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSeparator3.setMinimumSize(new java.awt.Dimension(50, 3));
        jSeparator3.setPreferredSize(new java.awt.Dimension(50, 3));

        javax.swing.GroupLayout pDBPanel1Layout = new javax.swing.GroupLayout(pDBPanel1);
        pDBPanel1.setLayout(pDBPanel1Layout);
        pDBPanel1Layout.setHorizontalGroup(
            pDBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(pdbComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(964, 964, 964))
        );
        pDBPanel1Layout.setVerticalGroup(
            pDBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDBPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(pdbComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDBPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(pDBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDBPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabsPane.addTab("PDB information", pDBPanel1);

        jCheckBox1.setText("export all projects");

        jCheckBox2.setText("export selection of projects");

        jCheckBox3.setText("export selection of proteins");

        jCheckBox4.setText("export selection of peptides");

        jLabel1.setText("select export options (temp)");

        jCheckBox5.setText("Export as tab seperated file");

        jCheckBox6.setText("Export as excel file");

        exportButton.setText("Export!");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        jCheckBox7.setText("change savefile name, this will change the name for all exported files");

        jTextField1.setText("autogeneratedName");

        javax.swing.GroupLayout exportPanelLayout = new javax.swing.GroupLayout(exportPanel);
        exportPanel.setLayout(exportPanelLayout);
        exportPanelLayout.setHorizontalGroup(
            exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(exportPanelLayout.createSequentialGroup()
                        .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportPanelLayout.createSequentialGroup()
                                    .addComponent(jCheckBox1)
                                    .addGap(301, 301, 301))
                                .addGroup(exportPanelLayout.createSequentialGroup()
                                    .addComponent(jCheckBox2)
                                    .addGap(257, 257, 257)))
                            .addGroup(exportPanelLayout.createSequentialGroup()
                                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox4))
                                .addGap(255, 255, 255)))
                        .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox5)))
                    .addGroup(exportPanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exportButton))))
                .addContainerGap(370, Short.MAX_VALUE))
        );
        exportPanelLayout.setVerticalGroup(
            exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox5))
                .addGap(18, 18, 18)
                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox6))
                .addGap(18, 18, 18)
                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox7))
                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exportPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox4))
                    .addGroup(exportPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(exportButton)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        tabsPane.addTab("exportPanel", exportPanel);
        tabsPane.addTab("Error Reporter", errorReporterPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //TODO add double click get protein information listener
    private void proteinListMouseReleased(java.awt.event.MouseEvent evt) {
        try {
            Protein proteinOfInterest = (Protein) proteinList.getSelectedValue();
            proteinOfInterest.setSequence(WebDAO.fetchSequence(proteinOfInterest.getProteinAccession()));
            infoPanel1.updateProteinGraphics(proteinOfInterest);
        } catch (MalformedURLException ex) {
            faultBarrier.handleError(ex);
        } catch (IOException ex) {
            faultBarrier.handleError(ex);
        } catch (SQLException ex) {
            faultBarrier.handleError(ex);
        } catch (ConversionException ex) {
            faultBarrier.handleError(ex);
            JOptionPane.showMessageDialog(null, "there has been a connection error while retrieving the protein sequence:\n" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tabsPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabsPaneStateChanged
        if (tabsPane.getSelectedIndex() == 2) {
            tabsPane.setFont(new Font(null, Font.PLAIN, 12));
            tabsPane.setIconAt(tabsPane.getTabCount() - 1, null);
        }
    }//GEN-LAST:event_tabsPaneStateChanged

    private void proteinListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseClicked
        if (evt.getClickCount() == 2) {
            ProteinInfoFrame protInfo = new ProteinInfoFrame((Protein) proteinList.getSelectedValue());
            protInfo.setVisible(true);
        }
    }//GEN-LAST:event_proteinListMouseClicked

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        //TODO extract export pane
        /*File saveLocation = new File(PropertiesManager.getPreferredSaveLocation());
         * JFileChooser saveLocationChooser = new JFileChooser(saveLocation,new DirectoryFilter()); --> set only directory mode
         * saveLocationChooser.setVisible(true); --> this is different
         * saveLocation = saveLocationChooser.getSelectedFile();
         * export(saveLocation,ExcelFileExportCheckbox.get)
         */
    }//GEN-LAST:event_exportButtonActionPerformed

    private void pdbProteinListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdbProteinListMouseReleased
        try {
            Protein proteinOfInterest = (Protein) proteinList.getSelectedValue();
            pdbComboBox = new JComboBox(PDBDAO.getPDBFileAccessionsForProtein(proteinOfInterest).toArray());
        } catch (MalformedURLException ex) {
            faultBarrier.handleError(ex);
        } catch (IOException ex) {
            faultBarrier.handleError(ex);
        }
    }//GEN-LAST:event_pdbProteinListMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accessionLabel;
    private com.compomics.partialtryp.view.panels.ErrorReporterPanel errorReporterPanel1;
    private javax.swing.JButton exportButton;
    private javax.swing.JPanel exportPanel;
    private com.compomics.partialtryp.view.panels.InfoPanel infoPanel1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel mainViewPanel;
    private com.compomics.partialtryp.view.panels.JmolPanel pDBPanel1;
    private javax.swing.JComboBox pdbComboBox;
    private javax.swing.JList pdbProteinList;
    private javax.swing.JList proteinList;
    private javax.swing.JTabbedPane tabsPane;
    // End of variables declaration//GEN-END:variables
}
