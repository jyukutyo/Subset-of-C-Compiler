package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public abstract class Statement {

    public abstract void emitCode(MethodBytecodeBuffer buffer) throws IOException;

}
