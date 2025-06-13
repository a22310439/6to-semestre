
/*  A Bison parser, made from parser.y
 by  GNU Bison version 1.27
  */

#define YYBISON 1  /* Identify Bison output.  */

#define	SETUNION	257
#define	CLEARSET	258
#define	PRINTSET	259
#define	SHOWSETS	260
#define	DELETESET	261
#define	UNIONSET	262
#define	INTERSECTION	263
#define	SETS	264
#define	SET	265
#define	INVERT	266
#define	ASSIGN	267
#define	LBRACE	268
#define	RBRACE	269
#define	COMMA	270
#define	SEMICOLON	271
#define	EXITCMD	272
#define	ID	273
#define	ELEMENT	274

#line 1 "parser.y"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* ---- Estructura de datos para conjuntos ---- */
#define MAXSETS 1000
#define INITIAL_CAPACITY 4

typedef struct {
    char *name;
    char **elements;
    int    count;
    int    capacity;
} Set;

/* Arreglo global de conjuntos y contador */
static Set sets[MAXSETS];
static int setCount = 0;

/* Puntero al conjunto que estamos definiendo en la regla SET … */
static Set *currentSet = NULL;

/* ---- Funciones ---- */
static Set *findSet(const char *name) {
    for(int i = 0; i < setCount; i++)
        if (strcmp(sets[i].name, name) == 0)
            return &sets[i];
    return NULL;
}

static Set *createOrGetSet(const char *name) {
    Set *s = findSet(name);
    if (s) {
        /* Si ya existía, lo limpiamos para redefinirlo */
        for(int i = 0; i < s->count; i++) free(s->elements[i]);
        s->count = 0;
        return s;
    }
    if (setCount >= MAXSETS) {
        fprintf(stderr, "Error: número máximo de conjuntos alcanzado\n");
        exit(1);
    }
    s = &sets[setCount++];
    s->name     = strdup(name);
    s->capacity = INITIAL_CAPACITY;
    s->elements = malloc(s->capacity * sizeof(char*));
    s->count    = 0;
    return s;
}

/* agregar un elemento a un conjunto */
static void addElement(Set *s, const char *elem) {
    if (!s) return;
    /* evitamos duplicados */
    for(int i = 0; i < s->count; i++)
        if (strcmp(s->elements[i], elem) == 0)
            return;
    if (s->count >= s->capacity) {
        s->capacity *= 2;
        s->elements = realloc(s->elements, s->capacity * sizeof(char*));
    }
    s->elements[s->count++] = strdup(elem);
}

/* eliminar un conjunto */
static void deleteSetFunc(const char *name) {
    for(int i = 0; i < setCount; i++) {
        if (strcmp(sets[i].name, name) == 0) {
            free(sets[i].name);
            for(int j=0;j<sets[i].count;j++) free(sets[i].elements[j]);
            free(sets[i].elements);
            /* compactamos el array */
            for(int k=i;k<setCount-1;k++) sets[k]=sets[k+1];
            setCount--;
            return;
        }
    }
    fprintf(stderr, "Warning: conjunto '%s' no existe (DeleteSet)\n", name);
}

/* eliminar los elementos de un conjunto */
static void clearSetFunc(const char *name) {
    Set *s = findSet(name);
    if (s) {
        for(int i=0;i<s->count;i++) free(s->elements[i]);
        s->count = 0;
    } else {
        fprintf(stderr, "Warning: conjunto '%s' no existe (ClearSet)\n", name);
    }
}

/* imprimir un conjunto */
static void printSetFunc(const char *name) {
    Set *s = findSet(name);
    if (!s) {
        fprintf(stderr, "Error: conjunto '%s' no existe (PrintSet)\n", name);
        return;
    }
    printf("%s = { ", name);
    for(int i=0;i<s->count;i++) {
        printf("%s%s", s->elements[i], (i+1<s->count)?", ":" ");
    }
    printf("}\n");
}

/* imprimir todos los conjuntos */
static void showAllSets() {
    if (setCount == 0) {
        printf("No hay conjuntos definidos.\n");
        return;
    }
    for(int i=0;i<setCount;i++) {
        printSetFunc(sets[i].name);
    }
}

