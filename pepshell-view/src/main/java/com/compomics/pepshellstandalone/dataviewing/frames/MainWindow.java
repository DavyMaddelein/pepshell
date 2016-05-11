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
package com.compomics.pepshellstandalone.dataviewing.frames;

import com.compomics.pepshellstandalone.ProgramVariables;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.WebDAO;
import com.compomics.pepshell.controllers.filters.RegexFilter;
import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.enums.DataRetrievalPropertyEnum;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.UpdateMessage;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.model.gradientMaps.HydrophobicityMaps;
import com.compomics.pepshellstandalone.dataloading.AccessionMaskDialog;
import com.compomics.pepshellstandalone.dataloading.CombinedLoginDialog;
import com.compomics.pepshellstandalone.exceptionhandling.ExceptionEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Davy Maddelein
 */
public class MainWindow extends javax.swing.JFrame implements Observer {

    private List<? extends PepshellProtein> proteinsToDisplay = new ArrayList<>();
    private final RegexFilter filter = new RegexFilter();
    private ComboBoxModel experimentComboBoxModel;

    public MainWindow() {
        initComponents();
//        downloadBar.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    public void setData(Experiment referenceExperiment, List<AnalysisGroup> analysisGroups) {


        proteinDetailPanel.setReferenceExperiment(referenceExperiment);

        for (AnalysisGroup experiments : analysisGroups) {
            proteinDetailPanel.setExperimentsToDisplay(experiments.getExperiments(), false);
            statisticsTabbedPane.setAnalysisGroupToDisplay(referenceExperiment, experiments);
            List<Experiment> var = experiments.getExperiments();
            //perhaps bring back tabbed panes
            experimentComboBoxModel = new DefaultComboBoxModel(var.stream().toArray());
        }

//proteinsToDisplay.addAll(((InfoPanel) infoPanelTabbedPane.getComponent(0)).getCondensedProject().getProteins());
    proteinsToDisplay = referenceExperiment.getProteins();

    proteinList.setListData(proteinsToDisplay.toArray(new PepshellProtein[proteinsToDisplay.size()]));
    proteinListScrollPane.setViewportView(proteinList);
    pdbProteinList.setListData(proteinsToDisplay.toArray(new PepshellProtein[proteinsToDisplay.size()]));
    pdbProteinListScrollPane.setViewportView(pdbProteinList);
    proteinList1.setListData(proteinsToDisplay.toArray());
    proteinListScrollPane1.setViewportView(proteinList1);
    experimentComboBox.setModel(experimentComboBoxModel);
    }


    @Override
    public void update(Observable o, Object o1) {
        if (o1 != null && o1 instanceof UpdateMessage) {
            if (((UpdateMessage) o1).informUser()) {
                JOptionPane.showMessageDialog(this, ((UpdateMessage) o1).getMessage());
            }
            if (((UpdateMessage) o1).repaint()) {
                proteinsToDisplay = proteinDetailPanel.getReferenceExperiment().getProteins();
                proteinList.setListData(proteinsToDisplay.toArray(new PepshellProtein[proteinsToDisplay.size()]));
                pdbProteinList.setListData(proteinsToDisplay.toArray(new PepshellProtein[proteinsToDisplay.size()]));
                proteinList1.setListData(proteinsToDisplay.toArray());
                this.repaint();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hydroPhobicityButtonGroup = new javax.swing.ButtonGroup();
        tabsPane = new javax.swing.JTabbedPane();
        mainViewPanel = new javax.swing.JPanel();
        proteinsOverviewPanel = new javax.swing.JPanel();
        proteinListScrollPane = new javax.swing.JScrollPane();
        proteinList = new javax.swing.JList<>();
        filterTextField = new javax.swing.JTextField();
        proteinDetailPanel = new com.compomics.pepshellstandalone.dataviewing.ProteinDetailPanel();
        pdbViewPanel = new javax.swing.JPanel();
        pdbProteinsOverviewPanel = new javax.swing.JPanel();
        pdbProteinListScrollPane = new javax.swing.JScrollPane();
        pdbProteinList = new javax.swing.JList<>();
        jmolPanel = new com.compomics.pepshellstandalone.dataviewing.JmolPanel();
        experimentComboBox = new javax.swing.JComboBox();
        statisticsPanel = new javax.swing.JPanel();
        proteinsOverviewPanel1 = new javax.swing.JPanel();
        proteinListScrollPane1 = new javax.swing.JScrollPane();
        proteinList1 = new javax.swing.JList();
        filterTextField1 = new javax.swing.JTextField();
        statisticsTabbedPane = new com.compomics.pepshellstandalone.dataviewing.StatisticsTabPane();
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
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        preferencesMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        ph5CheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        pH7CheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comparison window");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);

        tabsPane.setBackground(new java.awt.Color(255, 255, 255));
        tabsPane.setMinimumSize(new java.awt.Dimension(220, 220));
        tabsPane.setOpaque(true);
        tabsPane.addChangeListener(this::tabsPaneStateChanged);

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

        pdbProteinsOverviewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("proteins"));
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
                .addComponent(pdbProteinListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addContainerGap())
        );

        jmolPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("protein PDB detail"));

        experimentComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pdbViewPanelLayout = new javax.swing.GroupLayout(pdbViewPanel);
        pdbViewPanel.setLayout(pdbViewPanelLayout);
        pdbViewPanelLayout.setHorizontalGroup(
            pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdbViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pdbProteinsOverviewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jmolPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                    .addGroup(pdbViewPanelLayout.createSequentialGroup()
                        .addComponent(experimentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pdbViewPanelLayout.setVerticalGroup(
            pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdbViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pdbViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pdbProteinsOverviewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pdbViewPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(experimentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jmolPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        statisticsTabbedPane.setGraphData(null);

        javax.swing.GroupLayout statisticsPanelLayout = new javax.swing.GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proteinsOverviewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statisticsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                .addContainerGap())
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proteinsOverviewPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statisticsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsPane.addTab("statistics", statisticsPanel);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        fileMenu.setText("File");

        newViewMenuItem.setText("load new view ...");
        newViewMenuItem.addActionListener(this::newViewMenuItemActionPerformed);
        fileMenu.add(newViewMenuItem);

        saveToCsvMenuItem.setText("save to CSV file");
        fileMenu.add(saveToCsvMenuItem);

        saveToExcelMenuItem.setText("save to excel file");
        fileMenu.add(saveToExcelMenuItem);

        exportOptionsMenuItem.setText("export options ...");
        exportOptionsMenuItem.addActionListener(this::exportOptionsMenuItemActionPerformed);
        fileMenu.add(exportOptionsMenuItem);

        jMenuBar1.add(fileMenu);

        ViewOptionsMenu.setText("View options");

        accessionMenuItem.setText("accession");

        uniProtRadioButtonMenuItem.setSelected(true);
        uniProtRadioButtonMenuItem.setText("uniprot");
        uniProtRadioButtonMenuItem.addActionListener(this::uniProtRadioButtonMenuItemActionPerformed);
        accessionMenuItem.add(uniProtRadioButtonMenuItem);

        originalAccessionRadioButtonMenuItem.setSelected(true);
        originalAccessionRadioButtonMenuItem.setText("original accession");
        originalAccessionRadioButtonMenuItem.addActionListener(this::originalAccessionRadioButtonMenuItemActionPerformed);
        accessionMenuItem.add(originalAccessionRadioButtonMenuItem);

        setAccessionMaskOption.setText("set mask for accessions ...");
        setAccessionMaskOption.addActionListener(this::setAccessionMaskOptionActionPerformed);
        accessionMenuItem.add(setAccessionMaskOption);

        ViewOptionsMenu.add(accessionMenuItem);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("show only proteins found in reference");
        jCheckBoxMenuItem1.addActionListener(this::jCheckBoxMenuItem1ActionPerformed);
        ViewOptionsMenu.add(jCheckBoxMenuItem1);

        jMenuBar1.add(ViewOptionsMenu);

        preferencesMenu.setText("Preferences...");
        preferencesMenu.addActionListener(evt -> {
            //put in a preferencesmenu
        });

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("pH for hydrofobicity");

        ph5CheckBoxMenuItem.setBackground(new java.awt.Color(255, 255, 255));
        hydroPhobicityButtonGroup.add(ph5CheckBoxMenuItem);
        ph5CheckBoxMenuItem.setText("pH 5");
        ph5CheckBoxMenuItem.addActionListener(evt -> ph5CheckBoxMenuItemActionPerformed(evt));
        jMenu1.add(ph5CheckBoxMenuItem);

        pH7CheckBoxMenuItem2.setBackground(new java.awt.Color(255, 255, 255));
        hydroPhobicityButtonGroup.add(pH7CheckBoxMenuItem2);
        pH7CheckBoxMenuItem2.setSelected(true);
        pH7CheckBoxMenuItem2.setText("pH 7");
        pH7CheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pH7CheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(pH7CheckBoxMenuItem2);

        preferencesMenu.add(jMenu1);

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

        new SwingWorker<Boolean, Void>() {
            @Override
            public Boolean doInBackground() {

                    jmolPanel.setPDBProtein(pdbProteinList.getSelectedValue());
                return true;
            }
        }.execute();

    }//GEN-LAST:event_pdbProteinListMouseClicked

    private void proteinListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseReleased
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            PepshellProtein pepshellProteinOfInterest = proteinList.getSelectedValue();
            if (ProgramVariables.USEINTERNETSOURCES && pepshellProteinOfInterest.getProteinSequence().isEmpty()) {
                try {
                    pepshellProteinOfInterest.setSequence(WebDAO.fetchSequence(pepshellProteinOfInterest.getVisibleAccession()));
                } catch (IOException | ConversionException ex) {
                    ProgramVariables.exceptionBus.post(new ExceptionEvent("could not fetch protein sequence from uniprot for acession "+pepshellProteinOfInterest.getVisibleAccession(),true, Level.WARN));
                }
            }
            proteinDetailPanel.updateProteinGraphics(pepshellProteinOfInterest);
        }catch (NullPointerException ex) {
            if (proteinList.getSelectedValue() == null){
                //empty selected value
            }
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_proteinListMouseReleased

    private void proteinListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinListMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                JOptionPane.showMessageDialog(this, "opening uniprot accession");
                Desktop.getDesktop().browse(new URI(DataRetrievalPropertyEnum.PROTEININFOWEBSITE.getDefaultValue() + AccessionConverter.toUniprot(proteinList.getSelectedValue().getVisibleAccession())));
            } catch (URISyntaxException | IOException | ConversionException ex) {
                ProgramVariables.exceptionBus.post(new ExceptionEvent("could not open website for accession: " + proteinList.getSelectedValue().getVisibleAccession(),true,Level.WARN));
            }
        }
    }//GEN-LAST:event_proteinListMouseClicked

