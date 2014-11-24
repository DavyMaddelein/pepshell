/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.view.panels.statistics.wrappers;

import com.compomics.pepshell.model.Protein;

/**
 * this interface is meant to serve as a wrapper for all jfreechart panels that
 * are added to pepshell. The JFreechartPanel class calls the setGraphData
 * method on all the implementers of this interface, so that all jfreechart
 * graphs show the same protein
 *
 * @author Davy Maddelein
 */
public interface StatisticsWrapperInterface {

    public void setGraphData(Protein aProtein);
}
