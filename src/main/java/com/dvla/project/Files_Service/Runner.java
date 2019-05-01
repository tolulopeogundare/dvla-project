package com.dvla.project.Files_Service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    private static FilesReader filesReader = new FilesReader();
    private static Map<String, String []> data = new TreeMap<>();

    public void loadAllFiles(){
        File directory = new File(System.getProperty("user.dir") + "/src/main/java/resources/Files");
        for(File file : filesReader.getCsvFilesFromDirectory(directory)){
            try {
                if(file.getName().endsWith(".csv")){
                    System.out.println("File Name = " + file.getName());
                    readCsvFiles(file);
                } else {
                    System.out.println("File Name = " + file.getName());
                    getExcelFile(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("ALL DATA = " + getData().toString());
    }

    private static void readCsvFiles(final File file) throws IOException {
        try (BOMInputStream bis = new BOMInputStream(new FileInputStream(file), false)) {
            List<String> csvData = IOUtils.readLines(bis, StandardCharsets.UTF_8);

            for(String value : csvData){
                String [] values;
                String [] info = new String [2];
                values = value.split(",");
                info[0] = values[1];
                info[1] = values[2];
                data.put(values[0], info);
            }
        }
    }

    private static void getExcelFile(final File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;

        if(file.getName().endsWith(".xlsx")){
            workbook = new XSSFWorkbook(inputStream);
        } else if(file.getName().endsWith(".xls")){
            workbook = new HSSFWorkbook(inputStream);
        }

        Sheet sheet = workbook.getSheetAt(0);

        int numOfRows = sheet.getLastRowNum();

        for(int i=0 ; i < numOfRows ; i++){
            Row row = sheet.getRow(i +1);
            String VRM = row.getCell(0).getStringCellValue();
            String Make = row.getCell(1).getStringCellValue();
            String Colour = row.getCell(2).getStringCellValue();

            String [] info = new String[3] ;

            info [0] = Make;
            info [1] = Colour;
            data.put(VRM, info);
        }
    }

    public Map<String, String[]> getData(){ return data; }

    public String getVehicleDetails(String VRM, String Attribute){
        String [] value = data.get(VRM);
        if(Attribute.equals("Make")){
            return value[0];
        } else{
            return value[1];
        }
    }
}