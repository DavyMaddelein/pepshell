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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.view.statistics;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Davy Maddelein
 */
public class RatioStatisticsScatterplotPane extends JFreeChartPanel {

    private List<Experiment> experiments = new ArrayList<>();

    public <T extends Experiment> RatioStatisticsScatterplotPane(List<T> anExperimentList) {
        super();
        this.experiments = (List<Experiment>) anExperimentList;
        //chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(Protein aProtein) {
        List<XYLineAnnotation> annotations = new ArrayList<>();
        XYSeriesCollection allExperiments = new XYSeriesCollection();
        for (Experiment anExperiment : experiments) {
            int proteinIndex = anExperiment.getProteins().indexOf(aProtein);
            if (proteinIndex != -1) {
                XYSeries anExperimentSeries = new XYSeries(anExperiment.getExperimentName());
                Protein experimentProtein = anExperiment.getProteins().get(proteinIndex);
                for (PeptideGroup aGroup : experimentProtein.getPeptideGroups()) {
                    PeptideInterface shortestPeptide = aGroup.getShortestPeptide();

                    try {
                        if (shortestPeptide instanceof QuantedPeptide && ((QuantedPeptide) shortestPeptide).getRatio() != null) {
                            Double value = Math.log(((QuantedPeptide) shortestPeptide).getRatio()) / Math.log(2);
                            //Double value = ((QuantedPeptide) shortestPeptide).getRatio();                          
                            if (value == Double.NEGATIVE_INFINITY) {
                                value = 0.0;
                            }
                            double barWithErrors = (shortestPeptide.getEndProteinMatch() + shortestPeptide.getBeginningProteinMatch()) / 2;
                            double error = Math.log(((QuantedPeptide) shortestPeptide).getStandardError()) / Math.log(2);
                            //double error =((QuantedPeptide) shortestPeptide).getStandardError();
                            XYLineAnnotation vertical = new XYLineAnnotation(barWithErrors, value - error, barWithErrors, value + error, new BasicStroke(), Color.black);
                            XYLineAnnotation horizontalUpper = new XYLineAnnotation(value+error-0.25, value + error, value+error+0.25, value + error, new BasicStroke(), Color.black);
                            XYLineAnnotation horizontalLower = new XYLineAnnotation(value-error-0.25, value - error, value-error+0.25, value - error, new BasicStroke(), Color.black);
                            
                            annotations.add(vertical);
                            annotations.add(horizontalUpper);
                            annotations.add(horizontalLower);


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
        for (XYLineAnnotation anAnnotation : annotations) {
            ((XYPlot) ratioChart.getPlot()).addAnnotation(anAnnotation);
         }
        prettifyChart(ratioChart);
    }
}
