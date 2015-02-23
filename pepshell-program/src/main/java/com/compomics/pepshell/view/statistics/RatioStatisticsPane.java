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

package com.compomics.pepshell.view.statistics;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Davy Maddelein
 */
public class RatioStatisticsPane extends JFreeChartPanel {

    private List<? extends Experiment> experimentGroup = new ArrayList<>();
    private PepshellProtein currentPepshellProtein;

    public RatioStatisticsPane(List<? extends Experiment> experiments) {
        super();
        this.experimentGroup = experiments;
        //chart.setMinimumDrawWidth(5000);
        //chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(PepshellProtein aPepshellProtein) {
        //obligatory checks
        if (aPepshellProtein != null) {
            if (aPepshellProtein != currentPepshellProtein) {
                //TODO: run this outside of the gui thread
                CategoryDataset dataset = createRatioDataset(aPepshellProtein);
                JFreeChart ratioChart = ChartFactory.createBarChart("log 2 ratios of peptides on a protein", aPepshellProtein.getVisibleAccession(), "log 2 ratio", dataset, PlotOrientation.VERTICAL, true, true, false);
                prettifyChart(ratioChart);
//((CategoryPlot)ratioChart.getPlot()).setRenderer(new StatisticalBarRenderer());
                chart.setChart(ratioChart);
            }
        } else {
            chart.setChart(null);
            this.getGraphics().drawString("missing data", 0, 0);
        }
    }

    private CategoryDataset createRatioDataset(PepshellProtein aPepshellProtein) {
        DefaultCategoryDataset returnset = new DefaultCategoryDataset();
        //PepshellProtein protein = experimentGroup.get(0).getProteins().get(experimentGroup.get(0).getProteins().indexOf(aPepshellProtein));
        if (aPepshellProtein != null) {
            for (Experiment anExperiment : experimentGroup) {

                if (anExperiment.getProteins().indexOf(aPepshellProtein) != -1) {
                    PepshellProtein protein = anExperiment.getProteins().get(anExperiment.getProteins().indexOf(aPepshellProtein));

                    List<PeptideGroup> sortedCopy = protein.getPeptideGroups().stream().sorted(Comparator.comparing(e -> e.getShortestPeptide().getBeginningProteinMatch())).collect(Collectors.toList());

                    for (PeptideGroup aPeptideGroup : sortedCopy) {
                        PeptideInterface aPeptide = aPeptideGroup.getShortestPeptide();

                        try {
                            if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                                Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                                returnset.addValue(value, anExperiment.getExperimentName(), String.valueOf(aPeptide.getBeginningProteinMatch()));
                                //this part can be put in it's own method

                                for (Experiment checkList : experimentGroup) {

                                    if (!checkList.equals(anExperiment) && returnset.getRowKeys().contains(checkList.getExperimentName())) {
                                        if (returnset.getValue(checkList.getExperimentName(), String.valueOf(aPeptide.getBeginningProteinMatch())) == null) {
                                            returnset.addValue(null, checkList.getExperimentName(), String.valueOf(aPeptide.getBeginningProteinMatch()));
                                        }
                                    }
                                }
                            }
                        } catch (CalculationException ex) {
                            FaultBarrier.getInstance().handleException(ex);

                        }
                    }

                }
            }
        }

        return returnset;
    }
}
