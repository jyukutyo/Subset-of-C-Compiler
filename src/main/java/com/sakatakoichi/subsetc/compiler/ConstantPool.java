package com.sakatakoichi.subsetc.compiler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sakatakoichi.subsetc.compiler.constant.Constant;
import com.sakatakoichi.subsetc.compiler.constant.ConstantClass;
import com.sakatakoichi.subsetc.compiler.constant.ConstantFieldRef;
import com.sakatakoichi.subsetc.compiler.constant.ConstantInteger;
import com.sakatakoichi.subsetc.compiler.constant.ConstantMethodRef;
import com.sakatakoichi.subsetc.compiler.constant.ConstantNameAndType;
import com.sakatakoichi.subsetc.compiler.constant.ConstantUtf8;

/**
 * Representing Constant Pool.
 */
public class ConstantPool {

    private Map<ConstantKey, Constant> constants;
    private final int classIndex;
    private final int superClassIndex;

    public ConstantPool(String className) {
        this.constants = new HashMap<>();
        this.classIndex = putClass(className);
        this.superClassIndex = putClass("java/lang/Object");
    }

    /**
     * Write bytecode to output stream.
     * @param outputStream target output stream.
     * @throws IOException
     */
    public void emitCode(DataOutputStream outputStream) throws IOException {
        int entrySize = constants.size();

        int limit = 65535;
        if (limit < entrySize) {
            throw new RuntimeException("can't have constants over 65535");
        }

        outputStream.writeShort(entrySize + 1);
        List<Constant> constants = new ArrayList<>(this.constants.values());
        constants.sort((a, b) -> a.getIndex() <= b.getIndex() ? -1 : 1);
        for (Constant constant : constants) {
            constant.emitCode(outputStream);
        }
    }

    public short putUtf8(String str) {
        return putToConstants(new ConstantUtf8Key(str), new ConstantUtf8((short) (constants.size() + 1), str));
    }

    protected short putNameAndType(int nameIndex, int descriptorIndex) {
        return putToConstants(new ConstantNameAndTypeKey(nameIndex, descriptorIndex), new ConstantNameAndType((short) (constants.size() + 1), nameIndex, descriptorIndex));
    }

    public short putFieldRef(int nameIndex, int descriptorIndex) {
        int nameAndTypeIndex = putNameAndType(nameIndex, descriptorIndex);
        return putToConstants(new ConstantFieldRefKey(classIndex, nameAndTypeIndex), new ConstantFieldRef((short) (constants.size() + 1), classIndex, nameAndTypeIndex));
    }

    public short putMethodRef(int nameIndex, int descriptorIndex) {
        int nameAndTypeIndex = putNameAndType(nameIndex, descriptorIndex);
        return putToConstants(new ConstantMethodRefKey(classIndex, nameAndTypeIndex), new ConstantMethodRef((short) (constants.size() + 1), classIndex, nameAndTypeIndex));
    }

    public short putInteger(int integer) {
        return putToConstants(new ConstantIntegerKey(integer), new ConstantInteger((short) (constants.size() + 1), integer));
    }

    public short putClass(String name) {
        int nameIndex = putUtf8(name);
        return putToConstants(new ConstantClassKey(nameIndex), new ConstantClass((short) (constants.size() + 1), nameIndex));
    }

    private short putToConstants(ConstantKey key, Constant c) {
        constants.putIfAbsent(key, c);
        return constants.get(key).getIndex();
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getSuperClassIndex() {
        return superClassIndex;
    }

    private interface ConstantKey {
    }

    private static class ConstantUtf8Key implements ConstantKey {
        private String bytes;

        public ConstantUtf8Key(String bytes) {
            this.bytes = bytes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConstantUtf8Key)) return false;
            ConstantUtf8Key that = (ConstantUtf8Key) o;
            return Objects.equals(bytes, that.bytes);
        }

        @Override
        public int hashCode() {

            return Objects.hash(bytes);
        }
    }

    private static class ConstantNameAndTypeKey implements ConstantKey {
        private int nameIndex;
        private int descriptorIndex;

        public ConstantNameAndTypeKey(int nameIndex, int descriptorIndex) {
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConstantNameAndTypeKey)) return false;
            ConstantNameAndTypeKey that = (ConstantNameAndTypeKey) o;
            return nameIndex == that.nameIndex &&
                    descriptorIndex == that.descriptorIndex;
        }

        @Override
        public int hashCode() {

            return Objects.hash(nameIndex, descriptorIndex);
        }
    }

    private static class ConstantFieldRefKey implements ConstantKey {
        private int classIndex;
        private int nameAndTypeIndex;

        public ConstantFieldRefKey(int classIndex, int nameAndTypeIndex) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConstantFieldRefKey)) return false;
            ConstantFieldRefKey that = (ConstantFieldRefKey) o;
            return classIndex == that.classIndex &&
                    nameAndTypeIndex == that.nameAndTypeIndex;
        }

        @Override
        public int hashCode() {

            return Objects.hash(classIndex, nameAndTypeIndex);
        }
    }

    private static class ConstantMethodRefKey implements ConstantKey {
        private int classIndex;
        private int nameAndTypeIndex;

        public ConstantMethodRefKey(int classIndex, int nameAndTypeIndex) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConstantMethodRefKey)) return false;
            ConstantMethodRefKey that = (ConstantMethodRefKey) o;
            return classIndex == that.classIndex &&
                    nameAndTypeIndex == that.nameAndTypeIndex;
        }

        @Override
        public int hashCode() {

            return Objects.hash(classIndex, nameAndTypeIndex);
        }
    }

    private static class ConstantIntegerKey implements ConstantKey {
        private final int bytes;

        ConstantIntegerKey(int bytes) {
            this.bytes = bytes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConstantIntegerKey)) return false;
            ConstantIntegerKey that = (ConstantIntegerKey) o;
            return bytes == that.bytes;
        }

        @Override
        public int hashCode() {

            return Objects.hash(bytes);
        }
    }

    private static class ConstantClassKey implements ConstantKey {

        private final int nameIndex;

        ConstantClassKey(int nameIndex) {
            this.nameIndex = nameIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConstantClassKey)) return false;
            ConstantClassKey that = (ConstantClassKey) o;
            return nameIndex == that.nameIndex;
        }

        @Override
        public int hashCode() {
            return Objects.hash(nameIndex);
        }
    }
}
