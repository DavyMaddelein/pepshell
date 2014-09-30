package com.compomics.pepshell.view.panels.statistics;

import com.compomics.pepshell.model.CPDTPeptide;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Davy
 */
public class CleavingProbabilityPane extends JFreeChartPanel {

    private double cutoff = 0.7;
    private Protein currentProtein;
    private Experiment experiment;

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
    public void setGraphData(Protein aProtein) {
        //obligatory checks
        if (aProtein != null && experiment.getProteins().contains(aProtein)) {
            if (aProtein != currentProtein) {
                //TODO: run this outside of the gui thread
                currentProtein = experiment.getProteins().get(experiment.getProteins().indexOf(aProtein));
                XYSeriesCollection xYSeriesCollection = new XYSeriesCollection();
                for (XYSeries aSerie : fillSeries(currentProtein)) {
                    xYSeriesCollection.addSeries(aSerie);
                }
                JFreeChart CPDTchart = ChartFactory.createXYLineChart("probability of cleaving", "aminoacids of " + currentProtein.getProteinAccession(), "probability", xYSeriesCollection, PlotOrientation.VERTICAL, false, true, false);
                prettifyChart(CPDTchart);
                CPDTchart.getXYPlot().getRangeAxis().setLowerBound(cutoff - 0.05);
                for (int i = 0; i < CPDTchart.getXYPlot().getSeriesCount(); i++){
                 CPDTchart.getXYPlot().getRenderer().setSeriesStroke(i, new BasicStroke(5));
                }
                if (!aProtein.getDomains().isEmpty()) {
                    CPDTchart.setBackgroundImageAlpha(0.18f);
                    CPDTchart.getXYPlot().getDomainAxis().setRange(0, aProtein.getProteinSequence().length());
                    BufferedImage anImage = new BufferedImage(this.getHeight(), this.getWidth(), BufferedImage.TYPE_INT_ARGB);
                    for (Domain aDomain : aProtein.getDomains()) {
                        anImage.getGraphics().drawRect(aDomain.getStartPosition(), 0, aDomain.getStopPosition(), this.getHeight());
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
            setGraphData(currentProtein);
        }

    }

    private List<XYSeries> fillSeries(Protein currentProtein) {
        List<XYSeries> setList = new ArrayList<XYSeries>();
        for (PeptideGroup aPeptideGroup : currentProtein.getCPDTPeptideGroups()) {
            if (aPeptideGroup.getShortestPeptide() instanceof CPDTPeptide) {
                if (((CPDTPeptide) aPeptideGroup.getShortestPeptide()).getProbability() > cutoff && aPeptideGroup.getShortestPeptide().getSequence().length() > 4) {
                    XYSeries set = new XYSeries(aPeptideGroup.getShortestPeptide().getSequence());
                    set.add(aPeptideGroup.getShortestPeptide().getBeginningProteinMatch(), ((CPDTPeptide) aPeptideGroup.getShortestPeptide()).getProbability());
                    set.add(aPeptideGroup.getShortestPeptide().getBeginningProteinMatch() + aPeptideGroup.getShortestPeptide().getSequence().length(), ((CPDTPeptide) aPeptideGroup.getShortestPeptide()).getProbability());
                    setList.add(set);
                }
            }
        }
        return setList;
    }
}
