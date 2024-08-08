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
    private final ExecutorService demoPool = Executors.newFixedThreadPool(1000);

    public void process(Path path) {
        log.info("Starting process file {}", path.getFileName());

        var start1 = Instant.now();
        processPayments(path);
        var end1 = Instant.now();

        var start2 = Instant.now();
        processAllPayments(path);
        var end2 = Instant.now();

        var duration1 = Duration.between(start1, end1);
        var duration2 = Duration.between(start2, end2);

        System.out.println("Duracion1: " + duration1.getNano());
        System.out.println("Duracion2: " + duration2.getNano());

        log.info("End to process file {}", path.getFileName());
    }

    private void processAllPayments(Path path) {
        readFileAsync(path)                                                                         // lee el archivo de forma asincrona, usa un hilo aparte
                .thenAccept(lines -> {                                                              // cuando termina de procesarlo empieza con la siguiente tarea
                    List<CompletableFuture<Void>> futures = lines
                            .stream()                                                                       // se crea un stream de lineas
                            .map(this::processPayment)                                                      // cada linea se procesa en un hilo
                            .toList();                                                                      // se guarda en una lista
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();              // esa lista de completablesFutures se espera a que terminen todos para seguir
                });
    }

    /*
    En este metodo se crea un CompletableFuture para procesar cada payment
    es decir, cuando ejecutemos esto se creara un hilo por cada payment
     */
    private CompletableFuture<Void> processPayment(String payment) {
        return CompletableFuture.runAsync(() -> {
            ParseService.parseData(payment, PaymentIO.class);
            // TODO llamada a micro esterno para guardarlo en Base de datos
        }, demoPool);
    }

    //TODO añadir control de excepciones


    private void processPayments(Path path) {

        var paymentList = readFileIntoArray(path);                                       // se lee el fichero con el hilo principal

        List<CompletableFuture<Void>> parallel = new ArrayList<>();
        paymentList.forEach(payment -> {                                                            // recorremos todas las lineas y por cada linea parseamos en un hilo diferente
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                try {
                    ParseService.parseData(payment, PaymentIO.class);
                } catch (FileParseException fpe) {
                    log.info("Error when payment has been parsed. {}", payment);
                }
            }, demoPool);
            parallel.add(task);                                                                      // añadimos cada completableFuture en la lista

        });

        CompletableFuture.allOf(parallel.toArray(new CompletableFuture[0])).join();                  // esperamos a que todos terminen para continuar
    }

    private List<String> readFileIntoArray(Path path) {
        log.info("Reading file {}", path.getFileName());
        return new ArrayList<>(fileService.readFile(String.valueOf(path)));
    }

    public CompletableFuture<List<String>> readFileAsync(Path path) {
        return CompletableFuture.supplyAsync(() -> fileService.readFile(String.valueOf(path)), demoPool);
    }
}
