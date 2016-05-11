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
package com.compomics.pepshellstandalone.dataloading;

import com.compomics.pepshell.controllers.AccessionMaskReader;
import com.compomics.pepshell.controllers.properties.ProgramProperties;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.enums.ExportPropertyEnum;
import com.compomics.pepshellstandalone.ProgramVariables;
import com.compomics.pepshellstandalone.components.JFileChooserWithMemory;
import com.compomics.pepshellstandalone.exceptionhandling.ExceptionEvent;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

/**
 *
 * @author Davy Maddelein
 */
public class PreloadProteinMaskPanel extends javax.swing.JPanel {

    private Set<PepshellProtein> accessionMasks = new HashSet<>();

    /**
     * Creates new form PreloadProteinMaskPanel
     */
    public PreloadProteinMaskPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loadAccessionMaskingFile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        accessionToMaskTextField = new javax.swing.JTextField();
        maskingAccessionTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        saveAccessionMaskingButton = new javax.swing.JButton();
        addMaskingAccessionButton = new javax.swing.JButton();
        removeAccessionMaskingButton = new javax.swing.JButton();
        removeMaskingsFromListButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        accessionMaskingList = new javax.swing.JList<PepshellProtein>();

        loadAccessionMaskingFile.setText("load a masking file");
        loadAccessionMaskingFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAccessionMaskingFileActionPerformed(evt);
            }
        });

        jLabel1.setText("original accession");

        jLabel2.setText("masking accession");

        saveAccessionMaskingButton.setText("export masking");
        saveAccessionMaskingButton.setMaximumSize(new java.awt.Dimension(119, 23));
        saveAccessionMaskingButton.setMinimumSize(new java.awt.Dimension(119, 23));
        saveAccessionMaskingButton.setPreferredSize(new java.awt.Dimension(119, 23));
        saveAccessionMaskingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAccessionMaskingButtonActionPerformed(evt);
            }
        });

        addMaskingAccessionButton.setText("=>");
        addMaskingAccessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMaskingAccessionButtonActionPerformed(evt);
            }
        });

        removeAccessionMaskingButton.setText("<=");
        removeAccessionMaskingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAccessionMaskingButtonActionPerformed(evt);
            }
        });

        removeMaskingsFromListButton.setText("remove all maskings");
        removeMaskingsFromListButton.setMaximumSize(new java.awt.Dimension(119, 23));
        removeMaskingsFromListButton.setMinimumSize(new java.awt.Dimension(119, 23));
        removeMaskingsFromListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMaskingsFromListButtonActionPerformed(evt);
            }
        });

        accessionMaskingList.setModel(new DefaultListModel());
        jScrollPane5.setViewportView(accessionMaskingList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(accessionToMaskTextField)
                    .addComponent(loadAccessionMaskingFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maskingAccessionTextField))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(removeAccessionMaskingButton)
                        .addComponent(addMaskingAccessionButton))
                    .addComponent(removeMaskingsFromListButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveAccessionMaskingButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accessionToMaskTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(saveAccessionMaskingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(loadAccessionMaskingFile))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addMaskingAccessionButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeAccessionMaskingButton)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maskingAccessionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(removeMaskingsFromListButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loadAccessionMaskingFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAccessionMaskingFileActionPerformed
        // TODO, add custom renderer/model to show the string properly, if this takes more than 15 minutes, just overwrite the string in tostring
        JFileChooser accessionMaskingChooser = new JFileChooserWithMemory(ProgramProperties.getInstance().getProperty(ExportPropertyEnum.LASTACCESSIONMASKEXPORTFOLDER.getKey()));
        accessionMaskingChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        accessionMaskingChooser.showOpenDialog(this);
        File selectedFile = accessionMaskingChooser.getSelectedFile();
        try {
            Map<PepshellProtein, String> maskMap = AccessionMaskReader.parseAccessionFile(selectedFile);
            maskMap.entrySet().stream().forEach((entry) -> {
                entry.getKey().setVisibleAccession(entry.getValue());
                accessionMasks.add(entry.getKey());

            });
            accessionMaskingList.setListData(accessionMasks.toArray(new PepshellProtein[accessionMasks.size()]));
        } catch (IOException ex) {
            ProgramVariables.exceptionBus.post(new ExceptionEvent("could not read the accession mask file",true, Level.WARN));
        }
    }//GEN-LAST:event_loadAccessionMaskingFileActionPerformed

    private void saveAccessionMaskingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAccessionMaskingButtonActionPerformed
        try {
            JFileChooser accessionMaskSaver = new JFileChooser(ProgramProperties.getInstance().getProperty(ExportPropertyEnum.LASTACCESSIONMASKEXPORTFOLDER.getKey()));
            accessionMaskSaver.setDialogType(JFileChooser.SAVE_DIALOG);
            accessionMaskSaver.setMultiSelectionEnabled(false);
            accessionMaskSaver.showOpenDialog(this);
            File saveFile = accessionMaskSaver.getSelectedFile();
            //this could be moved out of this dialog to the controller
            try (FileWriter saveWriter = new FileWriter(saveFile)){
                for (PepshellProtein anAccessionMaskingEntry : accessionMasks) {
                    saveWriter.append(anAccessionMaskingEntry.getOriginalAccession()).append("=").append(anAccessionMaskingEntry.getVisibleAccession()).append("\n");
                }
            }
        } catch (IOException ex) {
            ProgramVariables.exceptionBus.post(new ExceptionEvent("could not write the accession masks to file",true,Level.WARN));
        }

    }//GEN-LAST:event_saveAccessionMaskingButtonActionPerformed

    private void addMaskingAccessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMaskingAccessionButtonActionPerformed
        //TODO add model that displays the original -> masked decently
        if (!accessionToMaskTextField.getText().isEmpty() && !maskingAccessionTextField.getText().isEmpty()) {
            PepshellProtein maskedPepshellProtein = new PepshellProtein(accessionToMaskTextField.getText());
            maskedPepshellProtein.setVisibleAccession(maskingAccessionTextField.getText());
            accessionMasks.add(maskedPepshellProtein);

            accessionMaskingList.setListData(accessionMasks.toArray(new PepshellProtein[accessionMasks.size()]));
        }
    }//GEN-LAST:event_addMaskingAccessionButtonActionPerformed

    private void removeAccessionMaskingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAccessionMaskingButtonActionPerformed
        if (!accessionMaskingList.getSelectedValuesList().isEmpty()) {
            accessionMasks.removeIf(e -> accessionMasks.contains(e));
            accessionMaskingList.setListData(accessionMasks.toArray(new PepshellProtein[accessionMasks.size()]));
        }
    }//GEN-LAST:event_removeAccessionMaskingButtonActionPerformed

    private void removeMaskingsFromListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMaskingsFromListButtonActionPerformed
        accessionMasks.clear();
        accessionMaskingList.setListData(new PepshellProtein[10]);
    }//GEN-LAST:event_removeMaskingsFromListButtonActionPerformed

    public Set<PepshellProtein> getProteinsToMaskWith() {
        return Collections.unmodifiableSet(accessionMasks);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<PepshellProtein> accessionMaskingList;
    private javax.swing.JTextField accessionToMaskTextField;
    private javax.swing.JButton addMaskingAccessionButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton loadAccessionMaskingFile;
    private javax.swing.JTextField maskingAccessionTextField;
    private javax.swing.JButton removeAccessionMaskingButton;
    private javax.swing.JButton removeMaskingsFromListButton;
    private javax.swing.JButton saveAccessionMaskingButton;
    // End of variables declaration//GEN-END:variables
}