package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public class BlockStatement extends Statement {

    private Block block;

    public BlockStatement(Block block) {
        this.block = block;
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {
        this.block.emitCode(buffer);
    }
}
