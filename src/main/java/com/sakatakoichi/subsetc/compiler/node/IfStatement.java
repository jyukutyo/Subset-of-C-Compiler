package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import static com.sakatakoichi.subsetc.compiler.Bytecode.GOTO_W;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPEQ;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPGE;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPGT;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPLE;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPLT;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPNE;
import com.sakatakoichi.subsetc.compiler.enums.ComparisonOperator;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public class IfStatement extends ConditionStatement {

    public IfStatement(ComparisonOperator operator, Expression left, Expression right, Statement statement) {
        super(operator, left, right, statement);
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {
        left.emitRightSideCode(buffer, 0);
        right.emitRightSideCode(buffer, 1);
        int pc = buffer.commit(3);
        statement.emitCode(buffer);

        int offset = buffer.currentBytecodeIndex() - pc;
        if (offset <= Short.MAX_VALUE) {
            switch (this.operator) {
                case EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPNE);
                    break;
                case NOT_EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPEQ);
                    break;
                case GREATER:
                    buffer.patchUnsignedByte(pc, IF_ICMPLE);
                    break;
                case GREATER_OR_EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPLT);
                    break;
                case LESS:
                    buffer.patchUnsignedByte(pc, IF_ICMPGE);
                    break;
                case LESS_OR_EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPGT);
                    break;
                default:
                    throw new RuntimeException();
            }
            buffer.patchShort(pc + 1, (short) offset);
        } else {
            buffer.insert(pc, 5);
            offset = offset + 5 - 3;
            switch (this.operator) {
                case EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPEQ);
                    break;
                case NOT_EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPNE);
                    break;
                case GREATER:
                    buffer.patchUnsignedByte(pc, IF_ICMPGT);
                    break;
                case GREATER_OR_EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPGE);
                    break;
                case LESS:
                    buffer.patchUnsignedByte(pc, IF_ICMPLT);
                    break;
                case LESS_OR_EQUAL:
                    buffer.patchUnsignedByte(pc, IF_ICMPLE);
                    break;
                default:
                    throw new RuntimeException();
            }
            buffer.patchShort(pc + 1, (short) 8);
            buffer.patchUnsignedByte(pc + 3, GOTO_W);
            buffer.patchInt(pc + 4, offset);
        }
    }
}
