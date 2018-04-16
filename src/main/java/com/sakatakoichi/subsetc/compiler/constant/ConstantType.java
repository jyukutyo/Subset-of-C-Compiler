package com.sakatakoichi.subsetc.compiler.constant;

public enum ConstantType {

    CLASS((byte) 7),
    FIELDREF((byte) 9),
    METHODREF((byte) 10),
    INTERFACEMETHODREF((byte) 11),
    STRING((byte) 8),
    INTEGER((byte) 3),
    FLOAT((byte) 4),
    LONG((byte) 5),
    DOUBLE((byte) 6),
    NAMEANDTYPE((byte) 12),
    UTF8((byte) 1),
    METHODHANDLE((byte) 15),
    METHODTYPE((byte) 16),
    INVOKEDYNAMIC((byte) 18);

    private byte value;

    ConstantType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
