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
package com.compomics.pepshell.view.dataloading.frames;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.dataimport.filevalidation.FileValidatorFactory;
import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.view.dataloading.CombinedLoginDialog;
import com.compomics.pepshell.view.dataloading.MetaDataProviderDialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Davy Maddelein
 */
public class ValidationFrame extends javax.swing.JFrame {

    private AnnotatedFile referenceFile;
    private List<AnnotatedFile> filesToCompare;
    private int fileDoneCounter = -1;
    private int totalFilesToValidate;

    private final ImageIcon loading = new ImageIcon(ClassLoader.getSystemResource("processing.gif"));
    private final ImageIcon done = new ImageIcon(ClassLoader.getSystemResource("completed.png"));
    private final ImageIcon failed = new ImageIcon(ClassLoader.getSystemResource("failed.png"));

    /**
     * Creates new form ValidationPanel
     *
     * @param aReferenceFile
     * @param filesToCompare
     */
    public ValidationFrame(AnnotatedFile aReferenceFile, List<AnnotatedFile> filesToCompare) {
        referenceFile = aReferenceFile;
        this.filesToCompare = filesToCompare;
        totalFilesToValidate = filesToCompare.size() + 1;
        initComponents();
        GridBagConstraints constraints = ((GridBagLayout) jPanel2.getLayout()).getConstraints(jPanel2);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
//TODO filestocompare still contains the reference file, for the flow it doesn't matter now but it breaks the check to pass on        
//jPanel2.add(new JLabel(referenceFile.getName()), constraints);
        //validateFile(aReferenceFile);
        for (int i = constraints.gridy; i < filesToCompare.size(); i++) {
            constraints.gridy = i + 1;
            jPanel2.add(new JLabel(filesToCompare.get(i).getName(), loading, JLabel.CENTER), constraints);
            validateFile(filesToCompare.get(i));
        }
    }

    public ValidationFrame(List<AnnotatedFile> filesToCompare) {
        initComponents();
        totalFilesToValidate = filesToCompare.size();
        GridBagConstraints constraints = ((GridBagLayout) jPanel1.getLayout()).getConstraints(jPanel1);
        constraints.gridx = 0;
        constraints.gridy = 0;
        for (int i = constraints.gridy; i > filesToCompare.size(); i++) {
            constraints.gridy = i;
            jPanel2.add(new JLabel(filesToCompare.get(i).getName(), loading, JLabel.CENTER), constraints);
            validateFile(filesToCompare.get(i));
        }
    }

    private void validateFile(AnnotatedFile fileToValidate) {
        new SwingWorker<Boolean, AnnotatedFile>() {

            private AnnotatedFile file;

            SwingWorker setFileForValidation(AnnotatedFile fileToValidate) {
                this.file = fileToValidate;
                return this;
            }

            @Override
            protected Boolean doInBackground() throws Exception {
                return file != null && file.exists() && FileValidatorFactory.getInstance().validate(file);
            }

            @Override
            protected void done() {
                final JLabel toEdit;

                if (filesToCompare.contains(file)) {
                    //todo uncomment this when the list thing is fixed
//                    if (referenceFile != null) {
                    //                      toEdit = ((JLabel) jPanel2.getComponents()[filesToCompare.indexOf(file) + 1]);
                    //                  } else {
                    toEdit = ((JLabel) jPanel2.getComponents()[filesToCompare.indexOf(file)]);
                    //                }
                } else {
                    toEdit = (JLabel) jPanel2.getComponents()[0];
                }
                try {
                    if (this.get()) {
                        toEdit.setIcon(done);
                        toEdit.setForeground(new Color(10, 127, 42));
                        fileDoneCounter++;
                    } else {
                        toEdit.setIcon(failed);
                        toEdit.setForeground(Color.red);
                        fileDoneCounter++;
                        if (toEdit.getText().contains(" could not be validated, click to change annotations")) {
                            toEdit.setText(toEdit.getText().substring(0, toEdit.getText().indexOf(" could not be validated, click to change annotations")));
                        }
                        toEdit.setText(toEdit.getText() + " could not be validated, click to change annotations");
                        toEdit.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int index = filesToCompare.indexOf(file);
                                reannotateExperiment(file);
                                toEdit.setText(file.getName());
                                if (index != -1) {
                                    filesToCompare.set(index, file);
                                } else {
                                    referenceFile = file;
                                }
                                fileDoneCounter--;
                                validateFile(file);
                            }

                        });
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                    toEdit.setIcon(failed);
                    toEdit.setForeground(Color.red);
                    if (toEdit.getText().contains(" could not be validated, click to change annotations")) {
                        toEdit.setText(toEdit.getText().substring(0, toEdit.getText().indexOf(" could not be validated, click to change annotations")));
                    }
                    StringBuilder builder = new StringBuilder(toEdit.getText()).append(" could not be validated, click to change annotations ");
                    if (ex.getCause().getSuppressed().length > 0) {
                        builder.append(ex.getCause().getSuppressed()[0].getMessage());
                    }
                    toEdit.setText(builder.toString());
                    toEdit.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int index = filesToCompare.indexOf(file);
                            reannotateExperiment(file);
                            toEdit.setText(file.getName());
                            if (index != -1) {
                                filesToCompare.set(index, file);
                            } else {
                                referenceFile = file;
                            }
                            validateFile(file);
                        }

                    });
                }
                if (fileDoneCounter == totalFilesToValidate) {
                    jButton2.setEnabled(true);
                }
            }
        }.setFileForValidation(fileToValidate).execute();
    }

    private void reannotateExperiment(final AnnotatedFile aFile) {
        MetaDataProviderDialog dialog = new MetaDataProviderDialog(this, true);
        dialog.setFilesToAnnotate(new ArrayList() {
            {
                this.add(aFile);
            }
        });
        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("validation");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Validation passed files");

        jButton1.setText("cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("continue");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());
        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                            .addGap(0, 478, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 390, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new CombinedLoginDialog().setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.OK_OPTION;
        for (Component aComponent : jPanel2.getComponents()) {
            if (((JLabel) aComponent).getIcon().equals(failed)) {
                result = JOptionPane.showConfirmDialog(this, "at least one file did not get validated. Do you want to continue?");
                break;
            }

        }
        if (result == JOptionPane.OK_OPTION) {
            new ExperimentSelectionFrame(referenceFile, filesToCompare).setLocation(this.getLocation());
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
