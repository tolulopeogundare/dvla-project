package com.dvla.project.Files_Service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    private static FilesReader filesReader = new FilesReader();

    public static void main(String args[]){
        File directory = new File(System.getProperty("user.dir") + "/src/main/java/resources/Files");
        for(File file : filesReader.getCsvFilesFromDirectory(directory)){
            try {
                if(file.getName().endsWith(".csv")){
                    System.out.println("File Name = " + file.getName() +" + size = " + readCsvFiles(file) + readCsvFiles(file).size());
                } else {
                    System.out.println("File Name = " + file.getName());
                    readXlsxFile(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> readCsvFiles(final File file) throws IOException {
        try (BOMInputStream bis = new BOMInputStream(new FileInputStream(file), false)) {
            return IOUtils.readLines(bis, StandardCharsets.UTF_8);
        }
    }

    private static void readXlsxFile(final File file) throws IOException, InvalidFormatException {

                // Creating a Workbook from an Excel file (.xls or .xlsx)
                Workbook workbook = WorkbookFactory.create(file);

                // Retrieving the number of sheets in the Workbook
                System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */
                // 2. Or you can use a for-each loop
                System.out.println("Retrieving Sheets using for-each loop");
                for(Sheet sheet: workbook) {
                    System.out.println("=> " + sheet.getSheetName());
                }

        /*
           ==================================================================
           Iterating over all the rows and columns in a Sheet (Multiple ways)
           ==================================================================
        */

                // Getting the Sheet at index zero
                Sheet sheet = workbook.getSheetAt(0);

                // Create a DataFormatter to format and get each cell's value as String
                DataFormatter dataFormatter = new DataFormatter();

                // 2. Or you can use a for-each loop to iterate over the rows and columns
                System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
                for (Row row: sheet) {
                    for(Cell cell: row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                        System.out.print(cellValue + "\t");
                    }
                    System.out.println();
                }
                // Closing the workbook
                workbook.close();
            }
}
