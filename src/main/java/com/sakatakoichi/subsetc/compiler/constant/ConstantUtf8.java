package com.sakatakoichi.subsetc.compiler.constant;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * CONSTANT_Utf8	1
 */
public class ConstantUtf8 extends AbstractConstant {

    protected String bytes;

    public ConstantUtf8(short index, String bytes) {
        super(index, ConstantType.UTF8);
        this.bytes = bytes;
    }

    @Override
    protected void emitSpecializedCode(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(bytes);
    }
}
