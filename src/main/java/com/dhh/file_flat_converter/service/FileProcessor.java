package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.exception.FIleParseException;
import com.dhh.file_flat_converter.exception.ReadException;
import com.dhh.file_flat_converter.model.PaymentIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dhh.file_flat_converter.constant.Constant.START_TRANSACTION;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessor {

    private final FileService fileService;
    private final ParseService parseService;

    public void process(Path path) {
        log.info("Starting process file {}", path.getFileName());
        var paymentList = readFileIntoArray(path);

        processPayments(paymentList);


    }
    /*
    mapeo el pago
    lo envio a otro micro
    si falla creo un nack con los fallos y especifico cual
     */
    private void processPayments(List<String> list) {

       var pay = parseService.parseData(list.get(0), PaymentIO.class);



    }

    private List<String> readFileIntoArray(Path path) {
        log.info("Reading file {}", path.getFileName());
        return fileService.readFile(String.valueOf(path))
                .stream()
                .filter(line -> line.startsWith(START_TRANSACTION))
                .collect(Collectors.toList());
    }
}
