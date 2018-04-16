// Generated from SubsetC.g4 by ANTLR 4.7
package com.sakatakoichi.subsetc.grammer;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SubsetCParser}.
 */
public interface SubsetCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SubsetCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SubsetCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#globalVariable}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVariable(SubsetCParser.GlobalVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#globalVariable}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVariable(SubsetCParser.GlobalVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(SubsetCParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(SubsetCParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(SubsetCParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(SubsetCParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SubsetCParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SubsetCParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code localVariableStatement}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableStatement(SubsetCParser.LocalVariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code localVariableStatement}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableStatement(SubsetCParser.LocalVariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(SubsetCParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(SubsetCParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallStatement}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallStatement(SubsetCParser.FunctionCallStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallStatement}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallStatement(SubsetCParser.FunctionCallStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStatementDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatementDef(SubsetCParser.IfStatementDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStatementDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatementDef(SubsetCParser.IfStatementDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatementDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatementDef(SubsetCParser.WhileStatementDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatementDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatementDef(SubsetCParser.WhileStatementDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockDef(SubsetCParser.BlockDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockDef(SubsetCParser.BlockDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStatementDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatementDef(SubsetCParser.ReturnStatementDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStatementDef}
	 * labeled alternative in {@link SubsetCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatementDef(SubsetCParser.ReturnStatementDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SubsetCParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SubsetCParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(SubsetCParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(SubsetCParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(SubsetCParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(SubsetCParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(SubsetCParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(SubsetCParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SubsetCParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SubsetCParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(SubsetCParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(SubsetCParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(SubsetCParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(SubsetCParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(SubsetCParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(SubsetCParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(SubsetCParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(SubsetCParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(SubsetCParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(SubsetCParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(SubsetCParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(SubsetCParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubsetCParser#variableType}.
	 * @param ctx the parse tree
	 */
	void enterVariableType(SubsetCParser.VariableTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubsetCParser#variableType}.
	 * @param ctx the parse tree
	 */
	void exitVariableType(SubsetCParser.VariableTypeContext ctx);
}