/* union de conjunutos */
static void unionSetsFunc(const char *n1, const char *n2) {
    Set *a = findSet(n1), *b = findSet(n2);
    if (!a || !b) {
        fprintf(stderr, "Error: conjuntos para unión no encontrados\n");
        return;
    }
    printf("Union(%s,%s) = { ", n1, n2);
    /* imprimimos todos de 'a' y luego los de 'b' que no estén en 'a' */
    int printed = 0;
    for(int i=0;i<a->count;i++) {
        if (printed++) printf(", ");
        printf("%s", a->elements[i]);
    }
    for(int j=0;j<b->count;j++) {
        int found = 0;
        for(int i=0;i<a->count;i++)
            if (strcmp(a->elements[i], b->elements[j])==0) { found=1; break; }
        if (!found) {
            if (printed++) printf(", ");
            printf("%s", b->elements[j]);
        }
    }
    printf(" }\n");
}

/* interseccion de conjuntos */
static void intersectSetsFunc(const char *n1, const char *n2) {
    Set *a = findSet(n1), *b = findSet(n2);
    if (!a || !b) {
        fprintf(stderr, "Error: conjuntos para intersección no encontrados\n");
        return;
    }
    printf("Intersection(%s,%s) = { ", n1, n2);
    int printed = 0;
    for(int i=0; i<a->count; i++){
        for(int j=0; j<b->count; j++){
            if (strcmp(a->elements[i], b->elements[j])==0){
                if (printed++) printf(", ");
                printf("%s", a->elements[i]);
                break;
            }
        }
    }
    printf(" }\n");
}

/* --- invertir un conjunto --- */
static void invertSetFunc(const char *name) {
    Set *s = findSet(name);
    if (!s) {
        fprintf(stderr, "Error: conjunto '%s' no existe (Invert)\n", name);
        return;
    }
    for (int i = 0; i < s->count/2; i++) {
        char *tmp = s->elements[i];
        s->elements[i] = s->elements[s->count - 1 - i];
        s->elements[s->count - 1 - i] = tmp;
    }
    printf(">> Conjunto '%s' invertido.\n", name);
}

/* Prototipos del parser */
extern int yylex(void);
extern int yylineno;
void yyerror(const char *s) {
    fprintf(stderr, "** yyerror (linea %d): %s\n", yylineno, s);
}

#line 194 "parser.y"
typedef union {
    char *str;
} YYSTYPE;
#include <stdio.h>

#ifndef __cplusplus
#ifndef __STDC__
#define const
#endif
#endif



#define	YYFINAL		37
#define	YYFLAG		-32768
#define	YYNTBASE	21

#define YYTRANSLATE(x) ((unsigned)(x) <= 274 ? yytranslate[x] : 26)

static const char yytranslate[] = {     0,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     1,     3,     4,     5,     6,
     7,     8,     9,    10,    11,    12,    13,    14,    15,    16,
    17,    18,    19,    20
};

#if YYDEBUG != 0
static const short yyprhs[] = {     0,
     0,     1,     4,     7,    10,    13,    14,    22,    25,    27,
    30,    33,    38,    43,    46,    48
};

static const short yyrhs[] = {    -1,
    21,    22,     0,    23,    17,     0,    18,    17,     0,     1,
    17,     0,     0,    11,    19,    13,    14,    24,    25,    15,
     0,     5,    19,     0,     6,     0,     4,    19,     0,     7,
    19,     0,     3,    19,    16,    19,     0,     9,    19,    16,
    19,     0,    12,    19,     0,    20,     0,    25,    16,    20,
     0
};

#endif

#if YYDEBUG != 0
static const short yyrline[] = { 0,
   203,   205,   208,   210,   212,   220,   226,   230,   235,   239,
   244,   249,   254,   259,   266,   272
};
#endif


#if YYDEBUG != 0 || defined (YYERROR_VERBOSE)

static const char * const yytname[] = {   "$","error","$undefined.","SETUNION",
"CLEARSET","PRINTSET","SHOWSETS","DELETESET","UNIONSET","INTERSECTION","SETS",
"SET","INVERT","ASSIGN","LBRACE","RBRACE","COMMA","SEMICOLON","EXITCMD","ID",
"ELEMENT","program","statement","instruction","@1","element_list", NULL
};
#endif

