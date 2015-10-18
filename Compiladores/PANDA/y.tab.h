
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     TK_IF = 258,
     TK_ELSE = 259,
     TK_FOR = 260,
     TK_DO = 261,
     TK_WHILE = 262,
     TK_SWITCH = 263,
     TK_CASE = 264,
     TK_MOD = 265,
     TK_MAIN = 266,
     TK_RETURN = 267,
     TK_INT = 268,
     TK_FLT = 269,
     TK_CHR = 270,
     PALAVRA = 271,
     TK_APAR = 272,
     TK_FPAR = 273,
     TK_ACH = 274,
     TK_FCH = 275,
     TK_PV = 276,
     TK_RECEBE = 277,
     TK_VI = 278,
     NUM_INT = 279,
     NUM_FLT = 280,
     TK_INCLUDE = 281,
     TK_VAR = 282,
     TK_OU = 283,
     TK_E = 284,
     TK_MA = 285,
     TK_ME = 286,
     TK_MEI = 287,
     TK_MAI = 288,
     TK_DIF = 289,
     TK_IGUAL = 290,
     TK_MENOS = 291,
     TK_MAIS = 292,
     TK_DIV = 293,
     TK_MULT = 294
   };
#endif
/* Tokens.  */
#define TK_IF 258
#define TK_ELSE 259
#define TK_FOR 260
#define TK_DO 261
#define TK_WHILE 262
#define TK_SWITCH 263
#define TK_CASE 264
#define TK_MOD 265
#define TK_MAIN 266
#define TK_RETURN 267
#define TK_INT 268
#define TK_FLT 269
#define TK_CHR 270
#define PALAVRA 271
#define TK_APAR 272
#define TK_FPAR 273
#define TK_ACH 274
#define TK_FCH 275
#define TK_PV 276
#define TK_RECEBE 277
#define TK_VI 278
#define NUM_INT 279
#define NUM_FLT 280
#define TK_INCLUDE 281
#define TK_VAR 282
#define TK_OU 283
#define TK_E 284
#define TK_MA 285
#define TK_ME 286
#define TK_MEI 287
#define TK_MAI 288
#define TK_DIF 289
#define TK_IGUAL 290
#define TK_MENOS 291
#define TK_MAIS 292
#define TK_DIV 293
#define TK_MULT 294




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