    private void uniProtRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uniProtRadioButtonMenuItemActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new SwingWorker<Boolean, Void>() {
            @Override
            public Boolean doInBackground() throws Exception {
                List<PepshellProtein> failedProteins = new ArrayList<>();
                for (PepshellProtein aPepshellProtein : proteinsToDisplay) {
                    try {
                        aPepshellProtein.setVisibleAccession(AccessionConverter.toUniprot(aPepshellProtein.getVisibleAccession()));
                        proteinList.revalidate();
                        proteinList.repaint();
                    } catch (IOException | ConversionException ex) {
                        failedProteins.add(aPepshellProtein);
                    }

                }
                if (!failedProteins.isEmpty()){
                    ProgramVariables.exceptionBus.post(new ExceptionEvent(failedProteins.stream().map(PepshellProtein::getVisibleAccession).collect(Collectors.joining("\n")),true,Level.WARN));
                }
                proteinList.revalidate();
                proteinList.repaint();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return true;
            }
        }.execute();
    }//GEN-LAST:event_uniProtRadioButtonMenuItemActionPerformed

    private void originalAccessionRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originalAccessionRadioButtonMenuItemActionPerformed
        for (PepshellProtein aPepshellProtein : proteinsToDisplay) {
            aPepshellProtein.setOriginalAccession(aPepshellProtein.getOriginalAccession());
        }
        proteinList.repaint();
        proteinList1.repaint();
        pdbProteinList.repaint();
    }//GEN-LAST:event_originalAccessionRadioButtonMenuItemActionPerformed

    private void filterTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTextFieldKeyTyped
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
            List<PepshellProtein> var = filter.filterProtein(proteinsToDisplay, searchRegex);
            proteinList.setListData(var.toArray(new PepshellProtein[var.size()]));
        } else {
            proteinList.setListData(proteinsToDisplay.toArray(new PepshellProtein[proteinsToDisplay.size()]));
        }
    }//GEN-LAST:event_filterTextFieldKeyTyped

    private void exportOptionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportOptionsMenuItemActionPerformed
        //new exportoptionspanel
    }//GEN-LAST:event_exportOptionsMenuItemActionPerformed

    private void newViewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newViewMenuItemActionPerformed
        int selection = JOptionPane.showConfirmDialog(null, "Select a new set of experiments?", "new view", JOptionPane.YES_NO_OPTION);
        if (selection == JOptionPane.YES_OPTION) {
            CombinedLoginDialog combinedLoginDialog = new CombinedLoginDialog();
            combinedLoginDialog.pack();
            combinedLoginDialog.setLocationRelativeTo(null);
            this.dispose();
            combinedLoginDialog.setVisible(true);
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
        statisticsTabbedPane.setGraphData((PepshellProtein) proteinList1.getSelectedValue());
    }//GEN-LAST:event_proteinList1MouseReleased

    private void filterTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterTextField1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTextField1FocusGained

    private void filterTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTextField1KeyTyped

    private void ph5CheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ph5CheckBoxMenuItemActionPerformed
        // TODO add your handling code here:
        if (ph5CheckBoxMenuItem.isSelected()) {
            //HydrophobicityMaps.setCurrentHydrophobicityMap(HydrophobicityMaps.hydrophobicityMapPh5);
            proteinDetailPanel.repaint();
        }
    }//GEN-LAST:event_ph5CheckBoxMenuItemActionPerformed

    private void pH7CheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pH7CheckBoxMenuItem2ActionPerformed
        // TODO add your handling code here:
        if (pH7CheckBoxMenuItem2.isSelected()) {
            HydrophobicityMaps.setCurrentHydrophobicityMap(HydrophobicityMaps.hydrophobicityMapPh7);
            proteinDetailPanel.repaint();
        }

    }//GEN-LAST:event_pH7CheckBoxMenuItem2ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMenuItem1.isSelected()) {
            PepshellProtein[] listToDisplay = proteinDetailPanel.getReferenceExperiment().getProteins().toArray(new PepshellProtein[proteinDetailPanel.getReferenceExperiment().getProteins().size()]);
            proteinList.setListData(listToDisplay);
            pdbProteinList.setListData(listToDisplay);
            proteinList1.setListData(listToDisplay);
        } else {
            proteinDetailPanel.getReferenceExperiment().getProteins();
            //proteinDetailPanel.get

        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ViewOptionsMenu;
    private javax.swing.JMenu accessionMenuItem;
    private javax.swing.JComboBox experimentComboBox;
    private javax.swing.JMenuItem exportOptionsMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JTextField filterTextField1;
    private javax.swing.ButtonGroup hydroPhobicityButtonGroup;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private com.compomics.pepshellstandalone.dataviewing.JmolPanel jmolPanel;
    private javax.swing.JPanel mainViewPanel;
    private javax.swing.JMenuItem newViewMenuItem;
    private javax.swing.JRadioButtonMenuItem originalAccessionRadioButtonMenuItem;
    private javax.swing.JCheckBoxMenuItem pH7CheckBoxMenuItem2;
    private javax.swing.JList<PepshellProtein> pdbProteinList;
    private javax.swing.JScrollPane pdbProteinListScrollPane;
    private javax.swing.JPanel pdbProteinsOverviewPanel;
    private javax.swing.JPanel pdbViewPanel;
    private javax.swing.JCheckBoxMenuItem ph5CheckBoxMenuItem;
    private javax.swing.JMenu preferencesMenu;
    private com.compomics.pepshellstandalone.dataviewing.ProteinDetailPanel proteinDetailPanel;
    private javax.swing.JList<PepshellProtein> proteinList;
    private javax.swing.JList proteinList1;
    private javax.swing.JScrollPane proteinListScrollPane;
    private javax.swing.JScrollPane proteinListScrollPane1;
    private javax.swing.JPanel proteinsOverviewPanel;
    private javax.swing.JPanel proteinsOverviewPanel1;
    private javax.swing.JMenuItem saveToCsvMenuItem;
    private javax.swing.JMenuItem saveToExcelMenuItem;
    private javax.swing.JMenuItem setAccessionMaskOption;
    private javax.swing.JPanel statisticsPanel;
    private com.compomics.pepshellstandalone.dataviewing.StatisticsTabPane statisticsTabbedPane;
    private javax.swing.JTabbedPane tabsPane;
    private javax.swing.JRadioButtonMenuItem uniProtRadioButtonMenuItem;
    // End of variables declaration//GEN-END:variables

}
