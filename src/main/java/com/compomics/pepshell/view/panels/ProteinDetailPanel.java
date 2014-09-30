package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;

/**
 *
 * @author Davy
 */
public class ProteinDetailPanel extends javax.swing.JPanel {

    private Experiment referenceExperiment;
    private Protein referenceProtein;

    public ProteinDetailPanel() {
        initComponents();
        experimentsScrollPane.getViewport().setOpaque(false);
        experimentsScrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        experimentsScrollPane.getHorizontalScrollBar().setModel(referenceExperimentPanel.getHorizontalScrollBarModel());
    }

    public ProteinDetailPanel(Experiment referenceExperiment, AnalysisGroup aGroup) {
        this();
        this.referenceExperiment = referenceExperiment;
        setExperimentsToDisplay(aGroup.getExperiments());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        referenceExperimentPanel = new com.compomics.pepshell.view.panels.ReferenceExperimentPanel();
        experimentsScrollPane = new javax.swing.JScrollPane();
        experimentsPanel = new com.compomics.pepshell.view.panels.ExperimentsPanel();
        sequenceCoveragePanel = new com.compomics.pepshell.view.panels.SequenceCoveragePanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder("protein details"));

        referenceExperimentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("reference protein"));
        referenceExperimentPanel.setPreferredSize(new java.awt.Dimension(1000, 227));

        experimentsScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        experimentsScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("experiments"));

        experimentsPanel.setPreferredSize(new java.awt.Dimension(200, 100));
        experimentsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                experimentsPanelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout experimentsPanelLayout = new javax.swing.GroupLayout(experimentsPanel);
        experimentsPanel.setLayout(experimentsPanelLayout);
        experimentsPanelLayout.setHorizontalGroup(
            experimentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );
        experimentsPanelLayout.setVerticalGroup(
            experimentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );

        experimentsScrollPane.setViewportView(experimentsPanel);

        sequenceCoveragePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("sequence coverage"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sequenceCoveragePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addComponent(referenceExperimentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(experimentsScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(referenceExperimentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(experimentsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sequenceCoveragePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void experimentsPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_experimentsPanelMouseExited
        // TODO add your handling code here:
        sequenceCoveragePanel.repaint();
        
    }//GEN-LAST:event_experimentsPanelMouseExited

    public void updateProteinGraphics(Protein proteinOfInterest) throws SQLException {
        sequenceCoveragePanel.showProteinCoverage(proteinOfInterest.getProteinSequence(), proteinOfInterest, true);
        referenceProtein = proteinOfInterest;
        this.revalidate();
        this.repaint();
        referenceExperimentPanel.updateProtein(proteinOfInterest);
        referenceExperimentPanel.revalidate();
        referenceExperimentPanel.repaint();
        experimentsPanel.setReferenceProtein(referenceProtein);
        this.updatePeptideGraphics(proteinOfInterest);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.compomics.pepshell.view.panels.ExperimentsPanel experimentsPanel;
    private javax.swing.JScrollPane experimentsScrollPane;
    private com.compomics.pepshell.view.panels.ReferenceExperimentPanel referenceExperimentPanel;
    private com.compomics.pepshell.view.panels.SequenceCoveragePanel sequenceCoveragePanel;
    // End of variables declaration//GEN-END:variables

    /**
     * sets the project do display the peptides from in relation to the
     * reference experiment
     *
     * @param experiments the experiments to show and compare
     * @param condense should the experiments be treated as a group or not
     */
    public void setExperimentsToDisplay(List<Experiment> experiments, boolean condense) {
        experimentsPanel.setLayout(new GridBagLayout());
        for (int i = 0; i < experiments.size(); i++) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            ExperimentPanel panel = new ExperimentPanel(experiments.get(i));
            panel.setExperiment(experiments.get(i));
            panel.setYOffset(panel.getSize().getHeight() * i);
            //change with smarter clustering jpanel
            experimentsPanel.add(panel, gridBagConstraints);
        }
        experimentsPanel.revalidate();
        experimentsPanel.repaint();
    }

    public Experiment getReferenceExperiment() {
        return referenceExperiment;
    }

    public void setReferenceExperiment(Experiment aReferenceExperiment) {
        this.referenceExperiment = aReferenceExperiment;
        referenceExperimentPanel.setReferenceExperiment(referenceExperiment);
    }

    public final void setExperimentsToDisplay(List<Experiment> experiments) {
        setExperimentsToDisplay(experiments, true);
    }

    private void updatePeptideGraphics(Protein aProtein) {
        for (int i = 0; i < experimentsPanel.getComponents().length; i++) {
            ((ExperimentPanel) experimentsPanel.getComponent(i)).setProtein(aProtein);
        }
    }

    protected void setSequenceCoverage(String proteinSequence, Protein protein) {
        // this is a very quick fix to get updating sequence coverage but has to be refined
        sequenceCoveragePanel.showProteinCoverage(proteinSequence,protein, false);
    }

    protected void setSequenceCoverageToOriginal() {
        if (!sequenceCoveragePanel.isOriginalCoverage()) {
            sequenceCoveragePanel.setOriginalCoverage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (referenceProtein != null) {
            double tempscale = (double) (referenceExperimentPanel.getWidth() - 100) / referenceProtein.getProteinSequence().length();
            if (tempscale < 1) {
                ProgramVariables.SCALE = 1;
            } else {
                ProgramVariables.SCALE = (int) Math.floor(tempscale);

            }

        }
    }
}
