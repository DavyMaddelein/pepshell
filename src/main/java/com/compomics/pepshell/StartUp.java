package com.compomics.pepshell;

import com.compomics.pepshell.view.panels.CombinedLoginDialog;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
class StartUp {

    private static final Logger LOGGER = Logger.getLogger(StartUp.class);

    public static void main(String[] args) {


        CombinedLoginDialog loginDialog = new CombinedLoginDialog();
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);

    }
}