static const short yyr1[] = {     0,
    21,    21,    22,    22,    22,    24,    23,    23,    23,    23,
    23,    23,    23,    23,    25,    25
};

static const short yyr2[] = {     0,
     0,     2,     2,     2,     2,     0,     7,     2,     1,     2,
     2,     4,     4,     2,     1,     3
};

static const short yydefact[] = {     1,
     0,     0,     0,     0,     0,     9,     0,     0,     0,     0,
     0,     2,     0,     5,     0,    10,     8,    11,     0,     0,
    14,     4,     3,     0,     0,     0,    12,    13,     6,     0,
    15,     0,     7,     0,    16,     0,     0
};

static const short yydefgoto[] = {     1,
    12,    13,    30,    32
};

static const short yypact[] = {-32768,
     0,   -15,   -11,    -9,    -4,-32768,    -3,     1,     2,     3,
     6,-32768,     7,-32768,     9,-32768,-32768,-32768,    10,     4,
-32768,-32768,-32768,     8,    11,     5,-32768,-32768,-32768,    12,
-32768,    -2,-32768,    13,-32768,    28,-32768
};

static const short yypgoto[] = {-32768,
-32768,-32768,-32768,-32768
};


#define	YYLAST		33


static const short yytable[] = {    36,
     2,    14,     3,     4,     5,     6,     7,    15,     8,    16,
     9,    10,    33,    34,    17,    18,    26,    11,    29,    19,
    20,    21,    22,    23,    24,    25,    27,    37,     0,    28,
     0,    31,    35
};

static const short yycheck[] = {     0,
     1,    17,     3,     4,     5,     6,     7,    19,     9,    19,
    11,    12,    15,    16,    19,    19,    13,    18,    14,    19,
    19,    19,    17,    17,    16,    16,    19,     0,    -1,    19,
    -1,    20,    20
};
/* -*-C-*-  Note some compilers choke on comments on `#line' lines.  */
#line 3 "bison.simple"
/* This file comes from bison-1.27.  */

/* Skeleton output parser for bison,
   Copyright (C) 1984, 1989, 1990 Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place - Suite 330,
   Boston, MA 02111-1307, USA.  */

/* As a special exception, when this file is copied by Bison into a
   Bison output file, you may use that output file without restriction.
   This special exception was added by the Free Software Foundation
   in version 1.24 of Bison.  */

/* This is the parser code that is written into each bison parser
  when the %semantic_parser declaration is not specified in the grammar.
  It was written by Richard Stallman by simplifying the hairy parser
  used when %semantic_parser is specified.  */

#ifndef YYSTACK_USE_ALLOCA
#ifdef alloca
#define YYSTACK_USE_ALLOCA
#else /* alloca not defined */
#ifdef __GNUC__
#define YYSTACK_USE_ALLOCA
#define alloca __builtin_alloca
#else /* not GNU C.  */
#if (!defined (__STDC__) && defined (sparc)) || defined (__sparc__) || defined (__sparc) || defined (__sgi) || (defined (__sun) && defined (__i386))
#define YYSTACK_USE_ALLOCA
#include <alloca.h>
#else /* not sparc */
/* We think this test detects Watcom and Microsoft C.  */
/* This used to test MSDOS, but that is a bad idea
   since that symbol is in the user namespace.  */
#if (defined (_MSDOS) || defined (_MSDOS_)) && !defined (__TURBOC__)
#if 0 /* No need for malloc.h, which pollutes the namespace;
	 instead, just don't use alloca.  */
#include <malloc.h>
#endif
#else /* not MSDOS, or __TURBOC__ */
#if defined(_AIX)
/* I don't know what this was needed for, but it pollutes the namespace.
   So I turned it off.   rms, 2 May 1997.  */
/* #include <malloc.h>  */
 #pragma alloca
#define YYSTACK_USE_ALLOCA
#else /* not MSDOS, or __TURBOC__, or _AIX */
#if 0
#ifdef __hpux /* haible@ilog.fr says this works for HPUX 9.05 and up,
		 and on HPUX 10.  Eventually we can turn this on.  */
#define YYSTACK_USE_ALLOCA
#define alloca __builtin_alloca
#endif /* __hpux */
#endif
#endif /* not _AIX */
#endif /* not MSDOS, or __TURBOC__ */
#endif /* not sparc */
#endif /* not GNU C */
#endif /* alloca not defined */
#endif /* YYSTACK_USE_ALLOCA not defined */

