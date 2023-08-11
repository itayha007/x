package com.example.calculator.input;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleInputHandler implements InputHandler {

    private final Scanner scanner;

    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public List<String> readCalculations() {
        return Stream.generate(this.scanner::nextLine)
                .takeWhile(line -> !line.isEmpty())
                .collect(Collectors.toList());
    }


}
