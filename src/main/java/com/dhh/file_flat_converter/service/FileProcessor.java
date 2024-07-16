package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.config.Directory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessor {

    private final FileService fileService;

    public void process(Path path) {
        log.info("Starting process files {}", path.getFileName());

    }

    private void processFile(Path path) {
        //leer el fichero

        /*
        crear los mapper
        leer el fichero y guardarlo en un arrayList
        con comparableFuture hacer llamadas a un servicio
        esperar respuesta del micro
        y si es fallo que no se ha guardado en BD se crea un ficjero con los que han fallado
         */

    }


}
