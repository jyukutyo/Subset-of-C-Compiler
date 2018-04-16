package com.sakatakoichi.subsetc.compiler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sakatakoichi.subsetc.grammer.SubsetCLexer;
import com.sakatakoichi.subsetc.grammer.SubsetCParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Compiler for my own language.
 */
public class Compiler {

    /**
     * Main method for compiling code.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if (args == null) {
            return;
        }

        List<String> files = Arrays.asList(args).stream()
                .filter(f -> f.endsWith(".jyu"))
                .filter(f -> Files.exists(Paths.get(f)))
                .collect(Collectors.toList());

        if (files.isEmpty()) {
            System.out.println("no sc file.");
            return;
        }

        Compiler compiler = new Compiler();
        List<ClassFile> classFiles = compiler.compile(files);
        compiler.emitCode(classFiles);
    }

    List<ClassFile> compile(List<String> files) throws IOException {
        List<ClassFile> classFiles = new ArrayList<>();
        for (String file : files) {
            byte[] byteArray = Files.readAllBytes(Paths.get(file));
            CharStream charStream = CodePointCharStream.fromBuffer(CodePointBuffer.withBytes(ByteBuffer.wrap(byteArray)));

            SubsetCLexer lexer = new SubsetCLexer(charStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);

            SubsetCParser parser = new SubsetCParser(tokenStream);
            SubsetCParser.ProgramContext programContext = parser.program();

            ParseTreeWalker walker = new ParseTreeWalker();

            SubsetCParseTreeListener listener = new SubsetCParseTreeListener(file.replace(".jyu", ""));
            walker.walk(listener, programContext);
            classFiles.add(listener.getClassFile());
        }
        return classFiles;
    }

    void emitCode(List<ClassFile> classFiles) throws Exception {
        for (ClassFile classFile : classFiles) {
            classFile.emitCode();
        }
    }

}
