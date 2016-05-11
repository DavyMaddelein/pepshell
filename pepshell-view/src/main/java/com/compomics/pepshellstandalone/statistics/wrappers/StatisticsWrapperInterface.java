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
 * To change this license header, choose License Headers in Project PepshellProperties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshellstandalone.statistics.wrappers;

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

/**
 * this interface is meant to serve as a wrapper for all jfreechart panels that
 * are added to pepshell. The JFreechartPanel class calls the setGraphData
 * method on all the implementers of this interface, so that all jfreechart
 * graphs show the same protein
 *
 * @author Davy Maddelein
 */
public interface StatisticsWrapperInterface {

    public void setGraphData(PepshellProtein aPepshellProtein);
}
