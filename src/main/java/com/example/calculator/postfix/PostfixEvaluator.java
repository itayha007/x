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

        for (String token : postfix) {
            if (Character.isDigit(token.charAt(0)) || (token.length() > 1 && token.charAt(0) == '-')) {
                stack.push(Double.parseDouble(token));
            } else if (this.operatorUtils.isValidOperator(token)) {
                if (this.operatorUtils.isValidUnaryOperator(token) && !stack.isEmpty()) {
                    UnaryOperator<Double> unaryOperator = this.operatorUtils.getUnaryOperator(token);
                    double operand = stack.pop();
                    stack.push(unaryOperator.apply(operand));

                } else if (stack.size() >= 2) {
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    double result = this.operatorUtils.applyOperator(token, operand1, operand2);
                    stack.push(result);
                }
            }
        }

        return stack.isEmpty() ? Optional.empty() : Optional.of(stack.pop());
    }
}
