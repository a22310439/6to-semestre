%%

"SetUnion"|"ClearSet"|"PrintSet"|"Showsets"|"Delete"|"Union"|"intersection"|"Sets"|"Set" {
    printf("|CMD, %s|", yytext);
}

":="              { printf("|ASSIGN, %s|", yytext); }
"{"             { printf("|LBRACE, %s|", yytext); }
"}"             { printf("|RBRACE, %s|", yytext); }
","               { printf("|COMMA, %s|", yytext); }
";"               { printf("|SEMICOLON, %s|", yytext); }

[A-Za-z][A-Za-z0-9]* {
    printf("|ID, %s|", yytext);
}

[0-9]+[A-Za-z0-9]* {
    printf("|ELEMENT, %s|", yytext);
}

[ \t]+            { /* nada */ }

\n                { putchar('\n'); }

.                 { /* nada */ }

%%

int main(void) {
    printf(">> Scanner iniciado. Escribe los comandos y pulsa Enter.\n");
    yylex();
    return 0;
}
