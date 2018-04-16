package com.sakatakoichi.subsetc.compiler.constant;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * CONSTANT_NameAndType	12
 */
public class ConstantNameAndType extends AbstractConstant {

    protected int nameIndex;

    protected int descriptorIndex;

    public ConstantNameAndType(short index, int nameIndex, int descriptorIndex) {
        super(index, ConstantType.NAMEANDTYPE);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    protected void emitSpecializedCode(DataOutputStream outputStream) throws IOException {
        outputStream.writeShort(this.nameIndex);
        outputStream.writeShort(this.descriptorIndex);
    }
}
