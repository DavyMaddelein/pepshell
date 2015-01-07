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
