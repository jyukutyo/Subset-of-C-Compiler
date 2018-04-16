package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;


import static com.sakatakoichi.subsetc.compiler.Bytecode.POP;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public class CallStatement extends Statement {

    private Expression expression;

    public CallStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {
        switch (expression.emitRightSideCode(buffer, 0)) {
            case INT:
                buffer.writeByte(POP);
        }
    }
}
