package com.example.calculator;

import java.util.List;
import java.util.stream.Collectors;

public class OperandReader {

    public List<Integer> extractOperands(List<String> lines) {
        return lines.stream()
                .filter(this::isNumeric)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+");
    }
}