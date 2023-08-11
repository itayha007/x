package com.example.calculator.operator.binary;

public interface Operator<T> {


    T calculate(T operand1, T operand2);

    String getSymbol();

    Integer getPrecedence();


}
