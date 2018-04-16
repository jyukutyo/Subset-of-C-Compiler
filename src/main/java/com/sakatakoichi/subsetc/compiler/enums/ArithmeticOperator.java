package com.sakatakoichi.subsetc.compiler.enums;

/**
 * Four arithmetic operator.
 */
public enum ArithmeticOperator {

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private String operator;

    ArithmeticOperator(String operator) {
        this.operator = operator;
    }

    public static ArithmeticOperator from(String operator) {
        for (ArithmeticOperator o : values()) {
            if (o.operator.equals(operator)) {
                return o;
            }
        }
        throw new RuntimeException();
    }
}
