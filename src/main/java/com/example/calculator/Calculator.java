package com.example.calculator;

import com.example.calculator.input.InputHandler;
import com.example.calculator.input.OutputHandler;
import com.example.calculator.operator.binary.Operator;
import com.example.calculator.operator.unary.UnaryOperator;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Calculator {
    private final OperatorReader operatorReader;


    public void processCalculations(InputHandler inputHandler, OutputHandler outputHandler) {
        Stream.generate(inputHandler::readCalculations)
                .takeWhile(lines -> !lines.isEmpty())
                .map(this::calculate)
                .forEach(outputHandler::presentResult);
    }

    private Optional<Double> calculate(List<String> input) {
        List<String> x = infixToPostfix(String.join("", input));
        return evaluateRPN(x);

    }


    private List<String> infixToPostfix(String infix) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        boolean lastTokenWasOperator = true; // Initialize to true to handle negative sign at the beginning

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            if (Character.isDigit(ch) || (ch == '-' && lastTokenWasOperator)) {
                StringBuilder number = new StringBuilder();
                if (ch == '-') {
                    number.append(ch);
                    i++;
                }
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    number.append(infix.charAt(i));
                    i++;
                }
                postfix.add(number.toString());
                i--;
                lastTokenWasOperator = false; // Set to false after a number
            } else if (Character.isLetter(ch)) {
                StringBuilder function = new StringBuilder();
                while (i < infix.length() && Character.isLetter(infix.charAt(i))) {
                    function.append(infix.charAt(i));
                    i++;
                }
                stack.push(function.toString());
                i--;
            } else if (ch == '(') {
                stack.push(Character.toString(ch));
                lastTokenWasOperator = true; // Set to true after an opening parenthesis
            } else if (ch == ')') {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                stack.pop();
                lastTokenWasOperator = false; // Set to false after a closing parenthesis
            } else if (this.operatorReader.isValidOperator(String.valueOf(ch))) {
                while (!stack.isEmpty() && !stack.peek().equals("(") && this.operatorReader.getOperatorPrecedence(String.valueOf(ch)) <= this.operatorReader.getOperatorPrecedence(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(Character.toString(ch));
                lastTokenWasOperator = true; // Set to true after an operator
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;
    }


    private Optional<Double> evaluateRPN(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (Character.isDigit(token.charAt(0)) || (token.length() > 1 && token.charAt(0) == '-')) {
                stack.push(Double.parseDouble(token));
            } else if (this.operatorReader.isValidOperator(token)) {
                if (this.operatorReader.isValidUnaryOperator(token) && stack.size() >= 1) {
                    UnaryOperator<Double> unaryOperator = this.operatorReader.getUnaryOperator(token);
                    double operand = stack.pop();
                    if (token.charAt(0) == '-') {
                        stack.push(-unaryOperator.apply(operand));
                    } else {
                        stack.push(unaryOperator.apply(operand));
                    }
                } else if (stack.size() >= 2) {
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    double result = applyOperator(token, operand1, operand2);
                    stack.push(result);
                }
            }
        }

        return stack.isEmpty() ? Optional.empty() : Optional.of(stack.pop());
    }




    private Double applyOperator(String operator, Double operand1, Double operand2) {
        if (this.operatorReader.isValidUnaryOperator(operator) && operand2 == null) {
            UnaryOperator<Double> unaryOperator = this.operatorReader.getUnaryOperator(operator);
            return unaryOperator.apply(operand1);
        } else {
            Operator<Double> op = operatorReader.getBinaryOperator(operator);
            return op.calculate(operand1, operand2);
        }
    }



}
