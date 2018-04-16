package com.sakatakoichi.subsetc.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sakatakoichi.subsetc.compiler.node.AssignmentStatement;
import com.sakatakoichi.subsetc.compiler.node.BinaryOperator;
import com.sakatakoichi.subsetc.compiler.node.Block;
import com.sakatakoichi.subsetc.compiler.node.BlockStatement;
import com.sakatakoichi.subsetc.compiler.node.CallExpression;
import com.sakatakoichi.subsetc.compiler.node.CallStatement;
import com.sakatakoichi.subsetc.compiler.node.ConstantInt;
import com.sakatakoichi.subsetc.compiler.node.Expression;
import com.sakatakoichi.subsetc.compiler.node.IfStatement;
import com.sakatakoichi.subsetc.compiler.node.LocalVariable;
import com.sakatakoichi.subsetc.compiler.node.ReturnStatement;
import com.sakatakoichi.subsetc.compiler.node.Statement;
import com.sakatakoichi.subsetc.compiler.node.UnaryOperator;
import com.sakatakoichi.subsetc.compiler.node.Variable;
import com.sakatakoichi.subsetc.compiler.node.WhileStatement;
import com.sakatakoichi.subsetc.compiler.enums.ArithmeticOperator;
import com.sakatakoichi.subsetc.compiler.enums.ComparisonOperator;
import com.sakatakoichi.subsetc.compiler.enums.Sign;
import com.sakatakoichi.subsetc.compiler.enums.Type;
import com.sakatakoichi.subsetc.grammer.SubsetCBaseListener;
import com.sakatakoichi.subsetc.grammer.SubsetCParser;
import com.sakatakoichi.subsetc.grammer.SubsetCParser.VariableContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * ANTLR listener.
 */
public class SubsetCParseTreeListener extends SubsetCBaseListener {

    private final ClassFile classFile;

    private LinkedList<Expression> expressionStack = new LinkedList<>();

    private LinkedList<Statement> statementStack = new LinkedList<>();

    private Map<String, LocalVariable> localVariableMap = new HashMap<>();

    private LinkedList<Block> blockStack = new LinkedList<>();

    public SubsetCParseTreeListener(String className) {
        this.classFile = new ClassFile(className);
    }

    @Override
    public void exitGlobalVariable(SubsetCParser.GlobalVariableContext ctx) {
        VariableContext variableContext = ctx.variable();
        Type type = Type.valueOf(variableContext.variableType().getText().toUpperCase());
        String variableName = variableContext.name.getText();
        this.classFile.addField(type, variableName);
    }

    @Override
    public void enterFunction(SubsetCParser.FunctionContext ctx) {
        for (int i = 0; i < ctx.variable().size(); i++) {
            this.localVariableMap.put(ctx.variable(i).name.getText(), new LocalVariable(Type.valueOf(ctx.variable(i).variableType().getText().toUpperCase()), i));
        }
    }

    @Override
    public void exitFunction(SubsetCParser.FunctionContext ctx) {
        Type returnType = Type.valueOf(ctx.returnType().getText().toUpperCase());
        String functionName = ctx.name.getText();

        List<LocalVariable> arguments = new ArrayList<>();
        for (int i = 0; i < ctx.variable().size(); i++) {
            arguments.add(new LocalVariable(Type.valueOf(ctx.variable(i).variableType().getText().toUpperCase()), i));
        }

        this.classFile.addMethod(returnType, functionName, arguments, this.blockStack.pop());
    }

    @Override
    public void exitBlock(SubsetCParser.BlockContext ctx) {
        Block b = new Block(new ArrayList<>(this.statementStack));
        b.setLocalVariableCount(this.localVariableMap.size());
        this.blockStack.push(b);
        this.statementStack.clear();
        this.localVariableMap.clear();
    }

    @Override
    public void exitLocalVariableStatement(SubsetCParser.LocalVariableStatementContext ctx) {
        LocalVariable l = new LocalVariable(Type.valueOf(ctx.localVariable.variableType().getText().toUpperCase()), this.localVariableMap.size());
        this.localVariableMap.put(ctx.localVariable.name.getText(), l);
    }

    @Override
    public void exitAssignmentStatement(SubsetCParser.AssignmentStatementContext ctx) {
        super.exitAssignmentStatement(ctx);
    }

