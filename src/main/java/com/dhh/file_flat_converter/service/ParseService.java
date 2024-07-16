package com.dhh.file_flat_converter.service;

import org.beanio.StreamFactory;
import org.beanio.Unmarshaller;
import org.beanio.builder.StreamBuilder;
import org.springframework.stereotype.Service;

@Service
public class ParseService {

    public <T> T parseData(String linea, final Class<T> clazz) {

        StreamFactory factory = StreamFactory.newInstance();

        String recordName = clazz.getSimpleName();

        StreamBuilder builder = new StreamBuilder(recordName)
                .format("fixedLength")
                .addRecord(clazz);
        factory.define(builder);

        Unmarshaller unmarshaller = factory.createUnmarshaller(recordName);
        return clazz.cast(unmarshaller.unmarshal(linea));
    }
}
