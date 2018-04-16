package com.sakatakoichi.subsetc.compiler.constant;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * CONSTANT_Integer	3
 */
public class ConstantInteger extends AbstractConstant {

    protected int bytes;

    public ConstantInteger(short index, int bytes) {
        super(index, ConstantType.INTEGER);
        this.bytes = bytes;
    }

    @Override
    protected void emitSpecializedCode(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(this.bytes);
    }
}
