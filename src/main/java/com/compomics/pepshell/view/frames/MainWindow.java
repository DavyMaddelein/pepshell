package com.compomics.pepshell.view.frames;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.WebDAO;
import com.compomics.pepshell.controllers.properties.ViewProperties;
import com.compomics.pepshell.filters.RegexFilter;
import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.view.panels.InfoPanel;
import com.compomics.pepshell.view.panels.StatisticsPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Davy
 */
public class MainWindow extends javax.swing.JFrame implements Observer {

    private static FaultBarrier faultBarrier;
    private List<Protein> proteinsToDisplay = new ArrayList<Protein>();
    private RegexFilter filter = new RegexFilter();

    public MainWindow() {
        faultBarrier = FaultBarrier.getInstance();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public MainWindow(final Experiment toCompareWithProject, final AnalysisGroup<Experiment> dataList) {
        this();
        //infoPanelTabbedPane.add(new InfoPanel());
        new Runnable() {
            @Override
            public void run() {
                collectData(toCompareWithProject, dataList);
            }
        }.run();
    }

    public MainWindow(final Experiment referenceExperiment, final List<AnalysisGroup<Experiment>> dataList) {
        this();
        new Runnable() {
            @Override
            public void run() {
                for (AnalysisGroup anAnalysisGroup : dataList) {
                    collectData(referenceExperiment, anAnalysisGroup);
                    //infoPanelTabbedPane.add(anAnalysisGroup.getName(), new InfoPanel(referenceExperiment,anAnalysisGroup));
                    infoPanel1.setReferenceExperiment(referenceExperiment);
                    statisticsTabbedPane.add(anAnalysisGroup.getName(), new StatisticsPanel(anAnalysisGroup));
                    infoPanel1.setExperimentsToDisplay(anAnalysisGroup, false);
                }
            }
        }.run();
        //proteinsToDisplay.addAll(((InfoPanel) infoPanelTabbedPane.getComponent(0)).getCondensedProject().getProteins());
        //TODO: turn the proteins not in the referenceExperiment red
        proteinsToDisplay = referenceExperiment;
        //this totally does not have to happen with anonymous stuff
        proteinList.setListData(proteinsToDisplay.toArray());
        jScrollPane1.setViewportView(proteinList);
        pdbProteinList.setListData(proteinsToDisplay.toArray());
        jScrollPane2.setViewportView(pdbProteinList);
    }

    private void collectData(Experiment referenceExperiment, List<Experiment> experiments) {
        DataModeController.getDb().getDataMode().getViewPreparationForMode().PrepareProteinsForJList(referenceExperiment, experiments.iterator(), false);
    }

    //
    private void collectAndShowData(Experiment referenceExperiment, List<Experiment> experiments, InfoPanel anInfoPanel) {
        DataModeController.getDb().getDataMode().getViewPreparationForMode().PrepareProteinsForJList(referenceExperiment, experiments.iterator(), false);
        anInfoPanel.setExperimentsToDisplay(experiments);

    }

    public void update(Observable o, Object o1) {
        if (o1 != null && o1 instanceof Exception) {
            tabsPane.setFont(new Font(null, Font.BOLD, 12));
            tabsPane.setIconAt(tabsPane.getTabCount() - 1, UIManager.getIcon("OptionPane.warningIcon"));
            //TODO show in error pane
            ((Exception) o1).printStackTrace();
        }
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
        infoPanel1 = new com.compomics.pepshell.view.panels.InfoPanel();
        filterTextField = new javax.swing.JTextField();
        PDBViewPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pdbProteinList = new javax.swing.JList();
        jSeparator2 = new javax.swing.JSeparator();
        jmolPanel1 = new com.compomics.pepshell.view.panels.JmolPanel();
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
        jPanel2 = new javax.swing.JPanel();
        statisticsTabbedPane = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        preferencesMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comparison window");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setPreferredSize(new java.awt.Dimension(800, 600));

        tabsPane.setBackground(new java.awt.Color(255, 255, 255));
        tabsPane.setMinimumSize(new java.awt.Dimension(1573, 571));
        tabsPane.setOpaque(true);
        tabsPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsPaneStateChanged(evt);
            }
        });

        mainViewPanel.setBackground(new java.awt.Color(255, 255, 255));

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

        filterTextField.setText("search for ...");
        filterTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                filterTextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout mainViewPanelLayout = new javax.swing.GroupLayout(mainViewPanel);
        mainViewPanel.setLayout(mainViewPanelLayout);
        mainViewPanelLayout.setHorizontalGroup(
            mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(accessionLabel)
                        .addGroup(mainViewPanelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(filterTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(infoPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainViewPanelLayout.setVerticalGroup(
            mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainViewPanelLayout.createSequentialGroup()
                .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainViewPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainViewPanelLayout.createSequentialGroup()
                                .addComponent(infoPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                                .addGap(19, 19, 19))))
                    .addGroup(mainViewPanelLayout.createSequentialGroup()
                        .addComponent(accessionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        tabsPane.addTab("Protein Comparison", mainViewPanel);

        pdbProteinList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        pdbProteinList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pdbProteinListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(pdbProteinList);

        javax.swing.GroupLayout PDBViewPanelLayout = new javax.swing.GroupLayout(PDBViewPanel);
        PDBViewPanel.setLayout(PDBViewPanelLayout);
        PDBViewPanelLayout.setHorizontalGroup(
            PDBViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PDBViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jmolPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        PDBViewPanelLayout.setVerticalGroup(
            PDBViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PDBViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PDBViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PDBViewPanelLayout.createSequentialGroup()
                        .addComponent(jmolPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PDBViewPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );

        tabsPane.addTab("pdb view panel", PDBViewPanel);

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
                .addContainerGap(538, Short.MAX_VALUE))
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
                .addContainerGap(507, Short.MAX_VALUE))
        );

        tabsPane.addTab("exportPanel", exportPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(statisticsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1392, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statisticsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
        );

        tabsPane.addTab("statistics", jPanel2);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("options");

        jMenu3.setText("accession");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("uniprot");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("original accession");
        jRadioButtonMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setSelected(true);
        jRadioButtonMenuItem3.setText("jRadioButtonMenuItem3");
        jMenu3.add(jRadioButtonMenuItem3);

        jMenu2.add(jMenu3);

        jMenuBar1.add(jMenu2);

        preferencesMenu.setText("preferences...");
        preferencesMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(preferencesMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1398, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabsPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabsPaneStateChanged
        if (tabsPane.getSelectedIndex() == 2) {
            tabsPane.setFont(new Font(null, Font.PLAIN, 12));
            tabsPane.setIconAt(tabsPane.getTabCount() - 1, null);
        }
    }//GEN-LAST:event_tabsPaneStateChanged

    private void pdbProteinListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdbProteinListMouseClicked

        new Runnable() {
            @Override
            public void run() {
                try {
                    jmolPanel1.setPDBProtein((Protein) pdbProteinList.getSelectedValue());
                } catch (MalformedURLException ex) {
                    faultBarrier.handleException(ex);
                } catch (IOException ex) {
                    faultBarrier.handleException(ex);
                } catch (ConversionException ex) {
                    faultBarrier.handleException(ex);
                } catch (SQLException ex) {
                    faultBarrier.handleException(ex);
                }
            }
        }.run();

    }//GEN-LAST:event_pdbProteinListMouseClicked

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        //TODO extract export pane
        /**
         * File saveLocation = new
         * File(PropertiesManager.getPreferredSaveLocation()); JFileChooser
         * saveLocationChooser = new JFileChooser(saveLocation,new
         * DirectoryFilter()); --> set only directory mode
         * saveLocationChooser.setVisible(true); --> this is different
         * saveLocation = saveLocationChooser.getSelectedFile();
         * export(saveLocation,ExcelFileExportCheckbox.get)
         */
    }//GEN-LAST:event_exportButtonActionPerformed

    private void proteinListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseReleased

        try {
            Protein proteinOfInterest = (Protein) proteinList.getSelectedValue();
            if (proteinOfInterest.getProteinSequence().isEmpty()) {
                try {
                    proteinOfInterest.setSequence(WebDAO.fetchSequence(proteinOfInterest.getProteinAccession()));
                } catch (Exception ex) {
                    //faultBarrier.handleException(ex);
                }
            }
            infoPanel1.updateProteinGraphics(proteinOfInterest);
            //very dirty
            pdbProteinListMouseClicked(evt);
        } catch (SQLException ex) {
            faultBarrier.handleException(ex);
            JOptionPane.showMessageDialog(null, "there has been a connection error while retrieving the protein sequence:\n" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ex) {
            faultBarrier.handleException(ex);
        }
    }//GEN-LAST:event_proteinListMouseReleased

    private void proteinListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                JOptionPane.showMessageDialog(this, "opening uniprot accession");
                Desktop.getDesktop().browse(new URI(ViewProperties.getInstance().getProperty("protein.externaldatalocation") + AccessionConverter.GIToUniprot(((Protein) proteinList.getSelectedValue()).getProteinAccession())));
            } catch (URISyntaxException ex) {
                faultBarrier.handleException(ex);
            } catch (IOException ex) {
                faultBarrier.handleException(ex);
            } catch (ConversionException ex) {
                faultBarrier.handleException(ex);
            }

        }
    }//GEN-LAST:event_proteinListMouseClicked

    private void preferencesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesMenuActionPerformed
        // TODO add your handling code here:
        new PreferenceFrame();

    }//GEN-LAST:event_preferencesMenuActionPerformed

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        // TODO add your handling code here:
        for (Protein aProtein : proteinsToDisplay) {
            try {
                aProtein.setAccession(AccessionConverter.ToUniprot(aProtein.getProteinAccession()));
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jRadioButtonMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem2ActionPerformed
        // TODO add your handling code here:
        for (Protein aProtein : proteinsToDisplay) {
            aProtein.setAccession(aProtein.getOriginalAccession());
        }
    }//GEN-LAST:event_jRadioButtonMenuItem2ActionPerformed

    private void filterTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTextFieldKeyTyped
        // TODO add your handling code here:
        String searchTerm = ((JTextField) evt.getComponent()).getText();
        if (!searchTerm.isEmpty()) {
            if (!searchTerm.endsWith("$")) {
                searchTerm += ".*";
            }
            if (!searchTerm.startsWith("^")) {
                searchTerm = ".*" + searchTerm;
            }
            List<String> searchRegex = new ArrayList<String>(1);
            searchRegex.add(searchTerm);
            for (Protein protein : proteinsToDisplay) {

            }
            proteinList.setListData(filter.filterProtein(proteinsToDisplay, searchRegex).toArray(new Protein[0]));
        } else {
            proteinList.setListData(proteinsToDisplay.toArray());
        }
    }//GEN-LAST:event_filterTextFieldKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PDBViewPanel;
    private javax.swing.JLabel accessionLabel;
    private javax.swing.JButton exportButton;
    private javax.swing.JPanel exportPanel;
    private javax.swing.JTextField filterTextField;
    private com.compomics.pepshell.view.panels.InfoPanel infoPanel1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private com.compomics.pepshell.view.panels.JmolPanel jmolPanel1;
    private javax.swing.JPanel mainViewPanel;
    private javax.swing.JList pdbProteinList;
    private javax.swing.JMenu preferencesMenu;
    private javax.swing.JList proteinList;
    private javax.swing.JTabbedPane statisticsTabbedPane;
    private javax.swing.JTabbedPane tabsPane;
    // End of variables declaration//GEN-END:variables
}
