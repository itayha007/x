package com.example.calculator.postfix;

import com.example.calculator.operator.OperatorUtils;
import com.example.calculator.operator.unary.UnaryOperator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

@RequiredArgsConstructor
public class PostfixEvaluator {
    private final OperatorUtils operatorUtils;

    public Optional<Double> evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        postfix.forEach(token -> this.processToken(token, stack));

        if (stack.size() == 1) {
            return Optional.of(stack.pop());
        } else {
            throw new RuntimeException("invalid expression ");
        }
    }

    private void processToken(String token, Stack<Double> stack) {
        if (this.isNumeric(token)) {
            stack.push(Double.parseDouble(token));
        } else if (this.operatorUtils.isValidOperator(token)) {
            if (this.operatorUtils.isValidUnaryOperator(token) && !stack.isEmpty()) {
                this.processUnaryOperator(token, stack);
            } else if (stack.size() >= 2) {
                this.processBinaryOperator(token, stack);
            }
        } else {
            throw new RuntimeException("Invalid operand or opeartor");
        }
    }

    private boolean isNumeric(String token) {
        return Character.isDigit(token.charAt(0)) || (token.length() > 1 && token.charAt(0) == '-');
    }

    private void processUnaryOperator(String token, Stack<Double> stack) {
        UnaryOperator<Double> unaryOperator = this.operatorUtils.getUnaryOperator(token);
        Double operand = stack.pop();
        stack.push(unaryOperator.apply(operand));
    }

    private void processBinaryOperator(String token, Stack<Double> stack) {
        Double operand2 = stack.pop();
        Double operand1 = stack.pop();
        Double result = this.operatorUtils.applyOperator(token, operand1, operand2);
        stack.push(result);
    }

}
