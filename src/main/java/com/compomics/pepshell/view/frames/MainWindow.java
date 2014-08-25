package com.compomics.pepshell.view.frames;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.WebDAO;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.InternetStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.ViewPreparation;
import com.compomics.pepshell.controllers.filters.RegexFilter;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.properties.DatabaseProperties;
import com.compomics.pepshell.controllers.properties.ViewProperties;
import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.UpdateMessage;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.view.panels.AccessionMaskDialog;
import com.compomics.pepshell.view.panels.CombinedLoginDialog;
import com.compomics.pepshell.view.panels.LinkDbLoginDialog;
import com.compomics.pepshell.view.panels.statistics.CleavingProbabilityPane;
import com.compomics.pepshell.view.panels.statistics.JFreeChartPanel;
import com.compomics.pepshell.view.panels.statistics.RatioStatisticsPane;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Davy
 */
public class MainWindow extends javax.swing.JFrame implements Observer {

    private static FaultBarrier faultBarrier;
    private List<Protein> proteinsToDisplay = new ArrayList<>();
    private final RegexFilter filter = new RegexFilter();

    public MainWindow() {
        faultBarrier = FaultBarrier.getInstance();
        initComponents();
//        downloadBar.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void collectData(Experiment referenceExperiment, List<AnalysisGroup> analysisList) {
        if (DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.ASKLINKDB.getKey()).equals("yes")
                && DbConnectionController.isLinkDbConnected() == false) {
            //JOptionpane ask about wanting to connect to link db
            askLinkDbLoginQuestion();
        }
        this.setEnabled(false);
        for (AnalysisGroup experiments : analysisList) {
            ViewPreparation prep = DataModeController.getDb().getDataMode().getViewPreparationForMode();
            prep.start(referenceExperiment, experiments.getExperiments().iterator(), false);
            prep.addObserver(this);
            proteinDetailPanel.setReferenceExperiment(referenceExperiment);
            proteinDetailPanel.setExperimentsToDisplay(experiments.getExperiments(), false);
            //when changed to a tab pane for multiple statistical views, replace with method that fills tabbed pane
            CleavingProbabilityPane cleavingProbabilityPane = new CleavingProbabilityPane(referenceExperiment);
            statisticsTabbedPane.add(cleavingProbabilityPane);
            //CPDTScrollPane.setViewportView(cleavingProbabilityPane);
//            GridBagConstraints tempConstraints = new GridBagConstraints();
//            tempConstraints.fill = GridBagConstraints.NONE;
//            tempConstraints.gridx = 0;
//            tempConstraints.gridy = 0;
            RatioStatisticsPane ratioStatisticsPane = new RatioStatisticsPane(experiments.getExperiments());
            statisticsTabbedPane.add(ratioStatisticsPane);
            //use this when needing to add more than one pane with data
            /**
             * int counter = 0; for (Experiment anExperiment :
             * experiments.getExperiments()) { tempConstraints.gridy = (int)
             * Math.floor(counter / 2); tempConstraints.gridx = counter % 2;
             * CPDTStatisticsPanel.add(new CPDTStatisticsPane(anExperiment),
             * tempConstraints); counter++; }
             */
        }

//proteinsToDisplay.addAll(((InfoPanel) infoPanelTabbedPane.getComponent(0)).getCondensedProject().getProteins());
        proteinsToDisplay = referenceExperiment.getProteins();
        //this totally does not have to happen with anonymous stuff
        proteinList.setListData(proteinsToDisplay.toArray());
        proteinListScrollPane.setViewportView(proteinList);
        pdbProteinList.setListData(proteinsToDisplay.toArray());
        pdbProteinListScrollPane.setViewportView(pdbProteinList);
        proteinList1.setListData(proteinsToDisplay.toArray());
        proteinListScrollPane1.setViewportView(proteinList1);
        this.setEnabled(true);
    }

