package com.example.calculator.input;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileInputHandler implements InputHandler {

    private final String inputFilePath;

    @Override
    public List<String> readCalculations() {
        try {
            return Files.lines(Paths.get(this.inputFilePath))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.singletonList(e.getMessage());
        }
    }
}
