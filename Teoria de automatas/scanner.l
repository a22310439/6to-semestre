%{
#include "parser.tab.h"
#include <string.h>
%}

%option yylineno noyywrap

%%

"SetUnion"        { return SETUNION; }
"ClearSet"        { return CLEARSET; }
"PrintSet"        { return PRINTSET; }
"Showsets"        { return SHOWSETS; }
"Delete"          { return DELETESET; }
"Union"           { return UNIONSET; }
"intersection"    { return INTERSECTION; }
"Sets"            { return SETS; }
"Set"             { return SET; }
"Invert"          { return INVERT; }
"Exit"            { return EXITCMD; }

":="              { return ASSIGN; }
"{"               { return LBRACE; }
"}"               { return RBRACE; }
","               { return COMMA; }
";"               { return SEMICOLON; }

[A-Za-z][A-Za-z0-9]* {
    yylval.str = strdup(yytext);
    return ID;
}

[0-9]+[A-Za-z0-9]* {
    yylval.str = strdup(yytext);
    return ELEMENT;
}

[ \t]+  { /* nada */ }

\n      { /* nada */ }

.       { /* nada */ }

%%

