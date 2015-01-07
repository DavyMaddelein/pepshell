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
package com.compomics.pepshell.controllers.DAO.DAUtils;

import com.google.common.collect.TreeTraverser;
import com.google.common.io.Files;
import java.io.File;

/**
 *
 * @author Davy Maddelein
 */
public class FileUtils {

    public static boolean recursivelyDeleteFilesFromDir(File aDirectory) {
        TreeTraverser<File> traverser = Files.fileTreeTraverser();
        for (File aChild : traverser.children(aDirectory)) {
            if (aChild.isDirectory()) {
                if (!recursivelyDeleteFilesFromDir(aChild)) {
                    break;
                }
            } else {
                if (!aChild.delete()) {
                    break;
                }
            }
        }
        return aDirectory.delete();
    }

    static void diff(File result, File expResult) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
