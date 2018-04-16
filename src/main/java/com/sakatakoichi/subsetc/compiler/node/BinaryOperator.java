package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import static com.sakatakoichi.subsetc.compiler.Bytecode.IADD;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IDIV;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IMUL;
import static com.sakatakoichi.subsetc.compiler.Bytecode.ISUB;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.ArithmeticOperator;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public class BinaryOperator extends Expression {

    private final ArithmeticOperator operator;

    private final Expression left;

    private final Expression right;

    public BinaryOperator(ArithmeticOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Type emitRightSideCode(MethodBytecodeBuffer buffer, int stackPointer) throws IOException {
        int opCode;
        switch (operator) {
            case DIVIDE:
                opCode = IDIV;
                break;
            case MINUS:
                opCode = ISUB;
                break;
            case MULTIPLY:
                opCode = IMUL;
                break;
            case PLUS:
                opCode = IADD;
                break;
            default:
                throw new RuntimeException();
        }

        Type type = left.emitRightSideCode(buffer, stackPointer);
        if (right.emitRightSideCode(buffer, stackPointer + 1) != type) {
            throw new RuntimeException();
        }

        switch (type) {
            case INT:
                buffer.writeByte((byte) opCode);
                return type;
            default:
                throw new RuntimeException();
        }
    }
}
