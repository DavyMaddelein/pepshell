package com.compomics.partialtryp.view.panels;

import com.compomics.partialtryp.view.frames.ProteinInfoFrame;
import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Project;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.QuantedPeptideGroup;
import com.compomics.partialtryp.view.DrawModes.PeptideProteinDrawMode;
import com.compomics.partialtryp.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;
import javax.swing.JLabel;

/**
 *
 * @author Davy
 */
public class PeptidesProteinsOverlapPanel extends javax.swing.JPanel {

    private int horizontalOffset = 115;
    private int verticalOffset = 50;
    private int proteinBarSize = 1000;
    private int proteinBarHeight = 15;
    private Protein protein;
    private float transparency = 1;
    private PeptideProteinDrawMode drawMode = new StandardPeptideProteinDrawMode();
    private int maxRatio = 1;
    private Project project;

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public PeptidesProteinsOverlapPanel(Project project) {
        this.project = project;
        initComponents();
    }

    public void setRatio(int ratio) {
        maxRatio = ratio;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public String getProjectNameForPanel() {
        return project.getProjectName();
    }

    public int getProjectidForPanel() {
        return project.getProjectId();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectNameLabel = new JLabel(project.getProjectName());

        setPreferredSize(new java.awt.Dimension(1000, 80));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        projectNameLabel.setMaximumSize(new java.awt.Dimension(1600, 300));
        projectNameLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        projectNameLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(831, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (evt.getY() >= verticalOffset + 5 && evt.getY() <= verticalOffset + proteinBarSize) {
            System.out.println(evt.getY());
            System.out.println(evt.getX());
            for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
                if (evt.getX() >= horizontalOffset + peptideGroup.getStartingAlignmentPosition() && evt.getX() <= horizontalOffset + peptideGroup.getEndAlignmentPosition()) {
                    ProteinInfoFrame frame = new ProteinInfoFrame(protein, peptideGroup);
                }
            }
        }
    }//GEN-LAST:event_formMouseMoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel projectNameLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        horizontalOffset = this.getX() + projectNameLabel.getX()+projectNameLabel.getWidth()+25;
        verticalOffset = this.getY()+ 50;
        proteinBarSize = this.getWidth() - 10;
        super.paintComponent(g);
        if (protein != null) {
            drawMode.drawProtein(protein, g, horizontalOffset, verticalOffset, proteinBarSize, proteinBarHeight);
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
            for (PeptideGroup peptideGroup : protein.getPeptideGroupsForProtein()) {
                if (peptideGroup instanceof QuantedPeptideGroup) {
                    drawMode.drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, proteinBarSize, proteinBarHeight,proteinBarSize, ((QuantedPeptideGroup) peptideGroup).getRatio() / maxRatio);
                } else {
                    drawMode.drawPeptide(peptideGroup, g, horizontalOffset, verticalOffset, protein.getProteinSequence().length(), proteinBarHeight,proteinBarSize, maxRatio);
                }
            }
        }
    }

    public void setDrawMode(PeptideProteinDrawMode drawMode) {
        this.drawMode = drawMode;
    }
}
