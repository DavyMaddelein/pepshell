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

import com.compomics.pepshell.model.*;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Davy Maddelein
 */
public class CleavingProbabilityPane extends JFreeChartPanel {

    private double cutoff = 0.7;
    private PepshellProtein currentPepshellProtein;
    private final Experiment experiment;
    private int sequenceCutoffLength = 4;

    /**
     *
     * inherits javadoc
     */
    public <T extends Experiment> CleavingProbabilityPane(T anExperiment) {
        super();
        this.experiment = anExperiment;
        //chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(PepshellProtein aPepshellProtein) {
        //obligatory checks
        if (aPepshellProtein != null && experiment.getProteins().contains(aPepshellProtein)) {
            if (aPepshellProtein != currentPepshellProtein) {
                //TODO: run this outside of the gui thread
                currentPepshellProtein = experiment.getProteins().get(experiment.getProteins().indexOf(aPepshellProtein));
                XYSeriesCollection xYSeriesCollection = new XYSeriesCollection();
                fillSeries(currentPepshellProtein).forEach(xYSeriesCollection::addSeries);
                JFreeChart CPDTchart = ChartFactory.createXYLineChart("probability of cleaving", "aminoacids of " + currentPepshellProtein.getVisibleAccession(), "probability", xYSeriesCollection, PlotOrientation.VERTICAL, false, true, false);
                prettifyChart(CPDTchart);
                CPDTchart.getXYPlot().getRangeAxis().setLowerBound(cutoff - 0.05);
                for (int i = 0; i < CPDTchart.getXYPlot().getSeriesCount(); i++) {
                    CPDTchart.getXYPlot().getRenderer().setSeriesStroke(i, new BasicStroke(5));
                }
                if (!aPepshellProtein.getDomains().isEmpty()) {
                    CPDTchart.setBackgroundImageAlpha(0.18f);
                    CPDTchart.getXYPlot().getDomainAxis().setRange(0, aPepshellProtein.getProteinSequence().length());
                    BufferedImage anImage = new BufferedImage(this.getHeight(), this.getWidth(), BufferedImage.TYPE_INT_ARGB);
                    for (FeatureWithLocation aDomain : aPepshellProtein.getDomains()) {
                        anImage.getGraphics().drawRect(aDomain.getStartPosition(), 0, aDomain.getEndPosition(), this.getHeight());
                    }
                    CPDTchart.setBackgroundImage(anImage);
                }
                chart.setChart(CPDTchart);
            }
        } else {
            chart.setChart(null);
            this.getGraphics().drawString("missing data", 0, 0);
        }
    }

    public void setCutoff(double aCutoff) {
        if (this.cutoff != aCutoff) {
            this.cutoff = aCutoff;
            setGraphData(currentPepshellProtein);
        }

    }

    public double getCutoff() {
        return cutoff;
    }

    public int getSequenceCutoffLength() {
        return sequenceCutoffLength;
    }

    public void setSequenceCutoffLength(int sequenceCutoffLength) {
        this.sequenceCutoffLength = sequenceCutoffLength;
    }

    private List<XYSeries> fillSeries(PepshellProtein currentPepshellProtein) {
        List<XYSeries> setList = new ArrayList<>();
        for (Iterator<PeptideGroup> it = currentPepshellProtein.getCPDTPeptideGroups().iterator(); it.hasNext(); ) {
            PeptideGroup aPeptideGroup = it.next();
            if (aPeptideGroup.getRepresentativePeptide().getProbability() > cutoff && aPeptideGroup.getRepresentativePeptide().getSequence().length() > sequenceCutoffLength) {
                XYSeries set = new XYSeries(aPeptideGroup.getRepresentativePeptide().getSequence());
                set.add(aPeptideGroup.getRepresentativePeptide().getBeginningProteinMatch(), aPeptideGroup.getRepresentativePeptide().getProbability());
                set.add(aPeptideGroup.getRepresentativePeptide().getBeginningProteinMatch() + aPeptideGroup.getRepresentativePeptide().getSequence().length(), aPeptideGroup.getRepresentativePeptide().getProbability());
                setList.add(set);
            }
        }
        return setList;
    }
}
