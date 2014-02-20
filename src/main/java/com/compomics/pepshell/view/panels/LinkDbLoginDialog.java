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
public class LinkDbLoginDialog extends DbLoginDialog {
    
    public LinkDbLoginDialog() {
        super(new javax.swing.JFrame(), true, DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.LINKDBUSERNAME.getKey()), DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.LINKDBURL.getKey()), DatabaseProperties.getInstance().getProperty(DataBasePropertyEnum.LINKDBNAME.getKey()));
        this.setTitle("link database login");
        loginPanel.getLoginInfoLabel().setText("please enter your link database login credentials");
    }
    
    @Override
    public void executeLogin() {
        try {
            DbConnectionController.createLinkDbConnection(loginPanel.getUsernameTextField().getText(), new String(loginPanel.getPasswordField().getPassword()), loginPanel.getUrlTextField().getText(), loginPanel.getDatabaseNameTextField().getText());
            JOptionPane.showMessageDialog(this, "login was successful to link db");
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
            JOptionPane.showMessageDialog(this, "there has been an error while trying to log in.\n" + sqle.getMessage());
        }
    }
    
    @Override
    public void storeCredentials(boolean store) {
        if (store) {
            DatabaseProperties.getInstance().setProperties(new ArrayList<Property>() {
                {
                    this.add(new Property(DataBasePropertyEnum.LINKDBUSERNAME, loginPanel.getUsernameTextField().getText()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBURL, loginPanel.getUrlTextField().getText()));
                    this.add(new Property(DataBasePropertyEnum.LINKDBNAME, loginPanel.getDatabaseNameTextField().getText()));
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
