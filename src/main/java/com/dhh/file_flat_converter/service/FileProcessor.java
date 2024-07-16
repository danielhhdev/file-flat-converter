package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.config.Directory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessor {

    private final FileService fileService;
    private final Directory directory;


    public void process() {
        log.info("Starting process files from directory {}", directory.getInput());
        try {
            var fileList = fileService.getFileFromDirectory(directory.getInput());
            if (!fileList.isEmpty()) processFile(fileList.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processFile(Path path) {

        //leer el fichero

    }


}
