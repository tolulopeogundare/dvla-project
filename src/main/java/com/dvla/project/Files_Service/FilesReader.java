package com.dvla.project.Files_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.*;

public class FilesReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilesReader.class);
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
                for(File file : filesInDir){
                    StringBuilder fileInfo = new StringBuilder();
                    fileInfo.append("File Name=" + file.getName())
                            .append(" | MIME Type=" + getFileMimeType(file))
                            .append(" | Size(bytes)=" + file.length())
                            .append(" | File Extension=" + file.getName().substring(file.getName().lastIndexOf(".")));
                    System.out.println(fileInfo);
                    if(supportedMimeTypes.contains(getFileMimeType(file))){
                        collectedFiles.add(file);
                        LOGGER.info(String.format("Collected file %s", file.getName()));
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
}
