package com.sakatakoichi.subsetc.compiler.constant;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * CONSTANT_Fieldref	9
 */
public class ConstantFieldRef extends AbstractConstant {

    protected int classIndex;

    protected int nameAndTypeIndex;

    public ConstantFieldRef(short index, int classIndex, int nameAndTypeIndex) {
        super(index, ConstantType.FIELDREF);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    protected void emitSpecializedCode(DataOutputStream outputStream) throws IOException {
        outputStream.writeShort(classIndex);
        outputStream.writeShort(nameAndTypeIndex);
    }
}
