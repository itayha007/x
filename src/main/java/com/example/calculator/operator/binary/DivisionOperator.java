package com.example.calculator.operator.binary;

public class DivisionOperator implements Operator<Double> {

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public Integer getPrecedence() {
        return 2;
    }

    @Override
    public Double calculate(Double operand1, Double operand2) {
        if (operand2 == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return operand1 / operand2;
    }
}