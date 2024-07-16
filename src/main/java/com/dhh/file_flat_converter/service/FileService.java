package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.exception.FileServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService {

    //coger lista ficheros de un directorio
    public List<Path> getFileFromDirectory(String directory) throws IOException {
        List<Path> fileList = new ArrayList<>();
        Path dirPath = Paths.get(directory);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            stream.forEach(file -> {
                if (Files.isRegularFile(file)) fileList.add(file);
            });
            return fileList;
        }
    }

    //coger fichero

    public InputStream getFile(String input) {
        Path path = Paths.get(input);
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            log.info("Error when reading the file {}", path.getFileName());
            throw new FileServiceException("Error when reading the file" + path.getFileName(), e);
        }
    }

    public List<String> readFile(String input) {
        Path path = Paths.get(input);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            log.info("Error when reading the file {}", path.getFileName());
            throw new FileServiceException("Error when reading the file" + path.getFileName(), e);
        }
    }


    //borrar fichero


    //guardar fichero


    //escribir fichero
}
