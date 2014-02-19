package com.compomics.pepshell.controllers.dataexport;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Davy
 */
public class PDFExport extends ImageExport {

    boolean append = false;

    @Override
    public void exportImage(BufferedImage imageToExport, String filename) {
        File exportFile = new File(ProgramVariables.EXPORTFOLDER, filename + ".pdf");
        if (append && exportFile.exists()) {

        } else {
            File exportImageFile = new File(System.getProperty("file.temp"), filename);
            try {
                ImageIO.write(imageToExport, "png", new FileOutputStream(exportImageFile));
                Image pdfImage = Image.getInstance(exportImageFile.getAbsolutePath());
                Document document = new Document(new Rectangle(pdfImage.absoluteX(), pdfImage.absoluteY()));
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(exportFile));
                document.open();
                document.newPage();
                document.add(pdfImage);
                document.close();
            } catch (FileNotFoundException ex) {
                FaultBarrier.getInstance().handleException(ex);
            } catch (    DocumentException | IOException ex) {
             FaultBarrier.getInstance().handleException(ex);}
        }
    }

    public void setAppendMode(boolean toAppend) {
        append = toAppend;
    }

    @Override
    public void exportComponent(Component aComponent, String fileName) {
        BufferedImage exportImage = new BufferedImage(aComponent.getWidth(), aComponent.getHeight(), BufferedImage.TYPE_INT_RGB);
        aComponent.paint(exportImage.createGraphics());
        this.exportImage(exportImage, fileName);

    }

}
