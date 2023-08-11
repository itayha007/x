package com.example.calculator;

import com.example.calculator.operator.binary.Operator;
import com.example.calculator.operator.unary.UnaryOperator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OperatorReader {

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
        if ("cos".equals(operatorSymbol) ||"sin".equals(operatorSymbol)) {
            return 3;
        }
        return this.binaryOperators.stream()
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
}