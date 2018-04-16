package com.sakatakoichi.subsetc.compiler.node;

import com.sakatakoichi.subsetc.compiler.enums.ComparisonOperator;

public abstract class ConditionStatement extends Statement {

    protected final ComparisonOperator operator;

    protected final Expression left;

    protected final Expression right;

    protected final Statement statement;

    public ConditionStatement(ComparisonOperator operator, Expression left, Expression right, Statement statement) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.statement = statement;
    }


}
