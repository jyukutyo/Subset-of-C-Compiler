package com.sakatakoichi.subsetc.compiler.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractConstant implements Constant {

    protected short index;

    protected ConstantType tag;

    protected AbstractConstant(short index, ConstantType tag) {
        this.index = index;
        this.tag = tag;
    }

    @Override
    public void emitCode(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(this.tag.getValue());
        this.emitSpecializedCode(outputStream);
    }

    @Override
    public short getIndex() {
        return this.index;
    }

    protected abstract void emitSpecializedCode(DataOutputStream outputStream) throws IOException;
}
