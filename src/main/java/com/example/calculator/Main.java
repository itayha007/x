package com.example.calculator;

import com.example.calculator.input.ConsoleInputHandler;
import com.example.calculator.operator.OperatorUtils;
import com.example.calculator.operator.binary.*;
import com.example.calculator.operator.unary.CosOperator;
import com.example.calculator.operator.unary.SinOperator;
import com.example.calculator.operator.unary.UnaryOperator;
import com.example.calculator.output.ConsoleOutputHandler;
import com.example.calculator.postfix.InfixToPostfixConvertor;
import com.example.calculator.postfix.PostfixEvaluator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Operator<Double>> operators = List.of(new AdditionOperator(), new SubtractionOperator(), new DivisionOperator(), new MultiplicationOperator());
        List<UnaryOperator<Double>> unaryOperators = List.of(new CosOperator(), new SinOperator());

        Calculator calculator = new Calculator(new InfixToPostfixConvertor(new OperatorUtils(operators, unaryOperators)), new PostfixEvaluator(new OperatorUtils(operators, unaryOperators)));
        calculator.processCalculations(new ConsoleInputHandler(), new ConsoleOutputHandler());
    }


}
