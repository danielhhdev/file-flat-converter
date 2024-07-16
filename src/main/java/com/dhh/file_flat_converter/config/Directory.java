package com.dhh.file_flat_converter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("application.directories")
public class Directory {

    private String input;
    private String output;
    private String fail;
}
