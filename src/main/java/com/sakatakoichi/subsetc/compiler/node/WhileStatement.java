package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import static com.sakatakoichi.subsetc.compiler.Bytecode.GOTO;
import static com.sakatakoichi.subsetc.compiler.Bytecode.GOTO_W;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPEQ;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPGE;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPGT;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPLE;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPLT;
import static com.sakatakoichi.subsetc.compiler.Bytecode.IF_ICMPNE;
import com.sakatakoichi.subsetc.compiler.enums.ComparisonOperator;
import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;

public class WhileStatement extends ConditionStatement {

    public WhileStatement(ComparisonOperator operator, Expression left, Expression right, Statement pop) {
        super(operator, left, right, pop);
    }

    @Override
    public void emitCode(MethodBytecodeBuffer buffer) throws IOException {

        int pc = buffer.commit(3);
        this.statement.emitCode(buffer);

        int offset = buffer.currentBytecodeIndex() - pc;
        if (offset <= Short.MAX_VALUE) {
            buffer.patchUnsignedByte(pc, GOTO);
            buffer.patchShort(pc + 1, (short) offset);
            pc = pc + 3;
        } else {
            buffer.insert(pc, 2);
            buffer.patchUnsignedByte(pc, GOTO_W);
            buffer.patchInt(pc + 1, offset);
            pc = pc + 5;
        }

        left.emitRightSideCode(buffer, 0);
        right.emitRightSideCode(buffer, 1);
        offset = pc - buffer.currentBytecodeIndex();

        switch (this.operator) {
            case EQUAL:
                buffer.writeByte(IF_ICMPEQ);
                break;
            case NOT_EQUAL:
                buffer.writeByte(IF_ICMPNE);
                break;
            case GREATER:
                buffer.writeByte(IF_ICMPGT);
                break;
            case GREATER_OR_EQUAL:
                buffer.writeByte(IF_ICMPGE);
                break;
            case LESS:
                buffer.writeByte(IF_ICMPLT);
                break;
            case LESS_OR_EQUAL:
                buffer.writeByte(IF_ICMPLE);
                break;
            default:
                throw new RuntimeException();
        }

        if (offset >= Short.MIN_VALUE) {
            buffer.writeShort((short) offset);
        } else {
            buffer.writeShort((short) 8);
            buffer.writeByte(GOTO_W);
            buffer.writeInt(offset - 3);
        }
    }
}
