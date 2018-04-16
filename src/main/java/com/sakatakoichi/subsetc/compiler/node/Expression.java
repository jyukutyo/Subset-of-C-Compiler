package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public abstract class Expression {

    public abstract Type emitRightSideCode(MethodBytecodeBuffer buffer, int stackPointer) throws IOException;

}
