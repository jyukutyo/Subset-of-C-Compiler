package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.Bytecode;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Sign;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public class UnaryOperator extends Expression {

    private final Sign sign;

    private final Expression expression;

    public UnaryOperator(Sign sign, Expression expression) {
        this.sign = sign;
        this.expression = expression;
    }


    @Override
    public Type emitRightSideCode(MethodBytecodeBuffer buffer, int stackPointer) throws IOException {
        Type t = expression.emitRightSideCode(buffer, stackPointer);
        switch (t) {
            case INT:
            case DOUBLE:
                break;
            default:
                throw new RuntimeException();
        }

        if (sign == Sign.MINUS) {
            buffer.writeByte(Bytecode.INEG);
        }
        return t;
    }
}
