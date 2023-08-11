package com.example.calculator.postfix;

import com.example.calculator.operator.OperatorUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RequiredArgsConstructor
public class InfixToPostfixConvertor {

    private final OperatorUtils operatorUtils;


    public List<String> infixToPostfix(String infix) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        boolean lastTokenWasOperator = true;

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            if (Character.isDigit(ch) || (ch == '-' && lastTokenWasOperator)) {
                i = processNumber(infix, i, postfix);
                lastTokenWasOperator = false;
            } else if (Character.isLetter(ch)) {
                i = processFunction(infix, i, stack);
            } else if (ch == '(') {
                stack.push(Character.toString(ch));
                lastTokenWasOperator = true;
            } else if (ch == ')') {
                processClosingParenthesis(stack, postfix);
                lastTokenWasOperator = false;
            } else if (operatorUtils.isValidOperator(String.valueOf(ch))) {
                processOperator(ch, stack, postfix);
                lastTokenWasOperator = true;
            }
        }

        processRemainingOperators(stack, postfix);

        return postfix;
    }

    private int processNumber(String infix, int startIndex, List<String> postfix) {
        StringBuilder number = new StringBuilder();
        int i = startIndex;
        if (infix.charAt(i) == '-') {
            number.append('-');
            i++;
        }
        while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
            number.append(infix.charAt(i));
            i++;
        }

        postfix.add(number.toString());
        return i - 1;
    }

    private int processFunction(String infix, int startIndex, Stack<String> stack) {
        StringBuilder function = new StringBuilder();
        int i = startIndex;

        while (i < infix.length() && Character.isLetter(infix.charAt(i))) {
            function.append(infix.charAt(i));
            i++;
        }

        stack.push(function.toString());
        return i - 1;
    }

    private void processClosingParenthesis(Stack<String> stack, List<String> postfix) {
        while (!stack.isEmpty() && !stack.peek().equals("(")) {
            postfix.add(stack.pop());
        }
        stack.pop();
    }

    private void processOperator(char ch, Stack<String> stack, List<String> postfix) {
        while (!stack.isEmpty() && !stack.peek().equals("(") && operatorUtils.getOperatorPrecedence(String.valueOf(ch)) <= operatorUtils.getOperatorPrecedence(stack.peek())) {
            postfix.add(stack.pop());
        }
        stack.push(Character.toString(ch));
    }

    private void processRemainingOperators(Stack<String> stack, List<String> postfix) {
        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }
    }

}
