package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.properties.DatabaseProperties;
import com.compomics.pepshell.model.Property;
import com.compomics.pepshell.model.enums.DataBasePropertyEnum;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class LinkDbLoginDialog extends LoginDialog {

    public LinkDbLoginDialog() {
        super(new javax.swing.JFrame(), true, DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.LINKDBUSERNAME.getKey()), DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.LINKDBURL.getKey()), DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.LINKDBNAME.getKey()));
        this.setTitle("log in to Link Db");
        this.loginCredentialLabel.setText("please enter your Link Db login credentials");
    }

    @Override
    void executeLogin() {
        try {
            DbConnectionController.createLinkDbConnection(usernameTextField.getText(), new String(passwordField.getPassword()), urlTextField.getText(), databaseNameTextField.getText());
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
            JOptionPane.showMessageDialog(this, "there has been an error while trying to log in.\n" + sqle.getMessage());
        }

    }

    @Override
    void storeCredentials(boolean store) {
        if (store) {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.LINKDBUSERNAME, usernameTextField.getText()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBURL, urlTextField.getText()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBNAME, databaseNameTextField.getText()));
                }
            });
        } else {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.LINKDBUSERNAME, DataBasePropertyEnum.LINKDBUSERNAME.getDefaultValue()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBURL, DataBasePropertyEnum.LINKDBURL.getDefaultValue()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBNAME, DataBasePropertyEnum.LINKDBNAME.getDefaultValue()));
                }
            });
        }
    }

}
