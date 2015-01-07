package com.compomics.pepshell.controllers.dataexport;

import com.compomics.pepshell.model.Protein;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Davy Maddelein
 */
class DataExport {

    public void exportToTSV(JList listOfProteins, File saveLocation) throws IOException {
        Protein protein;
        StringBuilder tsvOutput = new StringBuilder();
        for (int i = 0; i < listOfProteins.getModel().getSize(); i++) {
            protein = (Protein) listOfProteins.getModel().getElementAt(i);
            tsvOutput.append(protein.getProteinAccession()).append("\t");
            tsvOutput.append(DataPreparationForExport.returnListCommaSeparated(protein.getPeptideGroups())).append("\t");
            tsvOutput.append(DataPreparationForExport.returnListCommaSeparated(protein.getPdbFilesInfo())).append("\t");
            tsvOutput.append("\n");
        }
        writeFileToDisk(tsvOutput.toString(), saveLocation);
    }

    public void exportPNG(BufferedImage image, File saveLocation) throws IOException {
        ImageIO.write(image, "png", saveLocation);
    }

    public void excelProteinExport(JList listOfProteins, File saveLocation) throws FileNotFoundException, IOException {
        Workbook workBook = new HSSFWorkbook();
        ExcelExport.exportProteinToExcelWorkBook(listOfProteins, workBook);
        writeFileToDisk(workBook, saveLocation);
    }

    private void writeFileToDisk(Workbook workBook, File saveLocation) throws FileNotFoundException, IOException {
        try (FileOutputStream fos = new FileOutputStream(saveLocation)) {
            workBook.write(fos);
            fos.flush();
        }
    }

    private void writeFileToDisk(String output, File saveLocation) throws IOException {
        try (FileWriter fileWriter = new FileWriter(saveLocation)) {
            fileWriter.write(output);
            fileWriter.flush();
        }
    }
}