grammar SubsetC;

program
    : (globalVariable ';' | function)* EOF
    ;

globalVariable
    : variable
    ;

variable
    : variableType name=IDENTIFIER
    ;

function
    : returnType name=IDENTIFIER '(' variable? (',' variable)* ')' block
    ;

block
    : '{' statement* '}'
    ;

statement
    : localVariable=variable ';' #localVariableStatement
    | assignment #assignmentStatement
    | functionCall #functionCallStatement
    | ifStatement #ifStatementDef
    | whileStatement #whileStatementDef
    | block #blockDef
    | returnStatement #returnStatementDef
    ;

assignment
    : name=IDENTIFIER '=' expression ';'
    ;

ifStatement
    : IF '(' condition ')' statement
    ;

whileStatement
    : WHILE '(' condition ')' statement
    ;

returnStatement
    : RETURN expression? ';'
    ;

expression
    : term ((PLUS | MINUS) term)*
    ;

term
    : factor (('*' | '/') factor)*
    ;

factor
    : operator=(PLUS | MINUS) factor | '(' expression ')' | functionCall | variableName=IDENTIFIER | constant=NUMBER
    ;

functionCall
    : name=IDENTIFIER '(' expression? (',' expression)* ')' ';'
    ;

condition
    : left=expression comparisonOperator right=expression
    ;

comparisonOperator
    : EQAUL | NOTEQUAL | GREATER | GREATEROREQUAL | LESS | LESSOREQUAL
    ;

returnType
    : INT | VOID
    ;

variableType
    : INT
    ;

WHILE: 'while';
RETURN: 'return';
IF: 'if';
INT: 'int';
VOID: 'void';

PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
EXCLAMATION: '!';
GREATER: '>';
LESS: '<';
EQAUL: '==';
GREATEROREQUAL: '>=';
LESSOREQUAL: '<=';
NOTEQUAL: '!=';

IDENTIFIER
    : ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
    ;
NUMBER: [0-9]+;

WS: [ \t]+ -> skip;
NEWLINE: '\r'? '\n' -> skip;