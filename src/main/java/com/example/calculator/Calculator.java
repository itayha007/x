package com.example.calculator;

import com.example.calculator.input.InputHandler;
import com.example.calculator.output.FileOutputHandler;
import com.example.calculator.output.OutputHandler;
import com.example.calculator.postfix.InfixToPostfixConvertor;
import com.example.calculator.postfix.PostfixEvaluator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Calculator {

    private final InfixToPostfixConvertor convertor;

    private final PostfixEvaluator evaluator;


    public void processCalculations(InputHandler inputHandler, OutputHandler outputHandler) {
        Stream.generate(inputHandler::readCalculations)
                .takeWhile(lines -> !lines.isEmpty())
                .map(this::calculate)
                .forEach(outputHandler::presentResult);
    }

    public void processExpressionsFromFile(String inputFilePath, String outputFilePath) {
        try {
            List<String> expressions = Files.lines(Paths.get(inputFilePath))
                    .collect(Collectors.toList());

            FileOutputHandler outputHandler = new FileOutputHandler(outputFilePath);
            expressions.stream()
                    .map(expression -> {
                        Optional<Double> result = calculate(Collections.singletonList(expression));
                        outputHandler.setCurrentExpression(expression);
                        return result;
                    })
                    .forEach(outputHandler::presentResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Optional<Double> calculate(List<String> input) {
        List<String> postfixExpression = this.convertor.infixToPostfix(String.join("", input));
        return this.evaluator.evaluatePostfix(postfixExpression);

    }


}
