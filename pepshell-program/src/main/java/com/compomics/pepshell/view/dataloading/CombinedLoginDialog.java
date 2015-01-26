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

package com.compomics.pepshell.view.dataloading;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.ExternalStructureDataSource;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.LinkDb;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.properties.DatabaseProperties;
import com.compomics.pepshell.model.Property;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import com.compomics.pepshell.model.enums.DataModeEnum;
import com.compomics.pepshell.view.dataloading.frames.ExperimentSelectionFrame;
import com.compomics.pepshell.view.dataloading.frames.FileSelectionFrame;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy Maddelein
 */
public class CombinedLoginDialog extends javax.swing.JDialog {

    private static final String MODE_SELECTION_CARD = "modeSelection";
    private static final String DB_LOGIN_CARD = "dbLogin";
    private static final String LINK_DB_LOGIN_CARD = "linkDbLogin";

    /**
     *
     */
    public CombinedLoginDialog() {
        initComponents();
        skipStructureDbLoginButton.setVisible(false);
        init();
    }

    /**
     * Init the component.
     *
     */
    private void init() {
        //select dbSelectionRadioButton
        dbSelectionRadioButton.setSelected(true);
        fastaSelectionRadioButton.setEnabled(true);

        //set card names
        modeSelectionPanel.setName(MODE_SELECTION_CARD);
        dbLoginPanel.setName(DB_LOGIN_CARD);
        linkDbLoginPanel.setName(LINK_DB_LOGIN_CARD);

        //add action listeners
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentCardName = getCurrentCardName();
                switch (currentCardName) {
                    case MODE_SELECTION_CARD:
                        if (dbSelectionRadioButton.isSelected()) {
                            initDbLogin();
                            //go to the next card
                            getCardLayout().next(topPanel);
                        } else if (fastaSelectionRadioButton.isSelected()) {
                            loadFileBasedExperiments();
                        }
                        break;
                    case DB_LOGIN_CARD:
                        onDbLogin();
                        dbLoginPanel.getUsernameTextField().requestFocus();
                        break;
                    case LINK_DB_LOGIN_CARD:
                        onLinkDbLogin();
                        linkDbLoginPanel.getUsernameTextField().requestFocus();
                        break;
                    default:
                        break;
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Init the database login panels
     *
     */
    private void initDbLogin() {
        linkDbLoginPanel.getLoginInfoLabel().setText("please enter your link database login credentials");

        dbLoginPanel.getUsernameTextField().setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.DBUSERNAME.getKey()));
        dbLoginPanel.getUrlTextField().setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.DBURL.getKey()));
        dbLoginPanel.getDatabaseNameTextField().setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.DBNAME.getKey()));

        linkDbLoginPanel.getUsernameTextField().setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.LINKDBUSERNAME.getKey()));
        linkDbLoginPanel.getUrlTextField().setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.LINKDBURL.getKey()));
        linkDbLoginPanel.getDatabaseNameTextField().setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.LINKDBNAME.getKey()));

        //add action listeners        
        dbLoginPanel.getDatabaseNameTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    onDbLogin();
                } else if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbLoginPanel.getUrlTextField().requestFocus();
                    dbLoginPanel.getUrlTextField().selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_TAB) {
                    proceedButton.requestFocus();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        //add action listeners
        linkDbLoginPanel.getUsernameTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbLoginPanel.getPasswordField().requestFocus();
                    linkDbLoginPanel.getPasswordField().selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        //@TODO add shift tab
        linkDbLoginPanel.getPasswordField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbLoginPanel.getUsernameTextField().requestFocus();
                    linkDbLoginPanel.getUsernameTextField().selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbLoginPanel.getUrlTextField().requestFocus();
                    linkDbLoginPanel.getUrlTextField().selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        linkDbLoginPanel.getUrlTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbLoginPanel.getPasswordField().requestFocus();
                    linkDbLoginPanel.getPasswordField().selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbLoginPanel.getDatabaseNameTextField().requestFocus();
                    linkDbLoginPanel.getDatabaseNameTextField().selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        linkDbLoginPanel.getDatabaseNameTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    onLinkDbLogin();
                } else if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbLoginPanel.getUrlTextField().requestFocus();
                    linkDbLoginPanel.getUrlTextField().selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_TAB) {
                    proceedButton.requestFocus();
                } else {
                    super.keyTyped(e);
                }
            }
        });
    }

    /**
     * Validate the user input and try to log in with the given credentials.
     *
     */
    private void onDbLogin() {
        if (validateDbLogin()) {
            try {
                DbConnectionController.createExperimentDbConnection(dbLoginPanel.getUsernameTextField().getText(), new String(dbLoginPanel.getPasswordField().getPassword()), dbLoginPanel.getUrlTextField().getText(), dbLoginPanel.getDatabaseNameTextField().getText());
                DataModeController.getInstance().selectDataHandler();
                if (DataModeController.getInstance().getDb() == DataModeEnum.MSLIMS || DataModeController.getInstance().getDb() == DataModeEnum.COLIMS) {
                    JOptionPane.showMessageDialog(this, "login was successful to data db");
                    storeDbCredentials(dbLoginPanel.getStoreCredentialsCheckBox().isSelected());
                    //go to the next card
                    getCardLayout().next(topPanel);
                    skipStructureDbLoginButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "could not identify the database scheme");
                }

            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
                JOptionPane.showMessageDialog(this, "there has been an error while trying to log in.\n" + sqle.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please provide all the fields.");
        }
    }

    /**
     * Validate the db login fields.
     *
     * @return true if valid
     */
    private boolean validateDbLogin() {
        boolean valid = true;

        if (dbLoginPanel.getUsernameTextField().getText().equals("") || new String(dbLoginPanel.getPasswordField().getPassword()).equals("") || dbLoginPanel.getUrlTextField().getText().equals("") || dbLoginPanel.getDatabaseNameTextField().getText().equals("")) {
            valid = false;
        }

        return valid;
    }

    /**
     * Validate the user input and try to log in with the given credentials.
     *
     */
    private void onLinkDbLogin() {
        if (validateLinkDbLogin()) {
            try {
                DbConnectionController.createStructDbConnection(linkDbLoginPanel.getUsernameTextField().getText(), new String(linkDbLoginPanel.getPasswordField().getPassword()), linkDbLoginPanel.getUrlTextField().getText(), linkDbLoginPanel.getDatabaseNameTextField().getText());
                JOptionPane.showMessageDialog(this, "login was successful to link db");
                storeLinkDbCredentials(linkDbLoginPanel.getStoreCredentialsCheckBox().isSelected());
                DataModeController.getInstance().getDb().getDataMode().setStructureDataSource(new LinkDb());
                this.dispose();
                new ExperimentSelectionFrame();
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
                JOptionPane.showMessageDialog(this, "there has been an error while trying to log in.\n" + sqle.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please provide all the fields.");
        }
    }

    /**
     * Validate the link db login fields.
     *
     * @return true if valid
     */
    private boolean validateLinkDbLogin() {
        boolean valid = true;

        if (linkDbLoginPanel.getUsernameTextField().getText().equals("") || new String(linkDbLoginPanel.getPasswordField().getPassword()).equals("") || linkDbLoginPanel.getUrlTextField().getText().equals("") || linkDbLoginPanel.getDatabaseNameTextField().getText().equals("")) {
            valid = false;
        }

        return valid;
    }

    /**
     * Store the database credentials if necessary
     *
     * @param store
     */
    void storeDbCredentials(boolean store) {
        if (store) {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.DBUSERNAME, dbLoginPanel.getUsernameTextField().getText()));
                    this.add(new Property(DataBasePropertyEnum.DBURL, dbLoginPanel.getUrlTextField().getText()));
                    this.add(new Property(DataBasePropertyEnum.DBNAME, dbLoginPanel.getDatabaseNameTextField().getText()));
                }
            });
        } else {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.DBUSERNAME, DataBasePropertyEnum.DBUSERNAME.getDefaultValue()));
                    this.add(new Property(DataBasePropertyEnum.DBURL, DataBasePropertyEnum.DBURL.getDefaultValue()));
                    this.add(new Property(DataBasePropertyEnum.DBNAME, DataBasePropertyEnum.DBNAME.getDefaultValue()));
                }
            });
        }
    }

    /**
     * Store the link database credentials if necessary
     *
     * @param store
     */
    void storeLinkDbCredentials(boolean store) {
        if (store) {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.LINKDBUSERNAME, linkDbLoginPanel.getUsernameTextField().getText()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBURL, linkDbLoginPanel.getUrlTextField().getText()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBNAME, linkDbLoginPanel.getDatabaseNameTextField().getText()));
                }
            });
        } else {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.DBUSERNAME, DataBasePropertyEnum.LINKDBUSERNAME.getDefaultValue()));
                    this.add(new Property(DataBasePropertyEnum.DBURL, DataBasePropertyEnum.LINKDBURL.getDefaultValue()));
                    this.add(new Property(DataBasePropertyEnum.DBNAME, DataBasePropertyEnum.LINKDBNAME.getDefaultValue()));
                }
            });
        }
    }

    /**
     * Get the card layout.
     *
     * @return the CardLayout
     */
    private CardLayout getCardLayout() {
        return (CardLayout) topPanel.getLayout();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modeSelectionButtonGroup = new javax.swing.ButtonGroup();
        parentPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        modeSelectionPanel = new javax.swing.JPanel();
        dataSourceSelectionLabel = new javax.swing.JLabel();
        dbSelectionRadioButton = new javax.swing.JRadioButton();
        fastaSelectionRadioButton = new javax.swing.JRadioButton();
        dbLoginPanel = new com.compomics.pepshell.view.dataloading.LoginPanel();
        linkDbLoginPanel = new com.compomics.pepshell.view.dataloading.StructureDbLoginPanel();
        bottomPanel = new javax.swing.JPanel();
        proceedButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        skipStructureDbLoginButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        CPDTInstallCheckBox = new javax.swing.JCheckBoxMenuItem();
        alterMemorySettingsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("login");
        setModal(true);

        parentPanel.setBackground(new java.awt.Color(255, 255, 255));

        topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        topPanel.setOpaque(false);
        topPanel.setLayout(new java.awt.CardLayout());

        modeSelectionPanel.setName("modeSelection"); // NOI18N
        modeSelectionPanel.setOpaque(false);

        dataSourceSelectionLabel.setText("Select a data source");

        modeSelectionButtonGroup.add(dbSelectionRadioButton);
        dbSelectionRadioButton.setText("database connection");
        dbSelectionRadioButton.setOpaque(false);

        modeSelectionButtonGroup.add(fastaSelectionRadioButton);
        fastaSelectionRadioButton.setText("file based");
        fastaSelectionRadioButton.setOpaque(false);

        javax.swing.GroupLayout modeSelectionPanelLayout = new javax.swing.GroupLayout(modeSelectionPanel);
        modeSelectionPanel.setLayout(modeSelectionPanelLayout);
        modeSelectionPanelLayout.setHorizontalGroup(
            modeSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modeSelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modeSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modeSelectionPanelLayout.createSequentialGroup()
                        .addComponent(fastaSelectionRadioButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(modeSelectionPanelLayout.createSequentialGroup()
                        .addComponent(dbSelectionRadioButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(modeSelectionPanelLayout.createSequentialGroup()
                        .addComponent(dataSourceSelectionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                        .addGap(75, 75, 75))))
        );
        modeSelectionPanelLayout.setVerticalGroup(
            modeSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modeSelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataSourceSelectionLabel)
                .addGap(18, 18, 18)
                .addComponent(dbSelectionRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fastaSelectionRadioButton)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        topPanel.add(modeSelectionPanel, "");
        topPanel.add(dbLoginPanel, "");
        dbLoginPanel.getAccessibleContext().setAccessibleName("");

        topPanel.add(linkDbLoginPanel, "dbLinkLogin");
        linkDbLoginPanel.getAccessibleContext().setAccessibleName("linkDbLogin");

        bottomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        bottomPanel.setOpaque(false);

        proceedButton.setText("proceed");
        proceedButton.setMaximumSize(new java.awt.Dimension(100, 25));
        proceedButton.setMinimumSize(new java.awt.Dimension(100, 25));
        proceedButton.setPreferredSize(new java.awt.Dimension(100, 25));
        proceedButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                proceedButtonKeyTyped(evt);
            }
        });

        cancelButton.setText("cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(80, 25));

        skipStructureDbLoginButton.setText("skip login");
        skipStructureDbLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipStructureDbLoginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(skipStructureDbLoginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skipStructureDbLoginButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                    .addComponent(bottomPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("Startup Options");

        CPDTInstallCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        CPDTInstallCheckBox.setSelected(true);
        CPDTInstallCheckBox.setText("Install CPDT libraries");
        jMenu1.add(CPDTInstallCheckBox);

        alterMemorySettingsMenuItem.setText("alter memory settings...");
        jMenu1.add(alterMemorySettingsMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void proceedButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proceedButtonKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            proceedButton.doClick();
        }
    }//GEN-LAST:event_proceedButtonKeyTyped

    private void skipStructureDbLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipStructureDbLoginButtonActionPerformed
        // TODO add your handling code here:
        DataModeController.getInstance().getDb().getDataMode().setStructureDataSource(new ExternalStructureDataSource());
        this.dispose();
        new ExperimentSelectionFrame();

    }//GEN-LAST:event_skipStructureDbLoginButtonActionPerformed

    /**
     * Get the name of the component currently visible in the card layout
     *
     * @return the component name
     */
    private String getCurrentCardName() {
        String currentCardName = "";

        for (Component component : topPanel.getComponents()) {
            if (component.isVisible()) {
                currentCardName = component.getName();
                break;
            }
        }

        return currentCardName;
    }

    private void loadFileBasedExperiments() {
        this.dispose();
        new FileSelectionFrame();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem CPDTInstallCheckBox;
    private javax.swing.JMenuItem alterMemorySettingsMenuItem;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel dataSourceSelectionLabel;
    private com.compomics.pepshell.view.dataloading.LoginPanel dbLoginPanel;
    private javax.swing.JRadioButton dbSelectionRadioButton;
    private javax.swing.JRadioButton fastaSelectionRadioButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private com.compomics.pepshell.view.dataloading.StructureDbLoginPanel linkDbLoginPanel;
    private javax.swing.ButtonGroup modeSelectionButtonGroup;
    private javax.swing.JPanel modeSelectionPanel;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JButton proceedButton;
    private javax.swing.JButton skipStructureDbLoginButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
