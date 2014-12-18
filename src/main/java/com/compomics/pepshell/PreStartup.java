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
package com.compomics.pepshell;

import com.compomics.pepshell.controllers.properties.StartupProperties;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Davy Maddelein
 */
class PreStartup {
    
    private static Logger logger = Logger.getLogger(PreStartup.class);
    
    //parse added resources and add to classpath


    public static void main(String[] args) {


        Properties properties = System.getProperties();

        StartupProperties pepshellStartupProperties = StartupProperties.getInstance();

        File javaExec = new File(properties.getProperty("java.home")+"/java.exe");

        if (javaExec.exists()){

        StringBuilder settings = new StringBuilder(javaExec.getAbsolutePath());

            StringBuilder classPath =new StringBuilder("-cp "+properties.getProperty("java.class.path"));

        ProcessBuilder pcBuilder = new ProcessBuilder();

        try {
            pcBuilder.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"fatal error ");
        }} else {

            StartUp.main(args);
        }

    }
}
