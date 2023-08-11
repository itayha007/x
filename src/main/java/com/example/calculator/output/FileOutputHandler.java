package com.example.calculator.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class FileOutputHandler implements OutputHandler {
    private final String outputFilePath;

    private String currentExpression;

    public FileOutputHandler(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void setCurrentExpression(String expression) {
        this.currentExpression = expression;
    }

    @Override
    public void presentResult(Optional<Double> result) {
        try {
            FileWriter writer = new FileWriter(outputFilePath, true);
            if (result.isPresent()) {
                writer.write(currentExpression + " = " + result.get() + System.lineSeparator());
            } else {
                writer.write(currentExpression + " = N/A" + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
