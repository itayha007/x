package com.example.calculator.output;

import com.example.calculator.Calculator;
import com.example.calculator.input.InputHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
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

    @Override
    public void processCalculations(InputHandler inputHandler, Calculator calculator) {
        List<String> expressions = inputHandler.readCalculations();

        FileOutputHandler outputHandler = new FileOutputHandler(outputFilePath);
        expressions.stream()
                .map(expression -> {
                    Optional<Double> result = calculator.calculate(Collections.singletonList(expression));
                    outputHandler.setCurrentExpression(expression);
                    return result;
                })
                .forEach(outputHandler::presentResult);

    }


}
