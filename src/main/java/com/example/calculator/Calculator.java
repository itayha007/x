package com.example.calculator;

import com.example.calculator.input.InputHandler;
import com.example.calculator.output.OutputHandler;
import com.example.calculator.postfix.InfixToPostfixConvertor;
import com.example.calculator.postfix.PostfixEvaluator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class Calculator {

    private final InfixToPostfixConvertor convertor;

    private final PostfixEvaluator evaluator;


    public void performCalculation(InputHandler inputHandler, OutputHandler outputHandler, Calculator calculator) {
        outputHandler.processCalculations(inputHandler, calculator);
    }


    public Optional<Double> calculate(List<String> input) {
        List<String> postfixExpression = this.convertor.infixToPostfix(String.join("", input));
        return this.evaluator.evaluatePostfix(postfixExpression);

    }


}
