package com.sakatakoichi.subsetc.compiler.node;

import static com.sakatakoichi.subsetc.compiler.Bytecode.BIPUSH;
import static com.sakatakoichi.subsetc.compiler.Bytecode.ICONST_0;
import static com.sakatakoichi.subsetc.compiler.Bytecode.LDC;
import static com.sakatakoichi.subsetc.compiler.Bytecode.LDC_W;
import static com.sakatakoichi.subsetc.compiler.Bytecode.SIPUSH;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public class ConstantInt extends Expression {

    private int index;

    private final int value;

    public ConstantInt(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public ConstantInt(int value) {
        this.value = value;
    }

    @Override
    public Type emitRightSideCode(MethodBytecodeBuffer buffer, int stackPointer) {
        buffer.setMaxStack(stackPointer + 1);

        if (-1 <= value && value <= 5) {
            buffer.writeByte((byte) (ICONST_0 + value));
        } else if (Byte.MIN_VALUE <= value && value <= Byte.MAX_VALUE) {
            buffer.writeByte(BIPUSH);
            buffer.writeByte((byte) value);
        } else if (Short.MIN_VALUE <= value && value <= Short.MAX_VALUE) {
            buffer.writeByte(SIPUSH);
            buffer.writeShort((short) value);
        } else if (index < 256) {
            buffer.writeByte(LDC);
            buffer.writeByte((byte) index);
        } else {
            buffer.writeByte(LDC_W);
            buffer.writeShort((short) index);
        }
        return Type.INT;
    }
}
