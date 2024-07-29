package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.exception.FileParseException;
import com.dhh.file_flat_converter.model.PaymentIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessor {

    private final FileService fileService;
    private final ExecutorService demoPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public void process(Path path) {
        log.info("Starting process file {}", path.getFileName());

        var start1 = Instant.now();
        processPayments(path);
        var end1 = Instant.now();

        var start2 = Instant.now();
        processPayments2(path);
        var end2 = Instant.now();

        var duration1 = Duration.between(start1, end1);
        var duration2 = Duration.between(start2, end2);

        System.out.println("Duracion1: "+duration1.getNano());
        System.out.println("Duracion2: "+duration2.getNano());

        log.info("End to process file {}", path.getFileName());
    }

    private void processPayments2(Path path) {
        readFileAsync(path).thenAccept(lines -> {
            List<CompletableFuture<PaymentIO>> futures = lines.stream()
                    .map(ParseService::parsePaymentIOAsync).toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        });
    }


    private void processPayments(Path path) {

        var paymentList = readFileIntoArray(path);

        List<CompletableFuture<Void>> parallel = new ArrayList<>();
        paymentList.forEach(payment -> {
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                try {
                    ParseService.parseData(payment, PaymentIO.class);
                } catch (FileParseException fpe) {
                    log.info("Error when payment has been parsed. {}", payment);
                }
            }, demoPool);
            parallel.add(task);

        });

        CompletableFuture.allOf(parallel.toArray(new CompletableFuture[0])).join();
    }

    private List<String> readFileIntoArray(Path path) {
        log.info("Reading file {}", path.getFileName());
        return new ArrayList<>(fileService.readFile(String.valueOf(path)));
    }

    public CompletableFuture<List<String>> readFileAsync(Path path) {
        return CompletableFuture.supplyAsync(() -> fileService.readFile(String.valueOf(path)));
    }
}
