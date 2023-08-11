package com.example.calculator.operator.unary;

public class CosOperator implements UnaryOperator<Double> {

    @Override
    public Double apply(Double operand) {
        return Math.cos(operand);
    }

    @Override
    public String getSymbol() {
        return "cos";
    }

    @Override
    public Integer getPrecedence() {
        return 3;
    }


}
