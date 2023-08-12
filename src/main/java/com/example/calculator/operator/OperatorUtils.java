package com.example.calculator.operator;

import com.example.calculator.operator.binary.Operator;
import com.example.calculator.operator.unary.UnaryOperator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OperatorUtils {

    private final List<Operator<Double>> binaryOperators;

    private final List<UnaryOperator<Double>> unaryOperators;

    public Operator<Double> getBinaryOperator(String operatorSymbol) {
        return binaryOperators.stream()
                .filter(op -> op.getSymbol().equals(operatorSymbol))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("invalid operator"));
    }

    public UnaryOperator<Double> getUnaryOperator(String operatorSymbol) {
        return unaryOperators.stream()
                .filter(op -> op.getSymbol().equals(operatorSymbol))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("invalid operator"));
    }


    public Integer getOperatorPrecedence(String operatorSymbol) {
        UnaryOperator<Double> matchingUnaryOperator = unaryOperators.stream()
                .filter(operator -> operator.getSymbol().equals(operatorSymbol))
                .findFirst()
                .orElse(null);

        if (matchingUnaryOperator != null) {
            return matchingUnaryOperator.getPrecedence();
        }

        return binaryOperators.stream()
                .filter(operator -> operator.getSymbol().equals(operatorSymbol))
                .mapToInt(Operator::getPrecedence)
                .findFirst()
                .orElse(0);
    }

    public boolean isValidOperator(String operatorSymbol) {
        return this.binaryOperators.stream()
                .anyMatch(operator -> operator.getSymbol().equals(operatorSymbol))
                || this.unaryOperators.stream()
                .anyMatch(operator -> operator.getSymbol().equals(operatorSymbol));
    }

    public boolean isValidUnaryOperator(String operatorSymbol) {
        return this.unaryOperators.stream()
                .anyMatch(operator -> operator.getSymbol().equals(operatorSymbol));
    }

    public Double applyOperator(String operator, Double operand1, Double operand2) {
        if (isValidUnaryOperator(operator) && operand2 == null) {
            UnaryOperator<Double> unaryOperator = getUnaryOperator(operator);
            return unaryOperator.apply(operand1);
        } else {
            Operator<Double> op = getBinaryOperator(operator);
            return op.calculate(operand1, operand2);
        }
    }
}