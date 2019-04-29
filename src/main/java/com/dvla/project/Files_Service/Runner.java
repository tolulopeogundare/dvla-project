package com.dvla.project.Files_Service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
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
                    System.out.println("File Name = " + file.getName() +" + size = " + readXlsxFile(file) + readXlsxFile(file).size());
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

    private static List<String> readXlsxFile(final File file) throws IOException {
        List<String> data = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet dataSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = dataSheet.iterator();
        while (iterator.hasNext()){
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            while (cellIterator.hasNext()){
                Cell currentCell = cellIterator.next();
                System.out.print(currentCell.getStringCellValue() + "--");
                data.add(currentCell.getStringCellValue());
            }
            System.out.println();

        }
        return data;
    }
}
