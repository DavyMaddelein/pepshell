/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.view.panels.statistics.wrappers.RatioComparisonPaneWrapper;
import com.compomics.pepshell.view.panels.statistics.wrappers.StatisticsWrapperInterface;
import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.view.panels.statistics.CleavingProbabilityPane;
import com.compomics.pepshell.view.panels.statistics.JFreeChartPanel;
import com.compomics.pepshell.view.panels.statistics.RatioStatisticsPane;
import com.compomics.pepshell.view.panels.statistics.RatioStatisticsScatterplotPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;

/**
 *
 * @author Davy
 */
public class StatisticsTabPane extends JTabbedPane {

    public void setAnalysisGroupToDisplay(Experiment referenceExperiment, AnalysisGroup experiments) {

        List<Experiment> experimentsAndReference = new ArrayList<>(experiments.getExperiments());
        experimentsAndReference.add(referenceExperiment);
        
        CleavingProbabilityPane cleavingProbabilityPane = new CleavingProbabilityPane(referenceExperiment);
        this.addTab("CPDT cleavage probability", cleavingProbabilityPane);

        RatioStatisticsPane ratioStatisticsPane = new RatioStatisticsPane(experimentsAndReference);
        this.addTab("ratios in all experiments", ratioStatisticsPane);

        RatioComparisonPaneWrapper wrapper = new RatioComparisonPaneWrapper(referenceExperiment, experiments.getExperiments());
        this.addTab("ratio comparison", wrapper);
        
        RatioStatisticsScatterplotPane scatterplot = new RatioStatisticsScatterplotPane(experimentsAndReference);
        this.addTab("ratio scatterplot",scatterplot);
    }

    public void setGraphData(Protein aProtein) {
        for (int i = 0; i < this.getComponentCount(); i++) {
            if (this.getComponentAt(i) instanceof JFreeChartPanel) {
                ((JFreeChartPanel) this.getComponentAt(i)).setGraphData(aProtein);
            } else if (this.getComponentAt(i) instanceof StatisticsWrapperInterface) {
                ((StatisticsWrapperInterface) this.getComponentAt(i)).setGraphData(aProtein);
            }
        }
    }
}
