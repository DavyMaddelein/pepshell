/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.view.panels.statistics;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author svend
 */
public class RatioStatisticsScatterplotPane extends JFreeChartPanel {

    List<Experiment> experiments = new ArrayList<>();

    public <T extends Experiment> RatioStatisticsScatterplotPane(List<T> anExperimentList) {
        super();
        this.experiments = (List<Experiment>) anExperimentList;
        //chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(Protein aProtein) {
        XYSeriesCollection allExperiments = new XYSeriesCollection();
        for (Experiment anExperiment : experiments) {
            int proteinIndex = anExperiment.getProteins().indexOf(aProtein);
            if (proteinIndex != -1) {
                XYSeries anExperimentSeries = new XYSeries(anExperiment.getExperimentName());
                Protein experimentProtein = anExperiment.getProteins().get(proteinIndex);
                for (PeptideGroup aGroup : experimentProtein.getPeptideGroupsForProtein()) {
                    Peptide shortestPeptide = aGroup.getShortestPeptide();
                    try {
                        if (shortestPeptide instanceof QuantedPeptide && ((QuantedPeptide) shortestPeptide).getRatio() != null) {
                            Double value = Math.log(((QuantedPeptide) shortestPeptide).getRatio()) / Math.log(2);
                            if (value == Double.NEGATIVE_INFINITY) {
                                value = 0.0;
                            }
                            for (int i = shortestPeptide.getBeginningProteinMatch(); i < shortestPeptide.getEndProteinMatch(); i++) {
                                anExperimentSeries.add(i, value);
                            }
                        }
                    } catch (CalculationException ex) {
                        FaultBarrier.getInstance().handleException(ex);
                    }
                }
                allExperiments.addSeries(anExperimentSeries);

            }
        }
        JFreeChart ratioChart = ChartFactory.createHistogram("ratio for " + aProtein.getVisibleAccession() + " over all experiments", "peptide start", "log^2 ratio", allExperiments, PlotOrientation.VERTICAL, true, true, false);
        chart.setChart(ratioChart);
        prettifyChart(ratioChart);
    }
}
