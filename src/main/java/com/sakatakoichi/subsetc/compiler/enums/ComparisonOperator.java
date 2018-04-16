package com.sakatakoichi.subsetc.compiler.enums;

/**
 * Comparison operator for condition statement.
 */
public enum ComparisonOperator {

    GREATER(">"),
    LESS("<"),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!=");

    private String operator;

    ComparisonOperator(String operator) {
        this.operator = operator;
    }

    public static ComparisonOperator from(String operator) {
        for (ComparisonOperator o : values()) {
            if (o.operator.equals(operator)) {
                return o;
            }
        }
        throw new RuntimeException();
    }


}
