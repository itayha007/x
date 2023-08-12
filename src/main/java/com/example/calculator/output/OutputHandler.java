package com.example.calculator.output;

import com.example.calculator.Calculator;
import com.example.calculator.input.InputHandler;

import java.util.Optional;

public interface OutputHandler {

    void presentResult(Optional<Double> result);

    void processCalculations(InputHandler inputHandler, Calculator calculator);

}