#ifdef YYSTACK_USE_ALLOCA
#define YYSTACK_ALLOC alloca
#else
#define YYSTACK_ALLOC malloc
#endif

/* Note: there must be only one dollar sign in this file.
   It is replaced by the list of actions, each action
   as one case of the switch.  */

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		-2
#define YYEOF		0
#define YYACCEPT	goto yyacceptlab
#define YYABORT 	goto yyabortlab
#define YYERROR		goto yyerrlab1
/* Like YYERROR except do call yyerror.
   This remains here temporarily to ease the
   transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */
#define YYFAIL		goto yyerrlab
#define YYRECOVERING()  (!!yyerrstatus)
#define YYBACKUP(token, value) \
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    { yychar = (token), yylval = (value);			\
      yychar1 = YYTRANSLATE (yychar);				\
      YYPOPSTACK;						\
      goto yybackup;						\
    }								\
  else								\
    { yyerror ("syntax error: cannot back up"); YYERROR; }	\
while (0)

#define YYTERROR	1
#define YYERRCODE	256

#ifndef YYPURE
#define YYLEX		yylex()
#endif

#ifdef YYPURE
#ifdef YYLSP_NEEDED
#ifdef YYLEX_PARAM
#define YYLEX		yylex(&yylval, &yylloc, YYLEX_PARAM)
#else
#define YYLEX		yylex(&yylval, &yylloc)
#endif
#else /* not YYLSP_NEEDED */
#ifdef YYLEX_PARAM
#define YYLEX		yylex(&yylval, YYLEX_PARAM)
#else
#define YYLEX		yylex(&yylval)
#endif
#endif /* not YYLSP_NEEDED */
#endif

/* If nonreentrant, generate the variables here */

#ifndef YYPURE

int	yychar;			/*  the lookahead symbol		*/
YYSTYPE	yylval;			/*  the semantic value of the		*/
				/*  lookahead symbol			*/

#ifdef YYLSP_NEEDED
YYLTYPE yylloc;			/*  location data for the lookahead	*/
				/*  symbol				*/
#endif

int yynerrs;			/*  number of parse errors so far       */
#endif  /* not YYPURE */

#if YYDEBUG != 0
int yydebug;			/*  nonzero means print parse trace	*/
/* Since this is uninitialized, it does not stop multiple parsers
   from coexisting.  */
#endif

/*  YYINITDEPTH indicates the initial size of the parser's stacks	*/

#ifndef	YYINITDEPTH
#define YYINITDEPTH 200
#endif

/*  YYMAXDEPTH is the maximum size the stacks can grow to
    (effective only if the built-in stack extension method is used).  */

#if YYMAXDEPTH == 0
#undef YYMAXDEPTH
#endif

#ifndef YYMAXDEPTH
#define YYMAXDEPTH 10000
#endif

/* Define __yy_memcpy.  Note that the size argument
   should be passed with type unsigned int, because that is what the non-GCC
   definitions require.  With GCC, __builtin_memcpy takes an arg
   of type size_t, but it can handle unsigned int.  */

#if __GNUC__ > 1		/* GNU C and GNU C++ define this.  */
#define __yy_memcpy(TO,FROM,COUNT)	__builtin_memcpy(TO,FROM,COUNT)
#else				/* not GNU C or C++ */
#ifndef __cplusplus

/* This is the most reliable way to avoid incompatibilities
   in available built-in functions on various systems.  */
static void
__yy_memcpy (to, from, count)
     char *to;
     char *from;
     unsigned int count;
{
  register char *f = from;
  register char *t = to;
  register int i = count;

  while (i-- > 0)
    *t++ = *f++;
}

#else /* __cplusplus */

/* This is the most reliable way to avoid incompatibilities
   in available built-in functions on various systems.  */
static void
__yy_memcpy (char *to, char *from, unsigned int count)
{
  register char *t = to;
  register char *f = from;
  register int i = count;

  while (i-- > 0)
    *t++ = *f++;
}

#endif
#endif

#line 216 "bison.simple"

/* The user can define YYPARSE_PARAM as the name of an argument to be passed
   into yyparse.  The argument should have type void *.
   It should actually point to an object.
   Grammar actions can access the variable by casting it
   to the proper pointer type.  */

