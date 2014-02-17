package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.properties.DatabaseProperties;
import com.compomics.pepshell.model.Property;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Davy
 */
public class LoginDialog2 extends javax.swing.JDialog {

    private static final String MODE_SELECTION_CARD = "modeSelection";
    private static final String DB_LOGIN_CARD = "dbLogin";
    private static final String LINK_DB_LOGIN_CARD = "linkDbLogin";

    /**
     *
     */
    public LoginDialog2() {
        initComponents();

        init();
    }

    /**
     * Init the component.
     *
     */
    private void init() {
        //select dbSelectionRadioButton
        dbSelectionRadioButton.setSelected(true);
        //@TODO enable this again as soon as it works
        fastaSelectionRadioButton.setEnabled(false);
        
        //disable finish button
        finishButton.setEnabled(false);

        dbUrlTextField.setFocusTraversalKeysEnabled(false);
        dbPasswordField.setFocusTraversalKeysEnabled(false);

        //add action listeners
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentCardName = getCurrentCardName();
                switch (currentCardName) {
                    case MODE_SELECTION_CARD:
                        if (dbSelectionRadioButton.isSelected()) {
                            initDbLogin();
                        } else if (fastaSelectionRadioButton.isSelected()) {
                            //do nothing
                        }
                        break;
                    case DB_LOGIN_CARD:
                        
                        break;
                    case LINK_DB_LOGIN_CARD:

                        break;
                    default:
                        break;
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog2.this.dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Init the database login panels
     *
     */
    private void initDbLogin() {
        dbUsernameTextField.setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.DBUSERNAME.getKey()));
        dbUrlTextField.setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.DBURL.getKey()));
        dbDatabaseNameTextField.setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.DBURL.getKey()));
        
        linkDbUsernameTextField.setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.LINKDBUSERNAME.getKey()));
        linkDbUrlTextField.setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.LINKDBURL.getKey()));
        linkDbDatabaseNameTextField.setText(DatabaseProperties.getInstance().getProperties().getProperty(DataBasePropertyEnum.LINKDBNAME.getKey()));
        
        //add action listeners
        dbUsernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbPasswordField.requestFocus();
                    dbPasswordField.selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        //@TODO add shift tab
        dbPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbUsernameTextField.requestFocus();
                    dbUsernameTextField.selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbUrlTextField.requestFocus();
                    dbUrlTextField.selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        dbUrlTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbPasswordField.requestFocus();
                    dbPasswordField.selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbDatabaseNameTextField.requestFocus();
                    dbDatabaseNameTextField.selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        dbDatabaseNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    executeLogin();
                } else if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbUrlTextField.requestFocus();
                    dbUrlTextField.selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_TAB) {
                    loginButton.requestFocus();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    executeLogin();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        //add action listeners
        linkDbUsernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbPasswordField.requestFocus();
                    linkDbPasswordField.selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        //@TODO add shift tab
        linkDbPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbUsernameTextField.requestFocus();
                    linkDbUsernameTextField.selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbUrlTextField.requestFocus();
                    linkDbUrlTextField.selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        linkDbUrlTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbPasswordField.requestFocus();
                    linkDbPasswordField.selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
                    linkDbDatabaseNameTextField.requestFocus();
                    linkDbDatabaseNameTextField.selectAll();
                } else {
                    super.keyTyped(e);
                }
            }
        });

        linkDbDatabaseNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    executeLogin();
                } else if (e.isShiftDown() && e.getKeyChar() == KeyEvent.VK_TAB) {
                    dbUrlTextField.requestFocus();
                    dbUrlTextField.selectAll();
                } else if (e.getKeyChar() == KeyEvent.VK_TAB) {
                    loginButton.requestFocus();
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    executeLogin();
                } else {
                    super.keyTyped(e);
                }
            }
        });
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
        dbLoginPanel = new javax.swing.JPanel();
        dbUsernameTextField = new javax.swing.JTextField();
        dbUrlTextField = new javax.swing.JTextField();
        dbUsernameLabel = new javax.swing.JLabel();
        dbPasswordLabel = new javax.swing.JLabel();
        dbUrlLabel = new javax.swing.JLabel();
        dbLoginInfoLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        dbPasswordField = new javax.swing.JPasswordField();
        exitButton = new javax.swing.JButton();
        dbDatabaseNameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        storeDbCredentialsCheckBox = new javax.swing.JCheckBox();
        linkDbLoginPanel = new javax.swing.JPanel();
        linkDbUsernameTextField = new javax.swing.JTextField();
        linkDbUrlTextField = new javax.swing.JTextField();
        linkDbUsernameLabel = new javax.swing.JLabel();
        linkDbPasswordLabel = new javax.swing.JLabel();
        linkDbUrlLabel = new javax.swing.JLabel();
        linkDbLoginInfoLabel = new javax.swing.JLabel();
        loginButton1 = new javax.swing.JButton();
        linkDbPasswordField = new javax.swing.JPasswordField();
        exitButton1 = new javax.swing.JButton();
        linkDbDatabaseNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        storeLinkDbCredentialsCheckBox = new javax.swing.JCheckBox();
        bottomPanel = new javax.swing.JPanel();
        proceedButton = new javax.swing.JButton();
        finishButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("login");
        setModal(true);

        parentPanel.setBackground(new java.awt.Color(255, 255, 255));
        parentPanel.setOpaque(false);

        topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        topPanel.setOpaque(false);
        topPanel.setLayout(new java.awt.CardLayout());

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
                        .addComponent(dataSourceSelectionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
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
                .addContainerGap(133, Short.MAX_VALUE))
        );

        topPanel.add(modeSelectionPanel, "modeSelection");

        dbLoginPanel.setOpaque(false);

        dbUsernameLabel.setText("username");

        dbPasswordLabel.setText("password");

        dbUrlLabel.setText("database URL");

        dbLoginInfoLabel.setText("Enter your database login credentials");

        loginButton.setText("Login");
        loginButton.setMaximumSize(new java.awt.Dimension(85, 23));
        loginButton.setMinimumSize(new java.awt.Dimension(85, 23));
        loginButton.setPreferredSize(new java.awt.Dimension(85, 23));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                loginButtonMouseReleased(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.setMaximumSize(new java.awt.Dimension(85, 23));
        exitButton.setMinimumSize(new java.awt.Dimension(85, 23));
        exitButton.setPreferredSize(new java.awt.Dimension(85, 23));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                exitButtonMouseReleased(evt);
            }
        });

        jLabel1.setText("database name");

        storeDbCredentialsCheckBox.setSelected(true);
        storeDbCredentialsCheckBox.setText("remember me");
        storeDbCredentialsCheckBox.setOpaque(false);

        javax.swing.GroupLayout dbLoginPanelLayout = new javax.swing.GroupLayout(dbLoginPanel);
        dbLoginPanel.setLayout(dbLoginPanelLayout);
        dbLoginPanelLayout.setHorizontalGroup(
            dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dbLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dbLoginPanelLayout.createSequentialGroup()
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dbPasswordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dbUsernameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dbUrlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dbPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbDatabaseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storeDbCredentialsCheckBox, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(dbLoginPanelLayout.createSequentialGroup()
                        .addComponent(dbLoginInfoLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dbLoginPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dbLoginPanelLayout.setVerticalGroup(
            dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dbLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dbLoginPanelLayout.createSequentialGroup()
                        .addComponent(dbLoginInfoLabel)
                        .addGap(13, 13, 13)
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dbUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbUsernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dbPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbPasswordLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dbUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbUrlLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dbDatabaseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(storeDbCredentialsCheckBox)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dbLoginPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(dbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        topPanel.add(dbLoginPanel, "dbLogin");

        linkDbLoginPanel.setOpaque(false);

        linkDbUsernameLabel.setText("username");

        linkDbPasswordLabel.setText("password");

        linkDbUrlLabel.setText("database URL");

        linkDbLoginInfoLabel.setText("Enter your link database login credentials");

        loginButton1.setText("Login");
        loginButton1.setMaximumSize(new java.awt.Dimension(85, 23));
        loginButton1.setMinimumSize(new java.awt.Dimension(85, 23));
        loginButton1.setPreferredSize(new java.awt.Dimension(85, 23));
        loginButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                loginButton1MouseReleased(evt);
            }
        });

        exitButton1.setText("Exit");
        exitButton1.setMaximumSize(new java.awt.Dimension(85, 23));
        exitButton1.setMinimumSize(new java.awt.Dimension(85, 23));
        exitButton1.setPreferredSize(new java.awt.Dimension(85, 23));
        exitButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                exitButton1MouseReleased(evt);
            }
        });

        jLabel2.setText("database name");

        storeLinkDbCredentialsCheckBox.setSelected(true);
        storeLinkDbCredentialsCheckBox.setText("remember me");
        storeLinkDbCredentialsCheckBox.setOpaque(false);

        javax.swing.GroupLayout linkDbLoginPanelLayout = new javax.swing.GroupLayout(linkDbLoginPanel);
        linkDbLoginPanel.setLayout(linkDbLoginPanelLayout);
        linkDbLoginPanelLayout.setHorizontalGroup(
            linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linkDbLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, linkDbLoginPanelLayout.createSequentialGroup()
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linkDbPasswordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(linkDbUsernameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(linkDbUrlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linkDbPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linkDbUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linkDbUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linkDbDatabaseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storeLinkDbCredentialsCheckBox, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(linkDbLoginPanelLayout.createSequentialGroup()
                        .addComponent(linkDbLoginInfoLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, linkDbLoginPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(loginButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        linkDbLoginPanelLayout.setVerticalGroup(
            linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linkDbLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(linkDbLoginPanelLayout.createSequentialGroup()
                        .addComponent(linkDbLoginInfoLabel)
                        .addGap(13, 13, 13)
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(linkDbUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linkDbUsernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(linkDbPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linkDbPasswordLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(linkDbUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linkDbUrlLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(linkDbDatabaseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(storeLinkDbCredentialsCheckBox)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, linkDbLoginPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(linkDbLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loginButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        topPanel.add(linkDbLoginPanel, "linkDbLogin");

        bottomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        bottomPanel.setOpaque(false);

        proceedButton.setText("proceed");
        proceedButton.setMaximumSize(new java.awt.Dimension(80, 25));
        proceedButton.setMinimumSize(new java.awt.Dimension(80, 25));
        proceedButton.setPreferredSize(new java.awt.Dimension(80, 25));

        finishButton.setText("finish");
        finishButton.setMaximumSize(new java.awt.Dimension(80, 25));
        finishButton.setMinimumSize(new java.awt.Dimension(80, 25));
        finishButton.setPreferredSize(new java.awt.Dimension(80, 25));

        cancelButton.setText("cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finishButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(finishButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bottomPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseReleased
        if (dbUsernameTextField.getText().equals("") || new String(dbPasswordField.getPassword()).equals("") || dbUrlTextField.getText().equals("") || dbDatabaseNameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please provide all the fields");
        } else {
            executeLogin();
            storeCredentials(storeDbCredentialsCheckBox.isSelected());
            this.dispose();
        }
    }//GEN-LAST:event_loginButtonMouseReleased

    private void exitButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseReleased
        this.dispose();
    }//GEN-LAST:event_exitButtonMouseReleased

    private void loginButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButton1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_loginButton1MouseReleased

    private void exitButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButton1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_exitButton1MouseReleased

    void executeLogin() {
        try {
            DbConnectionController.createConnection(dbUsernameTextField.getText(), new String(dbPasswordField.getPassword()), dbUrlTextField.getText(), dbDatabaseNameTextField.getText());
            DataModeController.checkDbScheme();
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
            JOptionPane.showMessageDialog(this, "there has been an error while trying to log in.\n" + sqle.getMessage());
        }
    }

    void storeCredentials(boolean store) {
        if (store) {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.DBUSERNAME, dbUsernameTextField.getText()));
                    this.add(new Property(DataBasePropertyEnum.DBURL, dbUrlTextField.getText()));
                    this.add(new Property(DataBasePropertyEnum.DBNAME, dbDatabaseNameTextField.getText()));
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
     * Get the name of the component currently visible in the card layout
     *
     * @return the component name
     */
    private String getCurrentCardName() {
        String currentCardName = "";

        for (Component component : topPanel.getComponents()) {
            if (component.isVisible()) {
                currentCardName = ((JPanel) component).getName();
            }
            break;
        }

        return currentCardName;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel dataSourceSelectionLabel;
    javax.swing.JTextField dbDatabaseNameTextField;
    javax.swing.JLabel dbLoginInfoLabel;
    private javax.swing.JPanel dbLoginPanel;
    javax.swing.JPasswordField dbPasswordField;
    private javax.swing.JLabel dbPasswordLabel;
    private javax.swing.JRadioButton dbSelectionRadioButton;
    private javax.swing.JLabel dbUrlLabel;
    javax.swing.JTextField dbUrlTextField;
    private javax.swing.JLabel dbUsernameLabel;
    javax.swing.JTextField dbUsernameTextField;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton exitButton1;
    private javax.swing.JRadioButton fastaSelectionRadioButton;
    private javax.swing.JButton finishButton;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    javax.swing.JTextField linkDbDatabaseNameTextField;
    javax.swing.JLabel linkDbLoginInfoLabel;
    private javax.swing.JPanel linkDbLoginPanel;
    javax.swing.JPasswordField linkDbPasswordField;
    private javax.swing.JLabel linkDbPasswordLabel;
    private javax.swing.JLabel linkDbUrlLabel;
    javax.swing.JTextField linkDbUrlTextField;
    private javax.swing.JLabel linkDbUsernameLabel;
    javax.swing.JTextField linkDbUsernameTextField;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton loginButton1;
    private javax.swing.ButtonGroup modeSelectionButtonGroup;
    private javax.swing.JPanel modeSelectionPanel;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JButton proceedButton;
    private javax.swing.JCheckBox storeDbCredentialsCheckBox;
    private javax.swing.JCheckBox storeLinkDbCredentialsCheckBox;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
