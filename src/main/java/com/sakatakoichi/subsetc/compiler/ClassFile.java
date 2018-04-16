package com.sakatakoichi.subsetc.compiler;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sakatakoichi.subsetc.compiler.node.Block;
import com.sakatakoichi.subsetc.compiler.node.GlobalVariable;
import com.sakatakoichi.subsetc.compiler.node.LocalVariable;
import com.sakatakoichi.subsetc.compiler.enums.Type;

/**
 * Representing ClassFile.
 */
public class ClassFile {

    private final String name;

    private final ConstantPool constantPool;

    private final Map<String, Field> fields = new HashMap<>();

    private final Map<String, Method> methods = new HashMap<>();

    public ClassFile(String className) {
        this.name = className;
        this.constantPool = new ConstantPool(className);
    }

    /**
     * Add a field.
     * @param type type of field.
     * @param name name of field.
     */
    public void addField(Type type, String name) {

        if (fields.containsKey(name)) {
            throw new RuntimeException();
        }
        int nameIndex = constantPool.putUtf8(name);
        int descriptorIndex = constantPool.putUtf8(type.getDescriptor());
        int referenceIndex = constantPool.putFieldRef(nameIndex, descriptorIndex);
        Field f = new Field(type, nameIndex, descriptorIndex, referenceIndex);
        this.fields.put(name, f);
    }

    /**
     * Add a method.
     * @param returnType return type of method.
     * @param name name of method.
     * @param arguments argument variable list
     * @param block content of method
     */
    public void addMethod(Type returnType, String name, List<LocalVariable> arguments, Block block) {

        String argumentDescriptor = "(" +
                arguments.stream().map(a -> a.getType().getDescriptor()).collect(Collectors.joining()) +
                ")";

        if (methods.containsKey(argumentDescriptor)) {
            throw new RuntimeException();
        }
        String descriptor = argumentDescriptor + returnType.getDescriptor();
        int descriptorIndex = constantPool.putUtf8(descriptor);
        int nameIndex = constantPool.putUtf8(name);
        short referenceIndex = constantPool.putMethodRef(nameIndex, descriptorIndex);
        int attributeNameIndex = constantPool.putUtf8("Code");
        Method m = new Method(returnType, referenceIndex, nameIndex, descriptorIndex, attributeNameIndex, block);
        this.methods.put(name + argumentDescriptor, m);
    }

    /**
     * Add int to Constant Pool.
     * @param value target value
     * @return index of Constant Pool.
     */
    public int addInteger(int value) {
        return this.constantPool.putInteger(value);
    }

    /**
     * output ClassFile.
     * <pre>
     * ClassFile {
     * u4             magic;
     * u2             minor_version;
     * u2             major_version;
     * u2             constant_pool_count;
     * cp_info        constant_pool[constant_pool_count-1];
     * u2             access_flags;
     * u2             this_class;
     * u2             super_class;
     * u2             interfaces_count;
     * u2             interfaces[interfaces_count];
     * u2             fields_count;
     * field_info     fields[fields_count];
     * u2             methods_count;
     * method_info    methods[methods_count];
     * u2             attributes_count;
     * attribute_info attributes[attributes_count];
     * }
     * </pre>
     *
     * @throws Exception
     */
    public void emitCode() throws Exception {
        try (DataOutputStream classfile = new DataOutputStream(new FileOutputStream(this.name + ".class"))) {
            // magic
            classfile.writeInt(0xCAFEBABE);
            // minor_version 0
            classfile.writeShort(0x00);
            // major_version 52
            classfile.writeShort(0x34);
            // constant_pool_count & constant_pool[constant_pool_count-1]
            constantPool.emitCode(classfile);
            // access_flags
            classfile.writeShort(0x20);
            // this_class
            classfile.writeShort(constantPool.getClassIndex());
            // super_class
            classfile.writeShort(constantPool.getSuperClassIndex());
            // interfaces_count
            classfile.writeShort(0);
            // fields_count & fields[fields_count]
            emitCodeOfField(classfile);
            // methods_count & methods[methods_count]
            emitCodeOfMethod(classfile);
            // attributes_count
            classfile.writeShort(0);
        }
    }

    private void emitCodeOfField(DataOutputStream outputStream) throws IOException {
        int size = this.fields.size();
        if (size > 65535) {
            throw new RuntimeException("Too Big Classfile");
        }

        Collection<Field> values = this.fields.values();
        outputStream.writeShort(size);
        for (Field f : values) {
            f.emitCode(outputStream);
        }
    }

    private void emitCodeOfMethod(DataOutputStream outputStream) throws IOException {
        int size = this.methods.size();
        if (size > 65535) {
            throw new RuntimeException("Too Big Classfile");
        }

        Collection<Method> values = this.methods.values();
        outputStream.writeShort(size);
        for (Method m : values) {
            m.emitCode(outputStream);
        }
    }

    /**
     * Whether this ClassFile has the field.
     * @param name name of field.
     * @return true if this ClassFile has the field.
     */
    public boolean hasField(String name) {
        return this.fields.containsKey(name);
    }

    /**
     * Create field reference.
     * @param name name of field.
     * @return instance of global variable.
     */
    public GlobalVariable createFieldReference(String name) {
        if (!this.fields.containsKey(name)) {
            throw new RuntimeException();
        }
        return this.fields.get(name).createReference();
    }

    /**
     * Return the method.
     * @param identifier method identifier.
     * @return instance of method.
     */
    public Method getMethod(String identifier) {
        if (!this.methods.containsKey(identifier)) {
            throw new RuntimeException();
        }
        return this.methods.get(identifier);
    }
}