    @Override
    public void exitAssignment(SubsetCParser.AssignmentContext ctx) {
        Expression expression = expressionStack.pop();
        Variable variable;
        if (this.classFile.hasField(ctx.name.getText())) {
            variable = this.classFile.createFieldReference(ctx.name.getText());
        } else {
            variable = this.localVariableMap.get(ctx.name.getText());
        }
        this.statementStack.push(new AssignmentStatement(variable, expression));
    }

    @Override
    public void exitFunctionCall(SubsetCParser.FunctionCallContext ctx) {
        String functionName = ctx.name.getText();
        List<Expression> arguments = ctx.expression().stream()
                .map(c -> this.expressionStack.poll())
                .collect(Collectors.toList());
        this.expressionStack.push(new CallExpression(functionName, arguments, this.classFile));
    }

    @Override
    public void exitExpression(SubsetCParser.ExpressionContext ctx) {
        arithmeticOperation(ctx);
    }

    @Override
    public void exitTerm(SubsetCParser.TermContext ctx) {
        arithmeticOperation(ctx);
    }

    private void arithmeticOperation(ParserRuleContext ctx) {
        if (ctx.children.size() < 3) {
            return;
        }

        List<ParseTree> children = ctx.children;
        LinkedList<ParseTree> operators = IntStream.range(0, children.size())
                .filter(n -> n % 2 == 1)
                .mapToObj(children::get)
                .collect(Collectors.toCollection(LinkedList::new));

        Expression right = this.expressionStack.pop();
        while (!this.expressionStack.isEmpty()) {
            Expression left = this.expressionStack.pop();
            ArithmeticOperator arithmeticOperator = ArithmeticOperator.from(operators.pop().getText());
            right = new BinaryOperator(arithmeticOperator, left, right);
        }
        this.expressionStack.push(right);
    }

    @Override
    public void exitFactor(SubsetCParser.FactorContext ctx) {
        if (ctx.operator != null) {
            this.expressionStack.push(new UnaryOperator(Sign.from(ctx.operator.getText()), this.expressionStack.pop()));
        } else if (ctx.expression() != null) {
            // nop
        } else if (ctx.functionCall() != null) {
            // nop
        } else if (ctx.variableName != null) {
            Variable variable;
            if (this.classFile.hasField(ctx.variableName.getText())) {
                variable = this.classFile.createFieldReference(ctx.variableName.getText());
            } else {
                variable = this.localVariableMap.get(ctx.variableName.getText());
            }
            this.expressionStack.push(variable);
        } else if (ctx.constant != null) {
            int value = Integer.valueOf(ctx.constant.getText());
            if (value < Short.MIN_VALUE || Short.MAX_VALUE < value) {
                int index = this.classFile.addInteger(value);
                this.expressionStack.push(new ConstantInt(index, value));
            } else {
                this.expressionStack.push(new ConstantInt(value));
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void exitReturnStatement(SubsetCParser.ReturnStatementContext ctx) {
        if (this.expressionStack.isEmpty()) {
            this.statementStack.push(new ReturnStatement());
        } else {
            this.statementStack.push(new ReturnStatement(this.expressionStack.pop()));
        }
    }

    @Override
    public void exitIfStatement(SubsetCParser.IfStatementContext ctx) {
        ComparisonOperator operator = ComparisonOperator.from(ctx.condition().comparisonOperator().getText());
        Expression right = this.expressionStack.pop();
        Expression left = this.expressionStack.pop();
        this.statementStack.push(new IfStatement(operator, left, right, this.statementStack.pop()));
    }

    @Override
    public void exitWhileStatement(SubsetCParser.WhileStatementContext ctx) {
        ComparisonOperator operator = ComparisonOperator.from(ctx.condition().comparisonOperator().getText());
        Expression right = this.expressionStack.pop();
        Expression left = this.expressionStack.pop();
        this.statementStack.push(new WhileStatement(operator, left, right, this.statementStack.pop()));
    }

    @Override
    public void exitBlockDef(SubsetCParser.BlockDefContext ctx) {
        this.statementStack.push(new BlockStatement(this.blockStack.pop()));
    }

    @Override
    public void exitFunctionCallStatement(SubsetCParser.FunctionCallStatementContext ctx) {
        this.statementStack.push(new CallStatement(this.expressionStack.pop()));
    }

    public ClassFile getClassFile() {
        return classFile;
    }
}
