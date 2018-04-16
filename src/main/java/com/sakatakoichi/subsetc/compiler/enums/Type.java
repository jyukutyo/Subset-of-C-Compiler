package com.sakatakoichi.subsetc.compiler.enums;

/**
 * Types allowed in code.
 */
public enum Type {

    INT("I"),
    DOUBLE(""),
    VOID("V");

    private final String descriptor;

    Type(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}
