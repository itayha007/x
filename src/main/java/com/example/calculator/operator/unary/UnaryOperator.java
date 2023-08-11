package com.example.calculator.operator.unary;

public interface UnaryOperator<T> {

    T apply(Double operand);

    String getSymbol();

    Integer getPrecedence();
}
