package com.sakatakoichi.subsetc.compiler.node;

import static com.sakatakoichi.subsetc.compiler.Bytecode.ILOAD;
import static com.sakatakoichi.subsetc.compiler.Bytecode.ILOAD_0;
import static com.sakatakoichi.subsetc.compiler.Bytecode.ISTORE;
import static com.sakatakoichi.subsetc.compiler.Bytecode.WIDE;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public class LocalVariable extends Variable {

    public LocalVariable(Type type, int index) {
        super(type, index);
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer, boolean isLoad) {
        int codeOffset;
        switch (this.type) {
            case INT:
                codeOffset = isLoad ? 0 : ISTORE - ILOAD;
                break;
            default:
                throw new RuntimeException();
        }

        if (index <= 3) {
            int shortOpcodeBase = ILOAD_0 + codeOffset;
            buffer.writeByte((byte) (shortOpcodeBase + index));
            return;
        }

        int longOpcode = ILOAD + codeOffset;
        if (index <= 255) {
            buffer.writeByte((byte) longOpcode);
            buffer.writeByte((byte) index);
        } else {
            buffer.writeByte(WIDE);
            buffer.writeByte((byte) longOpcode);
            buffer.writeShort((short) index);
        }
    }


}
