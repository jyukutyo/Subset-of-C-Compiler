package com.sakatakoichi.subsetc.compiler;

/**
 * Java bytecode.
 */
public class Bytecode {
    public static final byte ICONST_M_1 = 0x02;
    public static final byte ICONST_0 = 0x03;
    public static final byte ICONST_1 = 0x04;
    public static final byte ICONST_2 = 0x05;
    public static final byte ICONST_3 = 0x06;
    public static final byte ICONST_4 = 0x07;
    public static final byte ICONST_5 = 0x08;
    public static final byte BIPUSH = 0x10;
    public static final byte SIPUSH = 0x11;
    public static final byte LDC = 0x12;
    public static final byte LDC_W = 0x13;
    public static final byte ILOAD = 0x15;
    public static final byte ILOAD_0 = 0x1a;
    public static final byte ILOAD_1 = 0x1b;
    public static final byte ILOAD_2 = 0x1c;
    public static final byte ILOAD_3 = 0x1d;
    public static final byte ALOAD_0 = 0x2a;
    public static final byte ISTORE = 0x36;
    public static final byte ISTORE_0 = 0x3b;
    public static final byte ISTORE_1 = 0x3c;
    public static final byte ISTORE_2 = 0x3d;
    public static final byte ISTORE_3 = 0x3e;
    public static final byte POP = 0x57;
    public static final byte IADD = 0x60;
    public static final byte ISUB = 0x64;
    public static final byte IMUL = 0x68;
    public static final byte IDIV = 0x6c;
    public static final byte INEG = 0x74;
    public static final byte IF_ICMPEQ = (byte) 0x9f;
    public static final byte IF_ICMPNE = (byte) 0xa0;
    public static final byte IF_ICMPLT = (byte) 0xa1;
    public static final byte IF_ICMPGE = (byte) 0xa2;
    public static final byte IF_ICMPGT = (byte) 0xa3;
    public static final byte IF_ICMPLE = (byte) 0xa4;
    public static final byte GOTO = (byte) 0xa7;
    public static final byte IRETURN = (byte) 0xac;
    public static final byte RETURN = (byte) 0xb1;
    public static final byte GETSTATIC = (byte) 0xb2;
    public static final byte PUTSTATIC = (byte) 0xb3;
    public static final byte INVOKESPECIAL = (byte) 0xb7;
    public static final byte INVOKESTATIC = (byte) 0xb8;
    public static final byte WIDE = (byte) 0xc4;
    public static final byte GOTO_W = (byte) 0xc8;
}
