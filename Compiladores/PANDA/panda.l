%{
#include <string>
#include <stdlib.h>

%}

NUM_INT [1-9][0-9]*
NUM_FLT [0-9]*\.[0-9]+|[0-9]+\.[0-9]*
ID [a-zA-Z_]+[0-9a-zA-Z_]*
PALAVRA [.\n]* 
ESCREVA \"([^\"]|\\\")*\" 

%%
	
"principal" 	{ return TK_MAIN;}

"inteiro" 		{ yylval.codigo = yytext;
				return TK_INT;}

"real"			{ yylval.codigo = yytext;
				return TK_FLT;}
				
"caractere"		{ return TK_CHR;}

"para"			{ return TK_FOR;}
"senao"			{ return TK_ELSE;}
"se"			{ return TK_IF;}
"enquanto"		{ return TK_WHILE;}
"faca"			{ return TK_DO;}
"selecionar"	{ return TK_SWITCH;}
"caso"			{ return TK_CASE;}
"retorna"		{return TK_RETURN;}

[}]		{ return TK_FCH;}
[{]		{ return TK_ACH;}
[;]		{ return TK_PV;}
[)]		{ return TK_FPAR;}
[(]		{ return TK_APAR;}
[*]		{ return TK_MULT;}
[/]		{ return TK_DIV;}
[+]		{ return TK_MAIS;}
[-]		{ return TK_MENOS;}
[,]		{ return TK_VI;}
[#]		{ return TK_INCLUDE;}
[%]		{ return TK_MOD; }

">="	{ return TK_MAI;}
"<="	{ return TK_MEI;}
"&&"	{ return TK_E;}
"||" 	{ return TK_OU;}
"!="	{ return TK_DIF;}
"=="	{ return TK_IGUAL;}
"="		{ return TK_RECEBE;}
"<"		{ return TK_ME;}
">"		{ return TK_MA;}
	
{ID}		{ yylval.codigo = yytext;
			return TK_VAR; }

{NUM_INT} 	{ yylval.codigo=yytext;
			return NUM_INT; }
			
{NUM_FLT} 	{ yylval.codigo=yytext;
			return NUM_FLT; }
			
[.]		{ return *yytext; }
%%

