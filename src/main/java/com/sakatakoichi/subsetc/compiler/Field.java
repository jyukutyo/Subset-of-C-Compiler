package com.sakatakoichi.subsetc.compiler;

import java.io.DataOutputStream;
import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.node.GlobalVariable;
import com.sakatakoichi.subsetc.compiler.enums.Type;

/**
 * Representing field for my own language.
 */
public class Field {
    private Type type;

    private int nameIndex;

    private int descriptorIndex;

    private int referenceIndex;

    public Field(Type type, int nameIndex, int descriptorIndex, int referenceIndex) {
        this.type = type;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.referenceIndex = referenceIndex;
    }

    /**
     * Write bytecode to output stream.
     * @param outputStream target output stream.
     * @throws IOException
     */
    public void emitCode(DataOutputStream outputStream) throws IOException {
        // ACC_STATIC | ACC_PRIVATE (0x02 | 0x08 = 0x0A)
        final int accessFlags = 0x0A;
        outputStream.writeShort(accessFlags);
        outputStream.writeShort(nameIndex);
        outputStream.writeShort(descriptorIndex);
        final int attributesCount = 0;
        outputStream.writeShort(attributesCount);
    }

    public GlobalVariable createReference() {
        return new GlobalVariable(type, referenceIndex);
    }
}
