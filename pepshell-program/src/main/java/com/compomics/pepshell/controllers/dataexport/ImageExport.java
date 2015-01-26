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

package com.compomics.pepshell.controllers.dataexport;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Davy Maddelein
 */
abstract class ImageExport {

    private File exportFolder;

    public void setExportFolder(File aFolder) {
        this.exportFolder = aFolder;
    }

    public void exportComponent(Component aComponent) {
        this.exportComponent(aComponent, Long.toString(System.currentTimeMillis() / 1000));
    }

    protected abstract void exportComponent(Component aComponent, String fileName);

    public void exportImage(BufferedImage imageToExport) {
        this.exportImage(imageToExport, Long.toString(System.currentTimeMillis() / 1000));
    }

    protected abstract void exportImage(BufferedImage imageToExport, String filename);
}
