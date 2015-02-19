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

package com.compomics.pepshell.view.dataviewing;

import com.compomics.pepshell.model.protein.ProteinInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import java.awt.Component;
import java.awt.Dimension;

/**
 *
 * @author Niels Hulstaert
 */
public class ExperimentAggregatorPanel extends javax.swing.JPanel {

    private PepshellProtein referenceProtein;

    /**
     * Creates new form ExperimentAggregatorPanel
     */
    public ExperimentAggregatorPanel() {
        initComponents();
    }

    public void setReferenceProtein(PepshellProtein referenceProtein) {
        this.referenceProtein = referenceProtein;
    }

    @Override
    public Dimension getPreferredSize() {
        if (referenceProtein != null) {
            int height = 0;
            for (Component component : this.getComponents()) {
                height += component.getPreferredSize().height;
            }
            return new Dimension(DrawModeUtilities.getInstance().scale(referenceProtein.getProteinSequence().length()) + 150, height);
        } else {
            return super.getPreferredSize();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
