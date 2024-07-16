package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.model.PaymentIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

        //var paymentIOList = parseToPayment();


    }

    private void parseToPayment() {

        /*
        mapeo el pago
        lo envio a otro micro
        si falla creo un nack con los fallos y especifico cual
         */
    }

    private List<String> readFileIntoArray(Path path) {
        log.info("Reading file {}", path.getFileName());

        return fileService.readFile(String.valueOf(path))
                .stream()
                .filter(line1 -> line1.startsWith(START_TRANSACTION))
                .collect(Collectors.toList());


//        try (BufferedReader inputFile = new BufferedReader(new InputStreamReader(fileInputStrean, StandardCharsets.UTF_8))) {
//            String line = inputFile.readLine();
//            while (line != null) {
//                Optional.of(line)
//                        .filter(line1 -> line1.startsWith(START_TRANSACTION))
//                        .map(line1 -> parseService.parseData(line1, PaymentIO.class))
//                        .ifPresent(paymentIOList::add);
//
//                line = inputFile.readLine();
//            }
//        } catch (Exception e) {
//            log.warn("Error when reading file {}", path.getFileName());
//            throw new ReadException("Error when reading file", e);
//        }

    }

}
