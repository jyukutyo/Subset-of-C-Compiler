package com.sakatakoichi.subsetc.compiler.node;

import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.MethodBytecodeBuffer;
import com.sakatakoichi.subsetc.compiler.enums.Type;

public abstract class Variable extends Expression {

    protected Type type;

    protected int index;

    public Variable(Type type, int index) {
        this.type = type;
        this.index = index;
    }

    public Type getType() {
        return type;
    }

    public abstract void emitCode(MethodBytecodeBuffer buffer, boolean isLoad) throws IOException;


    @Override
    public Type emitRightSideCode(MethodBytecodeBuffer buffer, int stackPointer) throws IOException {
        switch (this.type) {
            case INT:
                buffer.setMaxStack(stackPointer + 1);
                break;
            default:
                throw new RuntimeException();
        }
        emitCode(buffer, true);
        return this.type;
    }

    public Type emitLeftSideCode(MethodBytecodeBuffer buffer) throws IOException {
        emitCode(buffer, false);
        return this.type;
    }

}
