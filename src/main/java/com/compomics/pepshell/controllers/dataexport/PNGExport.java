package com.compomics.pepshell.controllers.dataexport;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Davy
 */
public class PNGExport extends ImageExport {

    @Override
    public void exportComponent(Component aComponent, String fileName) {
        BufferedImage image = new BufferedImage(aComponent.getWidth(), aComponent.getHeight(), BufferedImage.TYPE_INT_RGB);
        aComponent.paint(image.createGraphics());
        this.exportImage(image, fileName);
    }

    @Override
    public void exportImage(BufferedImage imageToExport, String filename) {
        try {
            ImageIO.write(imageToExport, "png", new FileOutputStream(new File(ProgramVariables.EXPORTFOLDER, filename + ".png")));
        } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }

    }

}