#ifdef YYPARSE_PARAM
#ifdef __cplusplus
#define YYPARSE_PARAM_ARG void *YYPARSE_PARAM
#define YYPARSE_PARAM_DECL
#else /* not __cplusplus */
#define YYPARSE_PARAM_ARG YYPARSE_PARAM
#define YYPARSE_PARAM_DECL void *YYPARSE_PARAM;
#endif /* not __cplusplus */
#else /* not YYPARSE_PARAM */
#define YYPARSE_PARAM_ARG
#define YYPARSE_PARAM_DECL
#endif /* not YYPARSE_PARAM */

/* Prevent warning if -Wstrict-prototypes.  */
#ifdef __GNUC__
#ifdef YYPARSE_PARAM
int yyparse (void *);
#else
int yyparse (void);
#endif
#endif

int
yyparse(YYPARSE_PARAM_ARG)
     YYPARSE_PARAM_DECL
{
  register int yystate;
  register int yyn;
  register short *yyssp;
  register YYSTYPE *yyvsp;
  int yyerrstatus;	/*  number of tokens to shift before error messages enabled */
  int yychar1 = 0;		/*  lookahead token as an internal (translated) token number */

  short	yyssa[YYINITDEPTH];	/*  the state stack			*/
  YYSTYPE yyvsa[YYINITDEPTH];	/*  the semantic value stack		*/

  short *yyss = yyssa;		/*  refer to the stacks thru separate pointers */
  YYSTYPE *yyvs = yyvsa;	/*  to allow yyoverflow to reallocate them elsewhere */

#ifdef YYLSP_NEEDED
  YYLTYPE yylsa[YYINITDEPTH];	/*  the location stack			*/
  YYLTYPE *yyls = yylsa;
  YYLTYPE *yylsp;

#define YYPOPSTACK   (yyvsp--, yyssp--, yylsp--)
#else
#define YYPOPSTACK   (yyvsp--, yyssp--)
#endif

  int yystacksize = YYINITDEPTH;
  int yyfree_stacks = 0;

#ifdef YYPURE
  int yychar;
  YYSTYPE yylval;
  int yynerrs;
#ifdef YYLSP_NEEDED
  YYLTYPE yylloc;
#endif
#endif

  YYSTYPE yyval;		/*  the variable used to return		*/
				/*  semantic values from the action	*/
				/*  routines				*/

  int yylen;

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Starting parse\n");
#endif

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY;		/* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */

  yyssp = yyss - 1;
  yyvsp = yyvs;
#ifdef YYLSP_NEEDED
  yylsp = yyls;
#endif

/* Push a new state, which is found in  yystate  .  */
/* In all cases, when you get here, the value and location stacks
   have just been pushed. so pushing a state here evens the stacks.  */
yynewstate:

  *++yyssp = yystate;

  if (yyssp >= yyss + yystacksize - 1)
    {
      /* Give user a chance to reallocate the stack */
      /* Use copies of these so that the &'s don't force the real ones into memory. */
      YYSTYPE *yyvs1 = yyvs;
      short *yyss1 = yyss;
#ifdef YYLSP_NEEDED
      YYLTYPE *yyls1 = yyls;
#endif

      /* Get the current used size of the three stacks, in elements.  */
      int size = yyssp - yyss + 1;

#ifdef yyoverflow
      /* Each stack pointer address is followed by the size of
	 the data in use in that stack, in bytes.  */
#ifdef YYLSP_NEEDED
      /* This used to be a conditional around just the two extra args,
	 but that might be undefined if yyoverflow is a macro.  */
      yyoverflow("parser stack overflow",
		 &yyss1, size * sizeof (*yyssp),
		 &yyvs1, size * sizeof (*yyvsp),
		 &yyls1, size * sizeof (*yylsp),
		 &yystacksize);
#else
      yyoverflow("parser stack overflow",
		 &yyss1, size * sizeof (*yyssp),
		 &yyvs1, size * sizeof (*yyvsp),
		 &yystacksize);
#endif

      yyss = yyss1; yyvs = yyvs1;
#ifdef YYLSP_NEEDED
      yyls = yyls1;
#endif
#else /* no yyoverflow */
      /* Extend the stack our own way.  */
      if (yystacksize >= YYMAXDEPTH)
	{
	  yyerror("parser stack overflow");
	  if (yyfree_stacks)
	    {
	      free (yyss);
	      free (yyvs);
#ifdef YYLSP_NEEDED
	      free (yyls);
#endif
	    }
	  return 2;
	}
      yystacksize *= 2;
      if (yystacksize > YYMAXDEPTH)
	yystacksize = YYMAXDEPTH;
#ifndef YYSTACK_USE_ALLOCA
      yyfree_stacks = 1;
#endif
      yyss = (short *) YYSTACK_ALLOC (yystacksize * sizeof (*yyssp));
      __yy_memcpy ((char *)yyss, (char *)yyss1,
		   size * (unsigned int) sizeof (*yyssp));
      yyvs = (YYSTYPE *) YYSTACK_ALLOC (yystacksize * sizeof (*yyvsp));
      __yy_memcpy ((char *)yyvs, (char *)yyvs1,
		   size * (unsigned int) sizeof (*yyvsp));
#ifdef YYLSP_NEEDED
      yyls = (YYLTYPE *) YYSTACK_ALLOC (yystacksize * sizeof (*yylsp));
      __yy_memcpy ((char *)yyls, (char *)yyls1,
		   size * (unsigned int) sizeof (*yylsp));
#endif
#endif /* no yyoverflow */

      yyssp = yyss + size - 1;
      yyvsp = yyvs + size - 1;
#ifdef YYLSP_NEEDED
      yylsp = yyls + size - 1;
#endif

#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Stack size increased to %d\n", yystacksize);
#endif

      if (yyssp >= yyss + yystacksize - 1)
	YYABORT;
    }

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Entering state %d\n", yystate);
#endif

  goto yybackup;
 yybackup:

/* Do appropriate processing given the current state.  */
/* Read a lookahead token if we need one and don't already have one.  */
/* yyresume: */

  /* First try to decide what to do without reference to lookahead token.  */

  yyn = yypact[yystate];
  if (yyn == YYFLAG)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* yychar is either YYEMPTY or YYEOF
     or a valid token in external form.  */

  if (yychar == YYEMPTY)
    {
#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Reading a token: ");
#endif
      yychar = YYLEX;
    }

  /* Convert token to internal form (in yychar1) for indexing tables with */

  if (yychar <= 0)		/* This means end of input. */
    {
      yychar1 = 0;
      yychar = YYEOF;		/* Don't call YYLEX any more */

#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Now at end of input.\n");
#endif
    }
  else
    {
      yychar1 = YYTRANSLATE(yychar);

#if YYDEBUG != 0
      if (yydebug)
	{
	  fprintf (stderr, "Next token is %d (%s", yychar, yytname[yychar1]);
	  /* Give the individual parser a way to print the precise meaning
	     of a token, for further debugging info.  */
#ifdef YYPRINT
	  YYPRINT (stderr, yychar, yylval);
#endif
	  fprintf (stderr, ")\n");
	}
#endif
    }

  yyn += yychar1;
  if (yyn < 0 || yyn > YYLAST || yycheck[yyn] != yychar1)
    goto yydefault;

  yyn = yytable[yyn];

  /* yyn is what to do for this token type in this state.
     Negative => reduce, -yyn is rule number.
     Positive => shift, yyn is new state.
       New state is final state => don't bother to shift,
       just return success.
     0, or most negative number => error.  */

  if (yyn < 0)
    {
      if (yyn == YYFLAG)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }
  else if (yyn == 0)
    goto yyerrlab;

  if (yyn == YYFINAL)
    YYACCEPT;

  /* Shift the lookahead token.  */

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Shifting token %d (%s), ", yychar, yytname[yychar1]);
#endif

  /* Discard the token being shifted unless it is eof.  */
  if (yychar != YYEOF)
    yychar = YYEMPTY;

  *++yyvsp = yylval;
#ifdef YYLSP_NEEDED
  *++yylsp = yylloc;
#endif

  /* count tokens shifted since error; after three, turn off error status.  */
  if (yyerrstatus) yyerrstatus--;

  yystate = yyn;
  goto yynewstate;

/* Do the default action for the current state.  */
yydefault:

  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;

/* Do a reduction.  yyn is the number of a rule to reduce with.  */
yyreduce:
  yylen = yyr2[yyn];
  if (yylen > 0)
    yyval = yyvsp[1-yylen]; /* implement default value of the action */

#if YYDEBUG != 0
  if (yydebug)
    {
      int i;

      fprintf (stderr, "Reducing via rule %d (line %d), ",
	       yyn, yyrline[yyn]);

      /* Print the symbols being reduced, and their result.  */
      for (i = yyprhs[yyn]; yyrhs[i] > 0; i++)
	fprintf (stderr, "%s ", yytname[yyrhs[i]]);
      fprintf (stderr, " -> %s\n", yytname[yyr1[yyn]]);
    }
#endif


  switch (yyn) {

case 4:
#line 211 "parser.y"
{ exit(0); ;
    break;}
case 5:
#line 213 "parser.y"
{
            yyerrok;
            fprintf(stderr, "** Error de sintaxis (línea %d). Ignoramos hasta ';'.\n", yylineno);
        ;
    break;}
case 6:
#line 222 "parser.y"
{
            currentSet = createOrGetSet(yyvsp[-2].str);
            free(yyvsp[-2].str);
        ;
    break;}
case 7:
#line 227 "parser.y"
{
            currentSet = NULL;
        ;
    break;}
case 8:
#line 231 "parser.y"
{
            printSetFunc(yyvsp[0].str);
            free(yyvsp[0].str);
        ;
    break;}
case 9:
#line 236 "parser.y"
{
            showAllSets();
        ;
    break;}
case 10:
#line 240 "parser.y"
{
            clearSetFunc(yyvsp[0].str);
            free(yyvsp[0].str);
        ;
    break;}
case 11:
#line 245 "parser.y"
{
            deleteSetFunc(yyvsp[0].str);
            free(yyvsp[0].str);
        ;
    break;}
case 12:
#line 250 "parser.y"
{
            unionSetsFunc(yyvsp[-2].str, yyvsp[0].str);
            free(yyvsp[-2].str); free(yyvsp[0].str);
        ;
    break;}
case 13:
#line 255 "parser.y"
{
            intersectSetsFunc(yyvsp[-2].str, yyvsp[0].str);
            free(yyvsp[-2].str); free(yyvsp[0].str);
        ;
    break;}
case 14:
#line 260 "parser.y"
{
            invertSetFunc(yyvsp[0].str);
            free(yyvsp[0].str);
        ;
    break;}
case 15:
#line 268 "parser.y"
{
            addElement(currentSet, yyvsp[0].str);
            free(yyvsp[0].str);
        ;
    break;}
case 16:
#line 273 "parser.y"
{
            addElement(currentSet, yyvsp[0].str);
            free(yyvsp[0].str);
        ;
    break;}
}
   /* the action file gets copied in in place of this dollarsign */
#line 542 "bison.simple"

  yyvsp -= yylen;
  yyssp -= yylen;
#ifdef YYLSP_NEEDED
  yylsp -= yylen;
#endif

#if YYDEBUG != 0
  if (yydebug)
    {
      short *ssp1 = yyss - 1;
      fprintf (stderr, "state stack now");
      while (ssp1 != yyssp)
	fprintf (stderr, " %d", *++ssp1);
      fprintf (stderr, "\n");
    }
#endif

  *++yyvsp = yyval;

#ifdef YYLSP_NEEDED
  yylsp++;
  if (yylen == 0)
    {
      yylsp->first_line = yylloc.first_line;
      yylsp->first_column = yylloc.first_column;
      yylsp->last_line = (yylsp-1)->last_line;
      yylsp->last_column = (yylsp-1)->last_column;
      yylsp->text = 0;
    }
  else
    {
      yylsp->last_line = (yylsp+yylen-1)->last_line;
      yylsp->last_column = (yylsp+yylen-1)->last_column;
    }
#endif

  /* Now "shift" the result of the reduction.
     Determine what state that goes to,
     based on the state we popped back to
     and the rule number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTBASE] + *yyssp;
  if (yystate >= 0 && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTBASE];

  goto yynewstate;

yyerrlab:   /* here on detecting error */

  if (! yyerrstatus)
    /* If not already recovering from an error, report this error.  */
    {
      ++yynerrs;

#ifdef YYERROR_VERBOSE
      yyn = yypact[yystate];

      if (yyn > YYFLAG && yyn < YYLAST)
	{
	  int size = 0;
	  char *msg;
	  int x, count;

	  count = 0;
	  /* Start X at -yyn if nec to avoid negative indexes in yycheck.  */
	  for (x = (yyn < 0 ? -yyn : 0);
	       x < (sizeof(yytname) / sizeof(char *)); x++)
	    if (yycheck[x + yyn] == x)
	      size += strlen(yytname[x]) + 15, count++;
	  msg = (char *) malloc(size + 15);
	  if (msg != 0)
	    {
	      strcpy(msg, "parse error");

	      if (count < 5)
		{
		  count = 0;
		  for (x = (yyn < 0 ? -yyn : 0);
		       x < (sizeof(yytname) / sizeof(char *)); x++)
		    if (yycheck[x + yyn] == x)
		      {
			strcat(msg, count == 0 ? ", expecting `" : " or `");
			strcat(msg, yytname[x]);
			strcat(msg, "'");
			count++;
		      }
		}
	      yyerror(msg);
	      free(msg);
	    }
	  else
	    yyerror ("parse error; also virtual memory exceeded");
	}
      else
#endif /* YYERROR_VERBOSE */
	yyerror("parse error");
    }

  goto yyerrlab1;
yyerrlab1:   /* here on error raised explicitly by an action */

  if (yyerrstatus == 3)
    {
      /* if just tried and failed to reuse lookahead token after an error, discard it.  */

      /* return failure if at end of input */
      if (yychar == YYEOF)
	YYABORT;

#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Discarding token %d (%s).\n", yychar, yytname[yychar1]);
#endif

      yychar = YYEMPTY;
    }

  /* Else will try to reuse lookahead token
     after shifting the error token.  */

  yyerrstatus = 3;		/* Each real token shifted decrements this */

  goto yyerrhandle;

yyerrdefault:  /* current state does not do anything special for the error token. */

#if 0
  /* This is wrong; only states that explicitly want error tokens
     should shift them.  */
  yyn = yydefact[yystate];  /* If its default is to accept any token, ok.  Otherwise pop it.*/
  if (yyn) goto yydefault;
#endif

yyerrpop:   /* pop the current state because it cannot handle the error token */

  if (yyssp == yyss) YYABORT;
  yyvsp--;
  yystate = *--yyssp;
#ifdef YYLSP_NEEDED
  yylsp--;
#endif

#if YYDEBUG != 0
  if (yydebug)
    {
      short *ssp1 = yyss - 1;
      fprintf (stderr, "Error: state stack now");
      while (ssp1 != yyssp)
	fprintf (stderr, " %d", *++ssp1);
      fprintf (stderr, "\n");
    }
#endif

yyerrhandle:

  yyn = yypact[yystate];
  if (yyn == YYFLAG)
    goto yyerrdefault;

  yyn += YYTERROR;
  if (yyn < 0 || yyn > YYLAST || yycheck[yyn] != YYTERROR)
    goto yyerrdefault;

  yyn = yytable[yyn];
  if (yyn < 0)
    {
      if (yyn == YYFLAG)
	goto yyerrpop;
      yyn = -yyn;
      goto yyreduce;
    }
  else if (yyn == 0)
    goto yyerrpop;

  if (yyn == YYFINAL)
    YYACCEPT;

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Shifting error token, ");
#endif

  *++yyvsp = yylval;
#ifdef YYLSP_NEEDED
  *++yylsp = yylloc;
#endif

  yystate = yyn;
  goto yynewstate;

 yyacceptlab:
  /* YYACCEPT comes here.  */
  if (yyfree_stacks)
    {
      free (yyss);
      free (yyvs);
#ifdef YYLSP_NEEDED
      free (yyls);
#endif
    }
  return 0;

 yyabortlab:
  /* YYABORT comes here.  */
  if (yyfree_stacks)
    {
      free (yyss);
      free (yyvs);
#ifdef YYLSP_NEEDED
      free (yyls);
#endif
    }
  return 1;
}
#line 279 "parser.y"


int main(void) {
    printf(">> Analizador iniciado. Termina instrucciones con ';', 'Exit;' para salir.\n\n");
    yylineno = 1;
    yyparse();
    return 0;
}
