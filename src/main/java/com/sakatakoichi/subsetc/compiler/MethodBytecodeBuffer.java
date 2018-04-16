package com.sakatakoichi.subsetc.compiler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Bytes;

/**
 * Code buffer for each method.
 */
public class MethodBytecodeBuffer {

    private final DataOutputStream classfile;

    private int attributeNameIndex;

    private List<Byte> bytecodes;

    private int maxStack;

    private int maxLocals;

    public MethodBytecodeBuffer(int attributeNameIndex, DataOutputStream outputStream) {
        this.attributeNameIndex = attributeNameIndex;
        this.bytecodes = new ArrayList<>();
        this.classfile = outputStream;
    }

    public void flush() throws IOException {
        classfile.writeShort(attributeNameIndex);
        // attribute_length
        int bytecodeCount = bytecodes.size();
        classfile.writeInt(bytecodeCount + 12);
        classfile.writeShort(maxStack);
        classfile.writeShort(maxLocals);
        classfile.writeInt(bytecodeCount);
        classfile.write(Bytes.toArray(bytecodes), 0, bytecodeCount);
        // exceptions_table_length
        classfile.writeShort(0);
        // attributes_count
        classfile.writeShort(0);
    }

    public void setMaxStack(int maxStack) {
        if (maxStack > this.maxStack && maxStack > 65535) {
            throw new RuntimeException("Too Deep Operand Stack");
        }
        this.maxStack = maxStack;
    }

    public void setMaxLocals(int maxLocals) {
        if (maxLocals > this.maxLocals && maxLocals > 65535) {
            throw new RuntimeException("Too Many Local Variables");
        }
        this.maxLocals = maxLocals;
    }

    public int currentBytecodeIndex() {
        return bytecodes.size() - 1;
    }

    private void expand(int size) {
        for (int i = 0; i < size; i++) {
            bytecodes.add((byte) 0);
        }
    }

    private void reserve(int offset) {
        if (offset < bytecodes.size()) {
            return;
        }
        expand(offset + 1 - bytecodes.size());
    }

    public void insert(int offset, int size) {
        expand(size);
        for (int i = 0; i < offset + size; i++) {
            bytecodes.set(offset + i, (byte) 0);
        }
    }

    public int commit(int size) {
        int result = bytecodes.size() - 1;
        expand(size);
        return result;
    }

    public void patchUnsignedByte(int offset, byte data) {
        reserve(offset);
        bytecodes.set(offset, data);
    }

    public void patchShort(int offset, short data) {
        reserve(offset + 1);
        byte[] bytes = ByteBuffer.allocate(2).putShort(data).array();
        bytecodes.set(offset, bytes[0]);
        bytecodes.set(offset + 1, bytes[1]);
    }

    public void patchInt(int offset, int data) {
        reserve(offset + 3);
        byte[] bytes = ByteBuffer.allocate(4).putInt(data).array();
        bytecodes.set(offset, bytes[0]);
        bytecodes.set(offset + 1, bytes[1]);
        bytecodes.set(offset + 2, bytes[2]);
        bytecodes.set(offset + 3, bytes[3]);
    }

    public void writeByte(byte data) {
        bytecodes.add(data);
    }

    public void writeShort(short data) {
        byte[] bytes = ByteBuffer.allocate(2).putShort(data).array();
        bytecodes.addAll(Bytes.asList(bytes));
    }

    public void writeInt(int data) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(data).array();
        bytecodes.addAll(Bytes.asList(bytes));
    }
}
