package com.dhh.file_flat_converter.service;

import com.dhh.file_flat_converter.exception.FileParseException;
import com.dhh.file_flat_converter.model.PaymentIO;
import org.beanio.StreamFactory;
import org.beanio.Unmarshaller;
import org.beanio.builder.StreamBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ParseService {

    public static <T> T parseData(String linea, final Class<T> clazz) throws FileParseException {

        try {
            StreamFactory factory = StreamFactory.newInstance();

            String recordName = clazz.getSimpleName();

            StreamBuilder builder = new StreamBuilder(recordName)
                    .format("fixedlength")
                    .addRecord(clazz);
            factory.define(builder);

            Unmarshaller unmarshaller = factory.createUnmarshaller(recordName);
            return clazz.cast(unmarshaller.unmarshal(linea));
        } catch (Exception e) {
            throw new FileParseException();
        }
    }


    public static CompletableFuture<PaymentIO> parsePaymentIOAsync(String line) {
        return CompletableFuture.supplyAsync(() -> parseData(line, PaymentIO.class));
    }

}
