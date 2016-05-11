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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Davy Maddelein
 */
public class PDFExport extends ImageExport {

    @Override
    public void exportImage(BufferedImage imageToExport, String filename) {
        File exportFile = new File(exportFolder, filename + ".pdf");

            File exportImageFile = new File(System.getProperty("file.temp"), filename);
            try {
                ImageIO.write(imageToExport, "png", new FileOutputStream(exportImageFile));
                Image pdfImage = Image.getInstance(exportImageFile.getAbsolutePath());
                Document document = new Document(new Rectangle(pdfImage.absoluteX(), pdfImage.absoluteY()));
                PdfWriter.getInstance(document, new FileOutputStream(exportFile));
                document.open();
                document.newPage();
                document.add(pdfImage);
                document.close();
            } catch (DocumentException | IOException ex) {
             //FaultBarrier.getInstance().handleException(ex);
            }
    }

    @Override
    public void exportComponent(Component aComponent, String fileName) {
        BufferedImage exportImage = new BufferedImage(aComponent.getWidth(), aComponent.getHeight(), BufferedImage.TYPE_INT_RGB);
        aComponent.paint(exportImage.createGraphics());
        this.exportImage(exportImage, fileName);

    }

}
