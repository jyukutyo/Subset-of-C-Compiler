package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.Bytecode;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public class ReturnStatement extends Statement {

    private Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public ReturnStatement() {
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {
        if (expression == null) {
            buffer.writeByte(Bytecode.RETURN);
        } else if (expression.emitRightSideCode(buffer, 0) == Type.INT) {
            buffer.writeByte(Bytecode.IRETURN);
        } else {
            throw new RuntimeException();
        }
    }
}
