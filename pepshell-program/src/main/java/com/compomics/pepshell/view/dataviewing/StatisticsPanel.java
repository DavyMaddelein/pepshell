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

import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Davy Maddelein
 */
class StatisticsPanel extends javax.swing.JPanel {

    private AnalysisGroup analysisGroup;

    /**
     * Creates new form StatisticsPanel
     */
    private StatisticsPanel() {
        initComponents();
    }

    public StatisticsPanel(AnalysisGroup group) {
        this();
        this.analysisGroup = group;
        new runStatistics(analysisGroup).run();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

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

    private final class runStatistics implements Runnable {

        private AnalysisGroup aGroup;

        runStatistics(AnalysisGroup group) {
            aGroup = group;
        }

        @Override
        public void run() {
            //peptideOccurence(aGroup);
            //peptideLengthDistribution(aGroup);
        }

//        private void peptideOccurence(AnalysisGroup anAnalysisGroup) {
//            //redo this completely with a hashmap containing arraylists
//            Set<PepshellProtein> onceFound = new HashSet();
//            Set<PepshellProtein> twiceFound = new HashSet();
//            Set<PepshellProtein> multipleFound = new HashSet();
//
//            for (Experiment aProject : anAnalysisGroup.getExperiments()) {
//                int peptidesFound;
//                for (PepshellProtein aProjectProtein : aProject.getProteins()) {
//                    peptidesFound = aProjectProtein.getPeptideGroupsForProtein().size();
//                    if (peptidesFound == 1) {
//                        if (onceFound.contains(aProjectProtein)) {
//                            twiceFound.add(aProjectProtein);
//                            onceFound.remove(aProjectProtein);
//                        } else if (twiceFound.contains(aProjectProtein)) {
//                            multipleFound.add(aProjectProtein);
//                            twiceFound.remove(aProjectProtein);
//                        } else {
//                            onceFound.add(aProjectProtein);
//                        }
//                    } else if (peptidesFound > 1) {
//                        if (onceFound.contains(aProjectProtein)) {
//                            multipleFound.add(aProjectProtein);
//                            onceFound.remove(aProjectProtein);
//                        } else if (twiceFound.contains(aProjectProtein)) {
//                            multipleFound.add(aProjectProtein);
//                            twiceFound.remove(aProjectProtein);
//                        } else if (peptidesFound == 2) {
//                            twiceFound.add(aProjectProtein);
//                        } else if (peptidesFound > 2) {
//                            multipleFound.add(aProjectProtein);
//                        } else {
//                            FaultBarrier.getInstance().handleException(new Exception("wut?"));
//                        }
//                    }
//                }
//            }
//            onceFoundList.setListData(onceFound.toArray());
//            twiceFoundList.setListData(twiceFound.toArray());
//            multipleFoundList.setListData(multipleFound.toArray());
//            int totalFound = onceFound.size() + twiceFound.size() + multipleFound.size();
//            percentageOnceFoundLabel.setText(String.valueOf(((double) onceFound.size() / (double) totalFound) * 100) + "%");
//            percentageTwiceFoundLabel.setText(String.valueOf(((double) twiceFound.size() / (double) totalFound) * 100) + "%");
//            percentageMultipleFoundLabel.setText(String.valueOf(((double) multipleFound.size() / (double) totalFound) * 100) + "%");
//
//        }

        private void peptideLengthDistribution(AnalysisGroup aGroup) {
            HashMap<Integer, HashSet<PepshellProtein>> distribution = new HashMap<>();
            for (Iterator<Experiment> it = aGroup.getExperiments().iterator(); it.hasNext(); ) {
                Experiment aProject = it.next();
                aProject.getProteins().stream().forEach((aProtein) -> {
                    for (PeptideGroup aPeptideGroup : aProtein.getPeptideGroups()) {
                        for (Iterator<PeptideInterface> it2 = aPeptideGroup.getPeptideList().iterator(); it.hasNext(); ) {
                            PeptideInterface aPeptide = it2.next();
                            if (distribution.containsKey(aPeptide.getSequence().length())) {
                                distribution.get(aPeptide.getSequence().length()).add(aProtein);
                            } else {
                                distribution.put(aPeptide.getSequence().length(), new HashSet<>());
                            }
                        }
                    }
                });
            }
        }
    }
}
