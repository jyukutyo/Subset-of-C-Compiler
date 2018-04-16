package com.sakatakoichi.subsetc.compiler;

import java.io.DataOutputStream;
import java.io.IOException;

import com.sakatakoichi.subsetc.compiler.node.Block;
import com.sakatakoichi.subsetc.compiler.enums.Type;

/**
 * Representing method for my own language.
 */
public class Method {

    private final Type returnType;
    private final short referenceIndex;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int attributeNameIndex;
    private Block block;

    public Method(Type returnType, short referenceIndex, int nameIndex, int descriptorIndex, int attributeNameIndex, Block block) {
        this.returnType = returnType;
        this.referenceIndex = referenceIndex;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributeNameIndex = attributeNameIndex;
        this.block = block;
    }

    /**
     * Write bytecode to output stream.
     * @param outputStream target output stream.
     * @throws IOException
     */
    public void emitCode(DataOutputStream outputStream) throws IOException {
        // ACC_STATIC | ACC_PUBLIC (0x01 | 0x08 = 0x09)
        outputStream.writeShort(9);
        outputStream.writeShort(nameIndex);
        outputStream.writeShort(descriptorIndex);
        // attributes_countはメソッド本体1つのみ -> 1
        outputStream.writeShort(1);

        MethodBytecodeBuffer buffer = new MethodBytecodeBuffer(attributeNameIndex, outputStream);
        this.block.emitCode(buffer);
        if (returnType == Type.VOID) {
            buffer.writeByte(Bytecode.RETURN);
        }

        buffer.flush();
    }

    public Type getReturnType() {
        return returnType;
    }

    public short getReferenceIndex() {
        return referenceIndex;
    }
}
