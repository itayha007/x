package com.example.calculator.operator.unary;

public class SinOperator implements UnaryOperator<Double> {


    @Override
    public Double apply(Double operand) {
        return Math.sin(operand);
    }

    @Override
    public String getSymbol() {
        return "sin";
    }

    @Override
    public Integer getPrecedence() {
        return 3;
    }


}
