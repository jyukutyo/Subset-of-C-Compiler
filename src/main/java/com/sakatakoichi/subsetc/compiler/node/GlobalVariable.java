package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import static com.sakatakoichi.subsetc.compiler.Bytecode.GETSTATIC;
import static com.sakatakoichi.subsetc.compiler.Bytecode.PUTSTATIC;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public class GlobalVariable extends Variable {

    public GlobalVariable(Type type, int referenceIndex) {
        super(type, referenceIndex);
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer, boolean isLoad) throws IOException {
        buffer.writeByte(isLoad ? GETSTATIC : PUTSTATIC);
        buffer.writeShort((short) this.index);
    }
}
