package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public class AssignmentStatement extends Statement {

    private final Variable variable;

    private final Expression expression;

    public AssignmentStatement(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {
        if (expression.emitRightSideCode(buffer, 0) != variable.emitLeftSideCode(buffer)) {
            throw new RuntimeException();
        }
    }
}
