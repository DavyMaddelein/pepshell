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
 * @author Davy Maddelein
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
            DbConnectionController.createStructDbConnection(loginPanel.getUsernameTextField().getText(), new String(loginPanel.getPasswordField().getPassword()), loginPanel.getUrlTextField().getText(), loginPanel.getDatabaseNameTextField().getText());
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
