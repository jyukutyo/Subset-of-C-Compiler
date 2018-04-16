package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;
import java.util.List;

import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public class Block {

    private final List<Statement> statements;

    private int localVariableCount;

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {

        boolean hasReturn = this.statements.stream().anyMatch(s -> s instanceof ReturnStatement);
        boolean isReturnLast = this.statements.stream()
                .filter(s -> s instanceof ReturnStatement)
                .allMatch(s -> this.statements.indexOf(s) == this.statements.size() - 1);

        if (hasReturn && !isReturnLast) {
            throw new RuntimeException();
        }

        buffer.setMaxLocals(this.localVariableCount);
        for (Statement s : this.statements) {
            s.emitCode(buffer);
        }
    }

    public void setLocalVariableCount(int count) {
        this.localVariableCount = count;
    }
}
