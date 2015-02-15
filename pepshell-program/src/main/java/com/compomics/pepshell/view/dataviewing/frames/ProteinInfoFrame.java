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

package com.compomics.pepshell.view.dataviewing.frames;

import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import java.awt.MouseInfo;
import java.util.Iterator;

/**
 *
 * @author Davy Maddelein
 */
class ProteinInfoFrame extends javax.swing.JFrame {

    /**
     * Creates new form ProteinInfoFrame
     */
    private ProteinInfoFrame(Protein protein) {
        initComponents();
        this.setLocation(MouseInfo.getPointerInfo().getLocation());
        proteinTextArea.setText(protein.getProteinAccession() + "\n" + protein.getProteinSequence());
        for (Domain domainInProtein : protein.getDomains()) {
            proteinTextArea.append("\n" + domainInProtein.getDomainName());
            proteinTextArea.append("\nDomain start: " + Integer.toString(domainInProtein.getStartPosition()));
            proteinTextArea.append("\nDomain stop: " + Integer.toString(domainInProtein.getStopPosition()));
        }
        this.setVisible(true);
    }

    public ProteinInfoFrame(Protein protein, PeptideGroup peptideGroup) {
        this(protein);
        proteinTextArea.append("\n" + "---------------");
        for (Iterator<PeptideInterface> it = peptideGroup.getPeptideList().iterator(); it.hasNext(); ) {
            PeptideInterface peptide = it.next();
            proteinTextArea.append("\n" + peptide.getSequence());
            proteinTextArea.append("\n" + peptideGroup.getStartingAlignmentPosition());
            proteinTextArea.append("\n" + peptideGroup.getEndAlignmentPosition());
            proteinTextArea.append("\nmiscleaved: " + peptide.isIsMiscleaved());
            if (peptide instanceof QuantedPeptide) {
                //proteinTextArea.append("\nquantitation type: " + ((QuantedPeptide) peptide).getQuantitationType());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        proteinTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Protein Info");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        proteinTextArea.setColumns(20);
        proteinTextArea.setRows(5);
        jScrollPane1.setViewportView(proteinTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        this.dispose();
    }//GEN-LAST:event_formWindowLostFocus
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea proteinTextArea;
    // End of variables declaration//GEN-END:variables
}
