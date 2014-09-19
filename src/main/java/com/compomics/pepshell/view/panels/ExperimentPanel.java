package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.DAO.WebDAO;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Peptides.IntensityPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.Peptides.QuantedPeptideDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class ExperimentPanel extends javax.swing.JPanel {

    private int horizontalOffset = this.getX() + 15;
    private int verticalOffset = 25;
    private Protein protein;
    private StandardPeptideProteinDrawMode peptideDrawMode = new IntensityPeptideDrawMode();
    private StandardPeptideProteinDrawMode proteinDrawMode = new IntensityPeptideDrawMode();
    private boolean recalculate = true;
    private Experiment experiment;
    private String experimentName;
    private boolean nameChanged = false;
    private BufferedImage cachedImage;
    private int startingZoomCoordinate;
    private int endingZoomCoordinate;
    private double scale;
    private boolean proteinChanged;

    /**
     * Creates new form PeptidesProteinsOverlapGUI
     */
    public ExperimentPanel() {
        initComponents();
        this.addMouseListener(new popupMenuListener());
    }

    public ExperimentPanel(Experiment experiment) {
        this();
        this.experiment = experiment;
        ((IntensityPeptideDrawMode) peptideDrawMode).setMaxIntensity(experiment.getMaxIntensity());
        ((IntensityPeptideDrawMode) peptideDrawMode).setMinIntensity(experiment.getMinIntensity());
        experimentName = experiment.getExperimentName();
        projectNameLabel.setText(experiment.getExperimentName());
    }

    public void setProtein(Protein protein) {
        try {
            if (!this.protein.equals(protein)) {
                this.protein = experiment.getProteins().get(experiment.getProteins().indexOf(protein));
                proteinChanged = true;
            }
        } catch (IndexOutOfBoundsException e) {
            this.protein = null;
        }
        this.revalidate();
        this.repaint();
    }

    public void setXoffset(int anXoffset) {
        horizontalOffset = anXoffset;
    }

    public String getProjectNameForPanel() {
        return projectNameLabel.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        experimentPopupMenu = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        projectNameLabel = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        jMenuItem1.setText("change name of selected experiment");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        experimentPopupMenu.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        experimentPopupMenu.add(jMenuItem2);

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 65));
        setMinimumSize(new java.awt.Dimension(1000, 65));
        setPreferredSize(new java.awt.Dimension(1000, 65));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        projectNameLabel.setMaximumSize(new java.awt.Dimension(1600, 300));
        projectNameLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        projectNameLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "intensity", "ratio" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        //replace with a hashset and call contains
        if (protein != null) {
            Iterator<PeptideGroup> peptideGroupIter = protein.getPeptideGroupsForProtein().iterator();
            while (peptideGroupIter.hasNext()) {
                PeptideGroup peptideGroup = peptideGroupIter.next();
                if (evt.getX() >= (int) Math.ceil((double) horizontalOffset + peptideGroup.getStartingAlignmentPosition() * ProgramVariables.SCALE) && evt.getX() <= (int) Math.ceil((double) horizontalOffset + peptideGroup.getEndAlignmentPosition() * ProgramVariables.SCALE)) {
                    //dirty
                    Protein aProtein = new Protein(protein.getProteinSequence());
                    aProtein.addDomains(protein.getDomains());
                    aProtein.addPeptideGroup(peptideGroup);
                    ((ProteinDetailPanel) this.getParent().getParent().getParent().getParent()).setSequenceCoverage(protein.getProteinSequence(), aProtein);
                }
            }
        }
    }//GEN-LAST:event_formMouseMoved

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        projectNameLabel.setText(JOptionPane.showInputDialog("change experiment name to " + projectNameLabel.getText()));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedIndex() == 0) {
            if ((peptideDrawMode instanceof IntensityPeptideDrawMode) == false) {
                peptideDrawMode = new IntensityPeptideDrawMode();
                proteinDrawMode = new IntensityPeptideDrawMode();
                ((IntensityPeptideDrawMode) peptideDrawMode).setMaxIntensity(experiment.getMaxIntensity());
                ((IntensityPeptideDrawMode) peptideDrawMode).setMinIntensity(experiment.getMinIntensity());
                recalculate = true;
            }
        } else if (jComboBox1.getSelectedIndex() == 1) {
            if ((peptideDrawMode instanceof QuantedPeptideDrawMode) == false) {
                peptideDrawMode = new QuantedPeptideDrawMode<>();
                proteinDrawMode = new QuantedPeptideDrawMode<>();
                ((QuantedPeptideDrawMode) peptideDrawMode).setMaxRatio(experiment.getMaxRatio());
                recalculate = true;
            }
        }
        if (recalculate) {
            this.repaint();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        //when extracting this code to it's own mouse adapter flip the scale calculations so the doubles are always bigger than 1 instead of smaller
        endingZoomCoordinate = evt.getX() + 15;
        Double tempScale = (double) (endingZoomCoordinate / startingZoomCoordinate);
        if (tempScale * protein.getProteinSequence().length() < 1) {
            // zoomed in to a value greater than one amino acid
            tempScale = (double) 10 / protein.getProteinSequence().length();
        } else {
            if (tempScale < 1) {
                //dragged to the left
                scale = tempScale;
            } else {
                //dragged to the right, flip the start and end
                endingZoomCoordinate = startingZoomCoordinate;
                startingZoomCoordinate = evt.getX() + 15;
                if ((1 / tempScale) * protein.getProteinSequence().length() < 1) {
                } else {
                    scale = 1 / tempScale;
                }
            }
        }
    }//GEN-LAST:event_formMouseDragged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        //add scrolling support if(evt.getMouseButton is scrollup) increase zoom with 10% centered on the mouse location
        // same as above but reduce by 10%
        startingZoomCoordinate = evt.getX() + 15;


    }//GEN-LAST:event_formMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu experimentPopupMenu;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JLabel projectNameLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        //replace protein recalculation with a buffered image to display
        if (protein != null && proteinChanged) {
            if (!nameChanged) {
                projectNameLabel.setText(experimentName);
            }
            if (protein.getProteinSequence().isEmpty()) {
                try {
                    protein.setSequence(WebDAO.fetchSequence(protein.getProteinAccession()));
                } catch (Exception e) {
                    FaultBarrier.getInstance().handleException(e);
                }
            }
            if (!protein.getProteinSequence().isEmpty()) {
                if (recalculate) {
                    int horizontalBarSize = (int) Math.ceil(protein.getProteinSequence().length() * ProgramVariables.SCALE);
                    BufferedImage tempImage = new BufferedImage(horizontalBarSize + 65, ProgramVariables.VERTICALSIZE + 10, BufferedImage.TYPE_INT_ARGB);
                    try {
                        proteinDrawMode.drawProtein(protein, tempImage.getGraphics(), horizontalOffset, verticalOffset + 5, horizontalBarSize, ProgramVariables.VERTICALSIZE); //((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
                        //proteinDrawMode.drawProtein(protein, g, horizontalOffset, verticalOffset + 5, horizontalBarSize, ProgramVariables.VERTICALSIZE); //((DrawableProtein) protein).draw(horizontalOffset, verticalOffset, g);
                    } catch (UndrawableException ex) {
                        FaultBarrier.getInstance().handleException(ex);
                    }
                    Iterator<PeptideGroup> peptideGroups = protein.getPeptideGroupsForProtein().iterator();
                    while (peptideGroups.hasNext()) {
                        PeptideGroup aPeptideGroup = peptideGroups.next();
                        try {
                            peptideDrawMode.drawPeptide(aPeptideGroup.getShortestPeptide(), tempImage.getGraphics(), horizontalOffset, verticalOffset + 5, ProgramVariables.VERTICALSIZE);
                            //peptideDrawMode.drawPeptide(aPeptideGroup.getShortestPeptide(), g, horizontalOffset, verticalOffset + 5, ProgramVariables.VERTICALSIZE);
                        } catch (Exception ex) {
                            FaultBarrier.getInstance().handleException(ex);
                        }
                    }
                    cachedImage = tempImage;
                    //((GradientDrawModeInterface) peptideDrawMode).drawColorLegend(horizontalOffset + horizontalBarSize + 15, 50, g);
                    ((GradientDrawModeInterface) peptideDrawMode).drawColorLegend(horizontalOffset + horizontalBarSize + 15, 50, tempImage.getGraphics());

                    recalculate = true;

                }
            } else {
                projectNameLabel.setText(experimentName + ": protein not found");
            }
        }
        if (cachedImage != null) {
            this.getGraphics().drawImage(cachedImage, horizontalOffset, verticalOffset, null);
        }
        if (scale != 1) {
            ((Graphics2D) g).translate(this.getWidth() / 2, this.getHeight() / 2);
            ((Graphics2D) g).scale(1 / scale, 1 / scale);
            this.setPreferredSize(new Dimension((int) Math.round(this.getWidth() * (1 / scale)), (int) Math.round(this.getHeight() * (1 / scale))));

        }

        //if the cached image works, the zooming logic can be extracted to it's own mouse adapter class and this can turn into a static method in a zoom controller
        super.paintComponent(g);
    }

    void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    void setYOffset(double height) {
        verticalOffset = (int) (verticalOffset + height);
    }

    public void scaleToDimension(Dimension dimension) {
        //if one panel zooms in, all zoom in so we zoom to the passed dimension
    }

    private class popupMenuListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                experimentPopupMenu.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }

    }
}
