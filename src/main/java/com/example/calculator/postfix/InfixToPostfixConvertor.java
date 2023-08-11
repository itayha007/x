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

        boolean lastTokenWasOperator = true; // Initialize to true to handle negative sign at the beginning

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            if (Character.isDigit(ch) || (ch == '-' && lastTokenWasOperator)) {
                StringBuilder number = new StringBuilder();
                if (ch == '-') {
                    number.append(ch);
                    i++;
                }
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    number.append(infix.charAt(i));
                    i++;
                }
                postfix.add(number.toString());
                i--;
                lastTokenWasOperator = false; // Set to false after a number
            } else if (Character.isLetter(ch)) {
                StringBuilder function = new StringBuilder();
                while (i < infix.length() && Character.isLetter(infix.charAt(i))) {
                    function.append(infix.charAt(i));
                    i++;
                }
                stack.push(function.toString());
                i--;
            } else if (ch == '(') {
                stack.push(Character.toString(ch));
                lastTokenWasOperator = true; // Set to true after an opening parenthesis
            } else if (ch == ')') {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                stack.pop();
                lastTokenWasOperator = false; // Set to false after a closing parenthesis
            } else if (this.operatorUtils.isValidOperator(String.valueOf(ch))) {
                while (!stack.isEmpty() && !stack.peek().equals("(") && this.operatorUtils.getOperatorPrecedence(String.valueOf(ch)) <= this.operatorUtils.getOperatorPrecedence(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(Character.toString(ch));
                lastTokenWasOperator = true; // Set to true after an operator
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;
    }
}
