package com.sakatakoichi.subsetc.compiler.enums;

/**
 * Sing for numbers.
 */
public enum Sign {

    PLUS("+"),
    MINUS("-");

    private String sign;

    Sign(String sign) {
        this.sign = sign;
    }

    public static Sign from(String sign) {
        if ("+".equals(sign)) {
            return PLUS;
        }
        if ("-".equals(sign)) {
            return MINUS;
        }
        throw new RuntimeException();
    }
}
