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

package com.compomics.pepshellstandalone.statistics;

import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedPeptide;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Davy Maddelein
 */
public class RatioComparisonPane extends JFreeChartPanel {

    private final Experiment referenceExperiment;
    private Experiment experimentToCompareTo;

    public RatioComparisonPane(Experiment aReferenceExperiment) {
        referenceExperiment = aReferenceExperiment;
    }

    public RatioComparisonPane(Experiment aReferenceExperiment, Experiment aExperimentToCompareTo) {
        referenceExperiment = aReferenceExperiment;
        experimentToCompareTo = aExperimentToCompareTo;
    }

    @Override
    public void setGraphData(PepshellProtein aPepshellProtein) {
        if (aPepshellProtein != null && referenceExperiment.getProteins().contains(aPepshellProtein) && experimentToCompareTo.getProteins().contains(aPepshellProtein)) {
            CategoryDataset dataset = createRatioDataset(aPepshellProtein);
            JFreeChart ratioChart = ChartFactory.createBarChart("log ratios of peptides on a protein", aPepshellProtein.getVisibleAccession(), "log^2 ratio", dataset, PlotOrientation.VERTICAL, true, true, false);
            prettifyChart(ratioChart);
            chart.setChart(ratioChart);
        }

    }

    private CategoryDataset createRatioDataset(PepshellProtein aPepshellProtein) {
        DefaultCategoryDataset returnset = new DefaultCategoryDataset();

        PepshellProtein protein = referenceExperiment.getProteins().get(referenceExperiment.getProteins().indexOf(aPepshellProtein));
        List<PeptideGroup> sortedCopy = protein.getPeptideGroups().stream().sorted(Comparator.comparing(e -> {
            return e.getRepresentativePeptide().getBeginningProteinMatch();
        })).collect(Collectors.toList());

        for (PeptideGroup aPeptideGroup : sortedCopy) {
            PeptideInterface aPeptide = aPeptideGroup.getRepresentativePeptide();


                if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                    Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                    returnset.addValue(value,(Integer) 1, (Integer) aPeptide.getBeginningProteinMatch());
                    //returnset.addValue(((QuantedPeptide) aPeptide).getRatio(), String.valueOf(aPeptide.getBeginningProteinMatch()), String.valueOf(groupcounter));
                }

        }

        protein = experimentToCompareTo.getProteins().get(referenceExperiment.getProteins().indexOf(aPepshellProtein));
        sortedCopy = protein.getPeptideGroups().stream().sorted(Comparator.comparing(e ->  e.getRepresentativePeptide().getBeginningProteinMatch())).collect(Collectors.toList());

        for (PeptideGroup aPeptideGroup : sortedCopy) {
            PeptideInterface aPeptide = aPeptideGroup.getRepresentativePeptide();

                if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                    Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                    returnset.addValue(value, (Integer) aPeptide.getBeginningProteinMatch(), (Integer) 2);
                    //returnset.addValue(((QuantedPeptide) aPeptide).getRatio(), String.valueOf(aPeptide.getBeginningProteinMatch()), String.valueOf(groupcounter));
                }

        }

        return returnset;
    }


    public void setComparisonExperiment(Experiment anExperiment) {
        experimentToCompareTo = anExperiment;
    }
}
