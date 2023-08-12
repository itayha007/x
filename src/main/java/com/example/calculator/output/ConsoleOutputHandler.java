package com.example.calculator.output;

import com.example.calculator.Calculator;
import com.example.calculator.input.InputHandler;

import java.util.Optional;
import java.util.stream.Stream;

public class ConsoleOutputHandler implements OutputHandler {


    @Override
    public void presentResult(Optional<Double> result) {
        result.ifPresent(aDouble -> System.out.printf("Result: %s%n", result));
    }


    @Override
    public void processCalculations(InputHandler inputHandler, Calculator calculator) {
        Stream.generate(inputHandler::readCalculations)
                .takeWhile(lines -> !lines.isEmpty())
                .map(calculator::calculate)
                .forEach(this::presentResult);
    }

}
