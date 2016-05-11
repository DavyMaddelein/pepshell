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

package com.compomics.pepshellstandalone;


import com.compomics.pepshell.controllers.secondarystructureprediction.RelativeSolventAccessibilityPredictor;

import java.awt.Color;
import java.io.File;

/**
 *
 * @author Davy Maddelein
 */
public class ProgramVariables {

    //TODO move all these to their designated preferences
    //viewpreferences
    public static final Color DOMAINCOLOR = Color.GREEN;
    public static final int VERTICALSIZE = 15;
    //program properties
    public static File EXPORTFOLDER = new File(System.getProperty("java.home"));
    public static RelativeSolventAccessibilityPredictor relativeSolventAccessibilityPredictor;
    public static boolean USEINTERNETSOURCES = true;


    //all aboard the fail train
    public static com.google.common.eventbus.EventBus exceptionBus;
}
