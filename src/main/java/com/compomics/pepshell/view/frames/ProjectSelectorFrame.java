package com.compomics.pepshell.view.frames;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.comparators.CompareProjects;
import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Davy
 */
public class ProjectSelectorFrame extends javax.swing.JFrame implements Observer {

    private AnalysisGroup analysisGroup = new AnalysisGroup<Experiment>("experiment group");
    private FaultBarrier faultBarrier = FaultBarrier.getInstance();
    private Experiment referenceProject;

    /**
     * Creates new form ProjectSelectorFrame
     */
    public ProjectSelectorFrame() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        errorLabel.setVisible(false);
        this.setVisible(true);
        try {
            fillProjectList();
        } catch (Exception ex) {
            faultBarrier.handleException(ex);
            JOptionPane.showMessageDialog(null, "something went wrong while retrieving the list of projects:\n" + ex.getMessage());
        }
    }

    public void dispose(boolean showMainWindow) {
        super.dispose();
        if (showMainWindow) {
            new MainWindow();
        }
    }

    @Override
    public void dispose() {
        this.dispose(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        projectList = new javax.swing.JList();
        projectSelectorButton = new javax.swing.JButton();
        referenceProjectSelectorButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        projectRemovalButton = new javax.swing.JButton();
        referenceProjectRemovalButton = new javax.swing.JButton();
        referenceProjectTextField = new javax.swing.JTextField();
        launchMainWindowButton = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedProjectsList = new JList(analysisGroup.toArray());
        onlyRefProjectPeptidesCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("select projects");
        setMinimumSize(new java.awt.Dimension(545, 336));

        projectList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        projectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        projectList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectListMouseClicked(evt);
            }
        });
        projectList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                projectListKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(projectList);

        projectSelectorButton.setText("select project");
        projectSelectorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                projectSelectorButtonMouseReleased(evt);
            }
        });

        referenceProjectSelectorButton.setText("select reference project");
        referenceProjectSelectorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                referenceProjectSelectorButtonMouseReleased(evt);
            }
        });

        jLabel1.setText("Select project and reference project");

        projectRemovalButton.setText("remove project");
        projectRemovalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                projectRemovalButtonMouseReleased(evt);
            }
        });

        referenceProjectRemovalButton.setText("remove reference project");
        referenceProjectRemovalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                referenceProjectRemovalButtonMouseReleased(evt);
            }
        });

        referenceProjectTextField.setEditable(false);
        referenceProjectTextField.setText(" ");

        launchMainWindowButton.setText("Continue ...");
        launchMainWindowButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                launchMainWindowButtonMouseReleased(evt);
            }
        });

        errorLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText("The selected projects cannot contain the reference project");

        selectedProjectsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        selectedProjectsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedProjectsListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(selectedProjectsList);

        onlyRefProjectPeptidesCheckBox.setText("Only show peptides found in reference project");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectRemovalButton)
                            .addComponent(projectSelectorButton)
                            .addComponent(referenceProjectRemovalButton)
                            .addComponent(referenceProjectSelectorButton)
                            .addComponent(onlyRefProjectPeptidesCheckBox))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(launchMainWindowButton)
                                .addComponent(errorLabel)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(referenceProjectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                        .addGap(44, 44, 44))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(referenceProjectTextField)
                            .addComponent(referenceProjectSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11)
                        .addComponent(referenceProjectRemovalButton)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(projectSelectorButton)
                                .addGap(18, 18, 18)
                                .addComponent(projectRemovalButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(errorLabel)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(launchMainWindowButton)
                            .addComponent(onlyRefProjectPeptidesCheckBox))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void projectListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectListMouseClicked
        if (evt.getClickCount() == 2) {
            automatedSelection();
        }
    }//GEN-LAST:event_projectListMouseClicked

    private void projectSelectorButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectSelectorButtonMouseReleased
        projectSelected();
    }//GEN-LAST:event_projectSelectorButtonMouseReleased

    private void projectRemovalButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectRemovalButtonMouseReleased
        analysisGroup.remove((Experiment) projectList.getSelectedValue());
        selectedProjectsList.setListData(analysisGroup.toArray());
    }//GEN-LAST:event_projectRemovalButtonMouseReleased

    private void referenceProjectSelectorButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_referenceProjectSelectorButtonMouseReleased
        referenceProjectSelected();
    }//GEN-LAST:event_referenceProjectSelectorButtonMouseReleased

    private void referenceProjectRemovalButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_referenceProjectRemovalButtonMouseReleased
        referenceProject = null;
        referenceProjectTextField.setText("");
    }//GEN-LAST:event_referenceProjectRemovalButtonMouseReleased

    private void launchMainWindowButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_launchMainWindowButtonMouseReleased
        if (analysisGroup.isEmpty() || referenceProject == null) {
            JOptionPane.showMessageDialog(this, "please select a project of interest and at least one project to compare with");
        } else {
            try {
                //MainWindow mainWindow = new MainWindow(referenceProject, analysisGroup);
            } finally {
                this.dispose(false);
            }
        }
    }//GEN-LAST:event_launchMainWindowButtonMouseReleased

    private void projectListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_projectListKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            automatedSelection();
        }
    }//GEN-LAST:event_projectListKeyTyped

    private void selectedProjectsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedProjectsListMouseClicked
        if (evt.getClickCount() == 2) {
            analysisGroup.remove((Experiment) selectedProjectsList.getSelectedValue());
            selectedProjectsList.setListData(analysisGroup.toArray());
        }
    }//GEN-LAST:event_selectedProjectsListMouseClicked

    private void fillProjectList() throws SQLException {
        projectList.setListData(DbDAO.getProjects().toArray());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton launchMainWindowButton;
    private javax.swing.JCheckBox onlyRefProjectPeptidesCheckBox;
    private javax.swing.JList projectList;
    private javax.swing.JButton projectRemovalButton;
    private javax.swing.JButton projectSelectorButton;
    private javax.swing.JButton referenceProjectRemovalButton;
    private javax.swing.JButton referenceProjectSelectorButton;
    private javax.swing.JTextField referenceProjectTextField;
    private javax.swing.JList selectedProjectsList;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object o1) {
        JOptionPane.showMessageDialog(this, "there has been a problem retrieving the projects from the database\n" + o1);
    }

    private void referenceProjectSelected() {
        if (!analysisGroup.contains((Experiment) projectList.getSelectedValue())) {
            referenceProject = (Experiment) projectList.getSelectedValue();
            referenceProjectTextField.setText(referenceProject.getExperimentName());
            errorLabel.setVisible(false);
        } else {
            referenceProject = null;
            referenceProjectTextField.setText("");
            errorLabel.setVisible(true);
        }
    }

    private void projectSelected() {
        Experiment selectedProject = (Experiment) projectList.getSelectedValue();
        if (!selectedProject.equals(referenceProject) && !analysisGroup.contains(selectedProject)) {
            analysisGroup.add(selectedProject);
            selectedProjectsList.setListData(analysisGroup.toArray(new Experiment[0]));
            errorLabel.setVisible(false);
        } else {
            errorLabel.setVisible(true);
        }
    }

    private void automatedSelection() {
        if (referenceProject == null) {
            referenceProjectSelected();
        } else {
            projectSelected();
        }
    }
}
