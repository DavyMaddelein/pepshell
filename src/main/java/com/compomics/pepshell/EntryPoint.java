package com.compomics.pepshell;

import com.compomics.pepshell.view.panels.CombinedLoginDialog;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class EntryPoint {

    private static final Logger LOGGER = Logger.getLogger(EntryPoint.class);

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CombinedLoginDialog loginDialog = new CombinedLoginDialog();
                loginDialog.pack();
                loginDialog.setLocationRelativeTo(null);
                loginDialog.setVisible(true);
            }
        });
    }
}
