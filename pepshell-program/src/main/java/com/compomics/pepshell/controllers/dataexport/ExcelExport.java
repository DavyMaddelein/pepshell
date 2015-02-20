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

import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedPeptideGroup;
import javax.swing.JList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Davy Maddelein
 */
class ExcelExport {

    private static void addHeadersToProteinsSheet(Sheet sheet, boolean quant) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Uniport accession");
        headerRow.createCell(1).setCellValue("peptide for protein");
        if (quant) {
            headerRow.createCell(2).setCellValue("peptideRatio");
        }
    }

    private static void excelPDBExportOfProtein(PepshellProtein pepshellProtein, Row pdbRow) {
        pdbRow.createCell(0).setCellValue(pepshellProtein.getVisibleAccession());
        pdbRow.createCell(1).setCellValue(DataPreparationForExport.returnListCommaSeparated(pepshellProtein.getPdbFilesInfo()));
    }

    private static void addHeadersToPDBSheet(Sheet PDBSheet) {
        Row PDBHeaderRow = PDBSheet.createRow(0);
        PDBHeaderRow.createCell(0).setCellValue("Uniprot Accession");
        PDBHeaderRow.createCell(1).setCellValue("PDB Accessions");
    }

    public static void exportProteinToExcelWorkBook(JList listOfProteins, Workbook workBook) throws ArrayIndexOutOfBoundsException {
        int row = 1;
        PepshellProtein pepshellProteinToExport;
        int listWalkCounter = 0;
        Sheet proteinSheet = workBook.createSheet("proteins");
        Sheet PDBSheet = workBook.createSheet("PDB");

        addHeadersToProteinsSheet(proteinSheet, ((PepshellProtein) listOfProteins.getModel().getElementAt(0)).getPeptideGroups().get(0) instanceof QuantedPeptideGroup);
        addHeadersToPDBSheet(PDBSheet);

        while (listWalkCounter < listOfProteins.getModel().getSize()) {
            pepshellProteinToExport = (PepshellProtein) listOfProteins.getModel().getElementAt(listWalkCounter);
            proteinSheet.createRow(row);
            excelProteinInfoExportOfProtein(pepshellProteinToExport, proteinSheet);
            excelPDBExportOfProtein(pepshellProteinToExport, PDBSheet.createRow(listWalkCounter + 1));
            listWalkCounter++;
        }
    }

    private static void excelProteinInfoExportOfProtein(PepshellProtein pepshellProteinToExport, Sheet proteinSheet) {
        int column = 0;
        Row proteinRow = proteinSheet.createRow(proteinSheet.getLastRowNum() + 1);
        proteinRow.createCell(column).setCellValue(pepshellProteinToExport.getVisibleAccession());
        column++;
        for (PeptideGroup peptideGroup : pepshellProteinToExport.getPeptideGroups()) {
            proteinRow.createCell(column).setCellValue(peptideGroup.getShortestPeptide().getSequence());
            if (peptideGroup instanceof QuantedPeptideGroup) {
                proteinRow.createCell(column + 1).setCellValue(Integer.toString(((QuantedPeptideGroup) peptideGroup).getRatio()));
            }
            proteinRow = proteinSheet.createRow(proteinSheet.getLastRowNum() + 1);
        }
    }
}
