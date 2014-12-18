package com.compomics.pepshell;

import com.compomics.pepshell.view.panels.dataloading.CombinedLoginDialog;

/**
 * Hello world!
 *
 */
class StartUp {

    public static void main(String[] args) {

            CombinedLoginDialog loginDialog = new CombinedLoginDialog();
            loginDialog.pack();
            loginDialog.setLocationRelativeTo(null);
            loginDialog.setVisible(true);
        
    }
}
