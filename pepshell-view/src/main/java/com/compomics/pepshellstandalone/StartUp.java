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

import com.compomics.pepshellstandalone.dataloading.CombinedLoginDialog;
import com.compomics.pepshellstandalone.exceptionhandling.ExceptionEventHandler;
import com.google.common.eventbus.EventBus;

/**
 * Hello world, what's shaking!
 */
public class StartUp {

    public StartUp() {
        EventBus eventBus = new EventBus("PepshellExceptionBus");

        eventBus.register(new ExceptionEventHandler());

        ProgramVariables.exceptionBus = eventBus;


        CombinedLoginDialog loginDialog = new CombinedLoginDialog();
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);
    }


    public static void main(String[] args) {
        new StartUp();
    }
}
