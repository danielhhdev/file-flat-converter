package com.dhh.file_flat_converter.task;

import com.dhh.file_flat_converter.config.Directory;
import com.dhh.file_flat_converter.service.FileProcessor;
import com.dhh.file_flat_converter.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class DirectoryProcessorTask {

    private final FileService fileService;
    private final FileProcessor fileProcessor;
    private final Directory directory;

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10000)
    public void directoryProcessor() {
        try {
            var fileList = fileService.getFileFromDirectory(directory.getInput());
            if (!fileList.isEmpty()) fileProcessor.process(fileList.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
