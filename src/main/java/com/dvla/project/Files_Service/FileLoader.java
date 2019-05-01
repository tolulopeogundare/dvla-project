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

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileLoader.class);
    private static Map<String, String []> data = new TreeMap<>();

    private List<String> supportedMimeTypes = Arrays.asList("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/octet-stream");

    public List<File> getCsvFilesFromDirectory(File directory){
        final long startTime = System.currentTimeMillis();

        List<File> collectedFiles = new ArrayList<>();
        Deque<File> remainingDirs = new ArrayDeque<>();
        if(directory.exists()) {
            remainingDirs.add(directory);
            while(!remainingDirs.isEmpty()) {
                File dir = remainingDirs.removeLast();
                List<File> filesInDir = Arrays.asList(dir.listFiles());
                int fileNum = 0;
                for(File file : filesInDir){
                    fileNum = fileNum +1;
                    StringBuilder fileInfo = new StringBuilder();
                    fileInfo.append("Name=" + file.getName())
                            .append(" | MIME Type=" + getFileMimeType(file))
                            .append(" | Size(bytes)=" + file.length())
                            .append(" | Extension=" + file.getName().substring(file.getName().lastIndexOf(".")));
                    LOGGER.info(String.format("File %s Info : %s", fileNum, fileInfo));
                    if(supportedMimeTypes.contains(getFileMimeType(file))){
                        collectedFiles.add(file);
                    } else {
                        LOGGER.info(String.format("Ignoring unsupported file %s", file.getName()));
                    }
                    final long elapsedTime = System.currentTimeMillis() - startTime;
                    LOGGER.info(String.format("Processed %s files in %s ms", String.valueOf(filesInDir.size()), elapsedTime));
                }
            }
        }
        return collectedFiles;
    }

    private static String getFileMimeType(File file){
        MimetypesFileTypeMap files = new MimetypesFileTypeMap();
        return files.getContentType(file);
    }

    public void loadAllFiles(){
        File directory = new File(System.getProperty("user.dir") + "/src/main/java/resources/Files");
        LOGGER.info(String.format("Loading files from directory - %s", directory.getAbsolutePath()));
        int supportedFiles = 0;
        int unsupportedFiles = 0;
        for(File file : getCsvFilesFromDirectory(directory)){
            try {
                if(file.getName().endsWith(".csv")){
                    readCsvFiles(file);
                    supportedFiles = supportedFiles + 1;
                } else if(file.getName().endsWith(".xls")) {
                    getExcelFile(file);
                    supportedFiles = supportedFiles + 1;
                } else {
                    unsupportedFiles = unsupportedFiles + 1;
                    LOGGER.info(String.format("Ignoring %s | File not supported | Files should be in .csv or .xls extension", file.getName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOGGER.info(String.format("File loading complete. %s supported files loaded | %s unsupported files ignored", supportedFiles, unsupportedFiles));
    }

    private static void readCsvFiles(final File file) throws IOException {
        try (BOMInputStream bis = new BOMInputStream(new FileInputStream(file), false)) {
            List<String> csvData = IOUtils.readLines(bis, StandardCharsets.UTF_8);
            csvData.remove(0);
            for(String value : csvData){
                String [] values;
                String [] info = new String [2];
                values = value.split(",");
                info[0] = values[1].toUpperCase();
                info[1] = values[2].toUpperCase();
                data.put(values[0].toUpperCase().replace(" ", ""), info);
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
            String VRM = row.getCell(0).getStringCellValue().toUpperCase();
            String Make = row.getCell(1).getStringCellValue().toUpperCase();
            String Colour = row.getCell(2).getStringCellValue().toUpperCase();

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