//    @TODO remove this method
//    public void collectAndShowData(Experiment referenceExperiment, List<Experiment> experiments, ProteinDetailPanel proteinDetailPanel1) {
//        DataModeController.getDb().getDataMode().getViewPreparationForMode().retrieveData(referenceExperiment, experiments.iterator(), false);
//        proteinDetailPanel1.setExperimentsToDisplay(experiments);
//
//    }
    @Override
    public void update(Observable o, Object o1) {
        if (o1 != null && o1 instanceof Exception) {
            //tabsPane.setFont(new Font(null, Font.BOLD, 12));
            //tabsPane.setIconAt(tabsPane.getTabCount() - 1, UIManager.getIcon("OptionPane.warningIcon"));
            //TODO show in error pane
            ((Exception) o1).printStackTrace();
        } else if (o1 != null && o1 instanceof UpdateMessage) {
            proteinsToDisplay = proteinDetailPanel.getReferenceExperiment().getProteins();
            proteinList.setListData(proteinsToDisplay.toArray());
            pdbProteinList.setListData(proteinsToDisplay.toArray());
            proteinList1.setListData(proteinsToDisplay.toArray());
            if (((UpdateMessage) o1).repaint()) {
                this.repaint();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabsPane = new javax.swing.JTabbedPane();
        mainViewPanel = new javax.swing.JPanel();
        proteinsOverviewPanel = new javax.swing.JPanel();
        proteinListScrollPane = new javax.swing.JScrollPane();
        proteinList = new javax.swing.JList();
        filterTextField = new javax.swing.JTextField();
        proteinDetailPanel = new com.compomics.pepshell.view.panels.ProteinDetailPanel();
        pdbViewPanel = new javax.swing.JPanel();
        pdbProteinsOverviewPanel = new javax.swing.JPanel();
        pdbProteinListScrollPane = new javax.swing.JScrollPane();
        pdbProteinList = new javax.swing.JList();
        jmolPanel = new com.compomics.pepshell.view.panels.JmolPanel();
        statisticsPanel = new javax.swing.JPanel();
        proteinsOverviewPanel1 = new javax.swing.JPanel();
        proteinListScrollPane1 = new javax.swing.JScrollPane();
        proteinList1 = new javax.swing.JList();
        filterTextField1 = new javax.swing.JTextField();
        statisticsTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newViewMenuItem = new javax.swing.JMenuItem();
        saveToCsvMenuItem = new javax.swing.JMenuItem();
        saveToExcelMenuItem = new javax.swing.JMenuItem();
        exportOptionsMenuItem = new javax.swing.JMenuItem();
        ViewOptionsMenu = new javax.swing.JMenu();
        accessionMenuItem = new javax.swing.JMenu();
        uniProtRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        originalAccessionRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        setAccessionMaskOption = new javax.swing.JMenuItem();
        preferencesMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comparison window");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        tabsPane.setBackground(new java.awt.Color(255, 255, 255));
        tabsPane.setMinimumSize(new java.awt.Dimension(220, 220));
        tabsPane.setOpaque(true);
        tabsPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsPaneStateChanged(evt);
            }
        });

        mainViewPanel.setBackground(new java.awt.Color(255, 255, 255));

        proteinsOverviewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("reference proteins"));
        proteinsOverviewPanel.setOpaque(false);

        proteinListScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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
        proteinListScrollPane.setViewportView(proteinList);

        filterTextField.setText("search for ...");
        filterTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                filterTextFieldFocusGained(evt);
            }
        });
        filterTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                filterTextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout proteinsOverviewPanelLayout = new javax.swing.GroupLayout(proteinsOverviewPanel);
        proteinsOverviewPanel.setLayout(proteinsOverviewPanelLayout);
        proteinsOverviewPanelLayout.setHorizontalGroup(
            proteinsOverviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinsOverviewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proteinsOverviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proteinListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        proteinsOverviewPanelLayout.setVerticalGroup(
            proteinsOverviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinsOverviewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proteinListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainViewPanelLayout = new javax.swing.GroupLayout(mainViewPanel);
        mainViewPanel.setLayout(mainViewPanelLayout);
        mainViewPanelLayout.setHorizontalGroup(
            mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proteinsOverviewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proteinDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainViewPanelLayout.setVerticalGroup(
            mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proteinsOverviewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proteinDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabsPane.addTab("Experiment Comparison", mainViewPanel);

        pdbViewPanel.setBackground(new java.awt.Color(255, 255, 255));

        pdbProteinsOverviewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("reference proteins"));
        pdbProteinsOverviewPanel.setOpaque(false);

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
        pdbProteinListScrollPane.setViewportView(pdbProteinList);

        javax.swing.GroupLayout pdbProteinsOverviewPanelLayout = new javax.swing.GroupLayout(pdbProteinsOverviewPanel);
        pdbProteinsOverviewPanel.setLayout(pdbProteinsOverviewPanelLayout);
        pdbProteinsOverviewPanelLayout.setHorizontalGroup(
            pdbProteinsOverviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdbProteinsOverviewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pdbProteinListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        pdbProteinsOverviewPanelLayout.setVerticalGroup(
            pdbProteinsOverviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdbProteinsOverviewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pdbProteinListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );

        jmolPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("protein PDB detail"));

        javax.swing.GroupLayout pdbViewPanelLayout = new javax.swing.GroupLayout(pdbViewPanel);
        pdbViewPanel.setLayout(pdbViewPanelLayout);
        pdbViewPanelLayout.setHorizontalGroup(
            pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdbViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pdbProteinsOverviewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jmolPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                .addContainerGap())
        );
        pdbViewPanelLayout.setVerticalGroup(
            pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdbViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pdbProteinsOverviewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jmolPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabsPane.addTab("pdb view panel", pdbViewPanel);

        statisticsPanel.setBackground(new java.awt.Color(255, 255, 255));

        proteinsOverviewPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("reference proteins"));
        proteinsOverviewPanel1.setOpaque(false);

        proteinListScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        proteinList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        proteinList1.setMaximumSize(new java.awt.Dimension(99, 90));
        proteinList1.setMinimumSize(new java.awt.Dimension(99, 90));
        proteinList1.setPreferredSize(null);
        proteinList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proteinList1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                proteinList1MouseReleased(evt);
            }
        });
        proteinListScrollPane1.setViewportView(proteinList1);

        filterTextField1.setText("search for ...");
        filterTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                filterTextField1FocusGained(evt);
            }
        });
        filterTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                filterTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout proteinsOverviewPanel1Layout = new javax.swing.GroupLayout(proteinsOverviewPanel1);
        proteinsOverviewPanel1.setLayout(proteinsOverviewPanel1Layout);
        proteinsOverviewPanel1Layout.setHorizontalGroup(
            proteinsOverviewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinsOverviewPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proteinsOverviewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proteinListScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        proteinsOverviewPanel1Layout.setVerticalGroup(
            proteinsOverviewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinsOverviewPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proteinListScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );

        statisticsTabbedPane.addTab("tab2", jScrollPane1);

        javax.swing.GroupLayout statisticsPanelLayout = new javax.swing.GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proteinsOverviewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(statisticsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
                .addContainerGap())
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proteinsOverviewPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addComponent(statisticsTabbedPane)
                .addContainerGap())
        );

        tabsPane.addTab("statistics", statisticsPanel);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        fileMenu.setText("File");

        newViewMenuItem.setText("load new view ...");
        newViewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newViewMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newViewMenuItem);

        saveToCsvMenuItem.setText("save to CSV file");
        fileMenu.add(saveToCsvMenuItem);

        saveToExcelMenuItem.setText("save to excel file");
        fileMenu.add(saveToExcelMenuItem);

        exportOptionsMenuItem.setText("export options ...");
        exportOptionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportOptionsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exportOptionsMenuItem);

        jMenuBar1.add(fileMenu);

        ViewOptionsMenu.setText("View options");

        accessionMenuItem.setText("accession");

        uniProtRadioButtonMenuItem.setSelected(true);
        uniProtRadioButtonMenuItem.setText("uniprot");
        uniProtRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uniProtRadioButtonMenuItemActionPerformed(evt);
            }
        });
        accessionMenuItem.add(uniProtRadioButtonMenuItem);

        originalAccessionRadioButtonMenuItem.setSelected(true);
        originalAccessionRadioButtonMenuItem.setText("original accession");
        originalAccessionRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                originalAccessionRadioButtonMenuItemActionPerformed(evt);
            }
        });
        accessionMenuItem.add(originalAccessionRadioButtonMenuItem);

        setAccessionMaskOption.setText("set mask for accessions ...");
        setAccessionMaskOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setAccessionMaskOptionActionPerformed(evt);
            }
        });
        accessionMenuItem.add(setAccessionMaskOption);

        ViewOptionsMenu.add(accessionMenuItem);

        jMenuBar1.add(ViewOptionsMenu);

        preferencesMenu.setText("Preferences...");
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
            .addComponent(tabsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    jmolPanel.setPDBProtein((Protein) pdbProteinList.getSelectedValue());
                } catch (MalformedURLException ex) {
                    faultBarrier.handleException(ex);
                } catch (ConversionException | SQLException ex) {
                    faultBarrier.handleException(ex);
                }
            }
        }.run();

    }//GEN-LAST:event_pdbProteinListMouseClicked

    private void proteinListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseReleased
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            Protein proteinOfInterest = (Protein) proteinList.getSelectedValue();
            if (ProgramVariables.USEINTERNETSOURCES && proteinOfInterest.getProteinSequence().isEmpty()) {
                try {
                    proteinOfInterest.setSequence(WebDAO.fetchSequence(proteinOfInterest.getProteinAccession()));
                } catch (IOException | ConversionException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            }
            proteinDetailPanel.updateProteinGraphics(proteinOfInterest);
        } catch (SQLException ex) {
            faultBarrier.handleException(ex);
            JOptionPane.showMessageDialog(null, "there has been a connection error while retrieving the protein sequence:\n" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ex) {
            faultBarrier.handleException(ex);
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_proteinListMouseReleased

    private void proteinListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                JOptionPane.showMessageDialog(this, "opening uniprot accession");
                Desktop.getDesktop().browse(new URI(ViewProperties.getInstance().getProperty("protein.externaldatalocation") + AccessionConverter.toUniprot(((Protein) proteinList.getSelectedValue()).getVisibleAccession())));
            } catch (URISyntaxException | IOException | ConversionException ex) {
                faultBarrier.handleException(ex);
            }
        }
    }//GEN-LAST:event_proteinListMouseClicked

    private void preferencesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesMenuActionPerformed
        // TODO add your handling code here:
        new PreferenceFrame();

    }//GEN-LAST:event_preferencesMenuActionPerformed

    private void uniProtRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uniProtRadioButtonMenuItemActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (final Protein aProtein : proteinsToDisplay) {
            try {
                aProtein.setVisibleAccession(AccessionConverter.toUniprot(aProtein.getProteinAccession()));
            } catch (IOException | ConversionException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
            proteinList.revalidate();
            proteinList.repaint();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_uniProtRadioButtonMenuItemActionPerformed

    private void originalAccessionRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originalAccessionRadioButtonMenuItemActionPerformed
        for (Protein aProtein : proteinsToDisplay) {
            aProtein.setAccession(aProtein.getOriginalAccession());
        }
        proteinList.repaint();
        proteinList1.repaint();
        pdbProteinList.repaint();
    }//GEN-LAST:event_originalAccessionRadioButtonMenuItemActionPerformed

    private void filterTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTextFieldKeyTyped
        // TODO add your handling code here:
        String searchTerm = ((JTextComponent) evt.getComponent()).getText();
        if (!searchTerm.isEmpty()) {
            if (!searchTerm.endsWith("$")) {
                searchTerm += ".*";
            }
            if (!searchTerm.startsWith("^")) {
                searchTerm = ".*" + searchTerm;
            }
            List<String> searchRegex = new ArrayList<>(1);
            searchRegex.add(searchTerm);
            proteinList.setListData(filter.filterProtein(proteinsToDisplay, searchRegex).toArray(new Protein[0]));
        } else {
            proteinList.setListData(proteinsToDisplay.toArray());
        }
    }//GEN-LAST:event_filterTextFieldKeyTyped

    private void exportOptionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportOptionsMenuItemActionPerformed
        // TODO add your handling code here:
        //new exportoptionspanel
    }//GEN-LAST:event_exportOptionsMenuItemActionPerformed

    private void newViewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newViewMenuItemActionPerformed
        int selection = JOptionPane.showConfirmDialog(null, "Select a new set of experiments?", "new view", JOptionPane.YES_NO_OPTION);
        if (selection == JOptionPane.YES_OPTION) {
            CombinedLoginDialog combinedLoginDialog = new CombinedLoginDialog();
            combinedLoginDialog.pack();
            combinedLoginDialog.setLocationRelativeTo(null);
            combinedLoginDialog.setVisible(true);

            this.dispose();
        }
    }//GEN-LAST:event_newViewMenuItemActionPerformed

    private void setAccessionMaskOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setAccessionMaskOptionActionPerformed
        AccessionMaskDialog dialog = new AccessionMaskDialog(this, true, proteinsToDisplay);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        proteinList.repaint();
        pdbProteinList.repaint();
    }//GEN-LAST:event_setAccessionMaskOptionActionPerformed

    private void filterTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterTextFieldFocusGained
        if (!filterTextField.getText().isEmpty() && filterTextField.getText().equals("search for ...")) {
            filterTextField.setText("");
        }
    }//GEN-LAST:event_filterTextFieldFocusGained

    private void proteinList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinList1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_proteinList1MouseClicked

    private void proteinList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinList1MouseReleased
        // TODO add your handling code here:
        for (int i = 0; i < statisticsTabbedPane.getComponentCount(); i++) {
            if (statisticsTabbedPane.getComponentAt(i) instanceof JFreeChartPanel) {
                ((JFreeChartPanel) statisticsTabbedPane.getComponentAt(i)).setGraphData((Protein) proteinList1.getSelectedValue());
            }
        }


    }//GEN-LAST:event_proteinList1MouseReleased

    private void filterTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterTextField1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTextField1FocusGained

    private void filterTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTextField1KeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ViewOptionsMenu;
    private javax.swing.JMenu accessionMenuItem;
    private javax.swing.JMenuItem exportOptionsMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JTextField filterTextField1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.compomics.pepshell.view.panels.JmolPanel jmolPanel;
    private javax.swing.JPanel mainViewPanel;
    private javax.swing.JMenuItem newViewMenuItem;
    private javax.swing.JRadioButtonMenuItem originalAccessionRadioButtonMenuItem;
    private javax.swing.JList pdbProteinList;
    private javax.swing.JScrollPane pdbProteinListScrollPane;
    private javax.swing.JPanel pdbProteinsOverviewPanel;
    private javax.swing.JPanel pdbViewPanel;
    private javax.swing.JMenu preferencesMenu;
    private com.compomics.pepshell.view.panels.ProteinDetailPanel proteinDetailPanel;
    private javax.swing.JList proteinList;
    private javax.swing.JList proteinList1;
    private javax.swing.JScrollPane proteinListScrollPane;
    private javax.swing.JScrollPane proteinListScrollPane1;
    private javax.swing.JPanel proteinsOverviewPanel;
    private javax.swing.JPanel proteinsOverviewPanel1;
    private javax.swing.JMenuItem saveToCsvMenuItem;
    private javax.swing.JMenuItem saveToExcelMenuItem;
    private javax.swing.JMenuItem setAccessionMaskOption;
    private javax.swing.JPanel statisticsPanel;
    private javax.swing.JTabbedPane statisticsTabbedPane;
    private javax.swing.JTabbedPane tabsPane;
    private javax.swing.JRadioButtonMenuItem uniProtRadioButtonMenuItem;
    // End of variables declaration//GEN-END:variables

    private void askLinkDbLoginQuestion() {
        JCheckBox doNotAskAgain = new JCheckBox();
        doNotAskAgain.setText("do not ask this again");
        Object[] params = {"you have selected to use link db but are not logged in yet\ndo you want to log in?", doNotAskAgain};
        int selection = JOptionPane.showConfirmDialog(null, params, "log into link db", JOptionPane.YES_NO_CANCEL_OPTION);
        if (selection != JOptionPane.CANCEL_OPTION) {
            if (doNotAskAgain.isSelected()) {
                DatabaseProperties.getInstance().getProperties().setProperty("linkdb.askagain", "no");
            }
            if (selection == JOptionPane.YES_OPTION) {
                LinkDbLoginDialog loginDialog = new LinkDbLoginDialog();
                loginDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            } else if (selection == JOptionPane.NO_OPTION) {
                ProgramVariables.STRUCTUREDATASOURCE = new InternetStructureDataSource();
            }
        }
    }
}
