package com.sakatakoichi.subsetc.compiler.constant;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * CONSTANT_Class	7
 */
public class ConstantClass extends AbstractConstant {

    protected int nameIndex;

    public ConstantClass(short index, int nameIndex) {
        super(index, ConstantType.CLASS);
        this.nameIndex = nameIndex;
    }

    @Override
    protected void emitSpecializedCode(DataOutputStream outputStream) throws IOException {
        outputStream.writeShort(nameIndex);
    }
}
