package com.compomics.partialtryp.controllers.dataexport;

import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.QuantedPeptideGroup;
import javax.swing.JList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Davy
 */
public class ExcelExport {
    
    private static void addHeadersToProteinsSheet(Sheet sheet, boolean quant) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Uniport accession");
        headerRow.createCell(1).setCellValue("peptide for protein");
        if (quant) {
            headerRow.createCell(2).setCellValue("peptideRatio");
        }
    }

    private static void excelPDBExportOfProtein(Protein protein, Row pdbRow) {
        pdbRow.createCell(0).setCellValue(protein.getProteinAccession());
        pdbRow.createCell(1).setCellValue(DataPreparationForExport.returnListCommaSeparated(protein.getPdbFileNames()));
    }

    private static void addHeadersToPDBSheet(Sheet PDBSheet) {
        Row PDBHeaderRow = PDBSheet.createRow(0);
        PDBHeaderRow.createCell(0).setCellValue("Uniprot Accession");
        PDBHeaderRow.createCell(1).setCellValue("PDB Accessions");
    }

    public static void exportProteinToExcelWorkBook(JList listOfProteins, Workbook workBook) throws ArrayIndexOutOfBoundsException {
        int row = 1;
        Protein proteinToExport;
        int listWalkCounter = 0;
        Sheet proteinSheet = workBook.createSheet("proteins");
        Sheet PDBSheet = workBook.createSheet("PDB");

        addHeadersToProteinsSheet(proteinSheet, ((Protein) listOfProteins.getModel().getElementAt(0)).getPeptideGroupsForProtein().get(0) instanceof QuantedPeptideGroup);
        addHeadersToPDBSheet(PDBSheet);

        while (listWalkCounter < listOfProteins.getModel().getSize()) {
            proteinToExport = (Protein) listOfProteins.getModel().getElementAt(listWalkCounter);
            proteinSheet.createRow(row);
            excelProteinInfoExportOfProtein(proteinToExport, proteinSheet);
            excelPDBExportOfProtein(proteinToExport, PDBSheet.createRow(listWalkCounter + 1));
            listWalkCounter++;
        }
    }

    private static void excelProteinInfoExportOfProtein(Protein proteinToExport, Sheet proteinSheet) {
        int column = 0;
        Row proteinRow = proteinSheet.createRow(proteinSheet.getLastRowNum()+1);
        proteinRow.createCell(column).setCellValue(proteinToExport.getProteinAccession());
        column++;
        for (PeptideGroup peptideGroup : proteinToExport.getPeptideGroupsForProtein()) {
            proteinRow.createCell(column).setCellValue(peptideGroup.getShortestPeptide().getSequence());
            if (peptideGroup instanceof QuantedPeptideGroup) {
                proteinRow.createCell(column+1).setCellValue(Integer.toString(((QuantedPeptideGroup) peptideGroup).getRatio()));
            }
            proteinRow = proteinSheet.createRow(proteinSheet.getLastRowNum()+1);
        }
    }   
}