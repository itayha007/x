package com.example.calculator.operator.binary;

public class SubtractionOperator implements Operator<Double> {

    @Override
    public String getSymbol() {
        return "-";
    }

    @Override
    public Integer getPrecedence() {
        return 1;
    }

    @Override
    public Double calculate(Double operand1, Double operand2) {
        return operand1 - operand2;
    }
}