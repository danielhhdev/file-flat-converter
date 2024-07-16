package com.dhh.file_flat_converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileFlatConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileFlatConverterApplication.class, args);
	}

}
