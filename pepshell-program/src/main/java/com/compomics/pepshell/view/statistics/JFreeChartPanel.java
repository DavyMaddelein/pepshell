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

import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.view.statistics.wrappers.StatisticsWrapperInterface;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 *
 * @author Davy Maddelein
 */
public abstract class JFreeChartPanel extends javax.swing.JPanel implements StatisticsWrapperInterface {

    private static void setupPlot(CategoryPlot categoryPlot) {
    categoryPlot.setBackgroundPaint(Color.white);
        categoryPlot.setRangeGridlinePaint(Color.black);
        // hide the border of the sorrounding box
        categoryPlot.setOutlinePaint(Color.white);
        // get domanin and range axes
        CategoryAxis domainAxis = categoryPlot.getDomainAxis();
        ValueAxis rangeAxis = categoryPlot.getRangeAxis();
        // set label paint for axes to black
        domainAxis.setLabelPaint(Color.black);
        rangeAxis.setLabelPaint(Color.black);
        // set font for labels, both on domain and range axes
        domainAxis.setLabelFont(new Font("Tahoma", Font.BOLD, 12));
        rangeAxis.setLabelFont(new Font("Tahoma", Font.BOLD, 12));
        
        
    }

    ChartPanel chart;

    /**
     * Creates new form JFreeChartPanel
     *
     * @param anExperiment the experiment to show the statistics of
     */
    JFreeChartPanel() {
        initComponents();
        chart = new ChartPanel(null, true);
        chart.setOpaque(false);
        this.add(chart, getDefaultGridBagConstraints());
        this.validate();
    }

    public abstract void setGraphData(Protein aProtein);

    private static GridBagConstraints getDefaultGridBagConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        return gridBagConstraints;
    }

    private static void setupPlot(XYPlot xYPlot) {
        // set background to white and grid color to black
        xYPlot.setBackgroundPaint(Color.white);
        xYPlot.setRangeGridlinePaint(Color.black);
        // hide the border of the sorrounding box
        xYPlot.setOutlinePaint(Color.white);
        // get domanin and range axes
        ValueAxis domainAxis = xYPlot.getDomainAxis();
        ValueAxis rangeAxis = xYPlot.getRangeAxis();
        // set label paint for axes to black
        domainAxis.setLabelPaint(Color.black);
        rangeAxis.setLabelPaint(Color.black);
        // set font for labels, both on domain and range axes
        domainAxis.setLabelFont(new Font("Tahoma", Font.BOLD, 12));
        rangeAxis.setLabelFont(new Font("Tahoma", Font.BOLD, 12));
    }

    static void prettifyChart(JFreeChart chart) {
        // set title font
        chart.getTitle().setFont(new Font("Tahoma", Font.BOLD, 12));
        if (chart.getPlot() instanceof XYPlot) {
            XYPlot xYPlot = chart.getXYPlot();
            setupPlot(xYPlot);
        } else if (chart.getPlot() instanceof CategoryPlot){
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            setupPlot(categoryPlot);
        }
        setShadowVisible(chart, false);
    }
       
    /**
     * Enable shadow of renderer? JFreeChart 1.0.11 changed the <b>default</b>
     * look by painting shadows for bars. To revert back to the old look, you
     * can disable the shadows with this method.
     *
     * @param chart JFreeChart.
     * @param state False, to disable shadow-
     * @since 4.1.0
     */
    private static void setShadowVisible(final JFreeChart chart, final boolean state) {
        if (chart != null) {
            final Plot p = chart.getPlot();
            if (p instanceof XYPlot) {
                final XYPlot xyplot = (XYPlot) p;
                final XYItemRenderer xyItemRenderer = xyplot.getRenderer();
                if (xyItemRenderer instanceof XYBarRenderer) {
                    final XYBarRenderer br = (XYBarRenderer) xyItemRenderer;
                    br.setBarPainter(new StandardXYBarPainter());
                    br.setShadowVisible(state);
                }
            } else if (p instanceof CategoryPlot) {
                final CategoryPlot categoryPlot = (CategoryPlot) p;
                final CategoryItemRenderer categoryItemRenderer = categoryPlot.getRenderer();
                if (categoryItemRenderer instanceof BarRenderer) {
                    final BarRenderer br = (BarRenderer) categoryItemRenderer;
                    br.setBarPainter(new StandardBarPainter());
                    br.setShadowVisible(state);
                }
            } 
        }
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
        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
