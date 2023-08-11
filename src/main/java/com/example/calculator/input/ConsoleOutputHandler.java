package com.example.calculator.input;

import java.util.Optional;

public class ConsoleOutputHandler implements OutputHandler {

    @Override
    public void presentResult(Optional<Double> result) {
        result.ifPresent(aDouble -> System.out.printf("Result: %s%n", result));
    }
}
