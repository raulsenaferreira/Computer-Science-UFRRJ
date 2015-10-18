%{
#include <iostream>
#include <string.h>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>
#include <map>

#define YYSTYPE atributos

using namespace std;

int tempVal = 0;

typedef struct atributos
{
	string codigo;
	string tipo;
	string label;	// $...
	string temp;

} atributos_t;

map<string,atributos_t> tabelaDeVariaveisTemporarias;
pair<map<string,atributos_t>::iterator,bool> ret;

int yylex(void);
void yyerror(string);
int yyparse();
void adicionarTemporario(string tipo, string chave, string temp, string codigo);
int stringToInt(string temp);
string intToString(int temp);
float stringToFlt(string temp);
string esvaziaMap(map<string,atributos_t> mapa);
string geraTemporario();

%}

//inicio
%start INICIO

//estruturas
%token TK_IF 
%token TK_ELSE 
%token TK_FOR
%token TK_DO 
%token TK_WHILE 
%token TK_SWITCH 
%token TK_CASE
%token TK_MOD

//palavras reservadas
%token TK_MAIN TK_RETURN

//tipos
%token TK_INT TK_FLT TK_CHR PALAVRA

//simbolos
%token TK_APAR TK_FPAR TK_ACH TK_FCH TK_PV TK_RECEBE TK_VI

//escrita
%token NUM_INT NUM_FLT

//outros
%token TK_INCLUDE TK_VAR

//operadores aritmeticos
%right          TK_RECEBE
%left           TK_OU
%left           TK_E
%left           TK_MAI TK_MEI TK_ME TK_MA
%left           TK_IGUAL TK_DIF
%left           TK_MAIS TK_MENOS
%left           TK_MULT TK_DIV
%left           TK_APAR TK_FPAR

%%

/**/

INICIO			:	PROGRAMA	
					{ cout << "#include<iostream>\n#include<string.h>\n#include<stdio.h>\n#include<stdlib.h>\n\n"
							<< "int main()\n{\n" 
							<< esvaziaMap(tabelaDeVariaveisTemporarias) 
							<< "\n}"
							<< endl; 			
					}
				
				;

PROGRAMA		:	DEC_FUNCAO PROGRAMA
					{
						$$.codigo = $1.codigo + $2.codigo;
					}
					
				|	DEC_VARIAVEL PROGRAMA
					{
						$$.codigo = $1.codigo + $2.codigo;						
					}
					
				|	TK_INT TK_MAIN TK_APAR TK_FPAR BLOCO
					{
						$$.codigo = $1.codigo + $2.codigo + "()\n" + $5.codigo;					
					}
				;
		
BLOCO			:	TK_ACH COMANDOS TK_FCH
					{
						$$.codigo = "{\n" + $2.codigo + "\n}";
					}
					
				|	COMANDO
					{
						$$.codigo = $1.codigo;
					}
					
				|	TK_ACH TK_FCH
					{
						$$.codigo = "{}";
					}
				;

COMANDOS		:	COMANDO
				|	COMANDO COMANDOS
				;
		
COMANDO			:	DEC_VARIAVEL TK_PV
					{
						$$.codigo = $1.codigo + ";";
					}
				;

DEC_FUNCAO		: 	TIPO TK_VAR TK_APAR TK_FPAR BLOCO
					{
						$$.codigo = $1.codigo + " " + $2.codigo + "()" + $5.codigo;
					}
				;

//adicionarTemporario(string tipo, string chave, string temp, string codigo)
		
DEC_VARIAVEL	:	TIPO TK_VAR
					{
						$$.temp = geraTemporario();
						adicionarTemporario($1.tipo, $2.codigo, $$.label, "");
					}
					
				|	TIPO ATRIB
					{
						adicionarTemporario($1.tipo, $2.label , $2.temp, $$.codigo);
					}
				;
				
TIPO			:	TK_INT	{ $$.tipo = "int"; }
				|	TK_CHR  { $$.tipo = "char"; }
				|	TK_FLT	{ $$.tipo = "float"; }
				;
				
ATRIB			:	TK_VAR TK_RECEBE EXPR
					{
						$$.temp = $3.temp;
						$$.codigo = $1.codigo + " = " + $3.codigo;
						$$.label = $1.codigo;
						
					}
				;
				
EXPR			:	TK_APAR EXPR TK_FPAR
					{
						$$.temp = geraTemporario();
						$$.codigo = "(" + $$.temp + ")";
						
					}
				
				|	EXPR TK_MAIS EXPR
					{
						$$.temp = geraTemporario();
						$$.codigo = $1.codigo + "\n" + $3.codigo + "\n" + $$.temp + " = " + $1.temp + "+" + $3.temp + ";";
						
					}

				| 	EXPR TK_MENOS EXPR
					{
						$$.temp = geraTemporario();
						$$.codigo = $1.codigo + "\n" + $3.codigo + "\n" + $$.temp + " = " + $1.temp + "-" + $3.temp + ";";
					}

				|	EXPR TK_MULT EXPR
					{
						$$.temp = geraTemporario();
						$$.codigo = $1.codigo + "\n" + $3.codigo + "\n" + $$.temp + " = " + $1.temp + "*" + $3.temp + ";";
					}

				|	EXPR TK_DIV EXPR
					{
						$$.temp = geraTemporario();
						$$.codigo = $1.codigo + "\n" + $3.codigo + "\n" + $$.temp + " = " + $1.temp + "/" + $3.temp + ";";
					}
					
				|	NUM_INT
					{
						$$.temp = geraTemporario();
						$$.codigo = $$.temp + " = " + $1.codigo;
					}
				;
				
//adicionarTemporario(string tipo, string chave, string temp, string codigo)

//INICIO	:	TK_INT TK_MAIN TK_APAR TK_FPAR BLOCO { cout << "int main()\n{\n"+ esvaziaMap(tabelaDeVariaveisTemporarias) +"\treturn 0;\n}\n"; }
		//;
/*COMANDOS:	COMANDO				{ $$.codigo = $1.codigo; }
		|	COMANDO COMANDOS	{ $$.codigo = $1.codigo + "\n" + $2.codigo; }
		;*/
		
/*COMANDO	:	DEC TK_PV  { $$.codigo = $1.codigo + ";\n"; }
		//|	COND	   { $$.codigo = $1.codigo + "\n"; }
		;*/
		
/*EXPRI	:	EXPRI OPER EXPRI { $$.codigo = $1.codigo + $2.codigo + $3.codigo; }
		|	TK_APAR EXPRI TK_FPAR	{ $$.codigo = "(" + $2.codigo + ")"; }
		|	NUM_INT {
						//adicionarTemporario("inteiro", $1.codigo, $1.codigo, $$.codigo);
						$$.codigo = $1.codigo;
					}
		;
		
EXPRF	:	EXPRF OPER EXPRF { $$.codigo = $1.codigo + $2.codigo + $3.codigo; }
		|	EXPRF OPER EXPRI { $$.codigo = $1.codigo + $2.codigo + $3.codigo; }
		|	EXPRI OPER EXPRF { $$.codigo = $1.codigo + $2.codigo + $3.codigo; }
		|	TK_APAR EXPRF TK_FPAR	{ $$.codigo = "(" + $2.codigo + ")"; }
		|	NUM_FLT {$$.codigo = $1.codigo; }
		;
		
EXPR	:	TK_APAR EXPR TK_FPAR
		|	TK_VAR
		|	TK_VAR OPER
		|	NUM_INT
		|	NUM_INT OPER
		;
		
OPER	:	TK_MAIS EXPR
		|	TK_MENOS EXPR
		|	TK_MULT EXPR
		|	TK_DIV EXPR
		;	

DEC		: 	
			TK_INT TK_VAR ATRIBI
			{
				adicionarTemporario("int", $2.codigo, $3.codigo, $$.codigo);
				$$.codigo = "int " + tabelaDeVariaveisTemporarias[$2.codigo].label;
			}
		| 	TK_FLT TK_VAR ATRIBF
			{
				adicionarTemporario("float", $2.codigo, $3.codigo, $$.codigo);
				$$.codigo = "float " + tabelaDeVariaveisTemporarias[$2.codigo].label;
			}
		;
		
DEC		:	TIPO IDS
			{
				adicionarTemporario($1.codigo, $2.codigo, "", $$.codigo);
				
			}
		|
			TIPO TK_VAR
			
		|
		
			TIPO ATRIB
		;
		
TIPO	:	TK_INT	{ $$.codigo = "int"; }
		|	TK_CHR  { $$.codigo = "char"; }
		|	TK_FLT	{ $$.codigo = "float"; }
		;	
*/

%%

#include "lex.yy.c"

int yyparse();

int main( int argc, char* argv[] )
{
yyparse();

return 0;
}

void adicionarTemporario(string tipo, string chave, string temp, string codigo)
{
	atributos_t val;
	
	val.tipo = tipo;
	val.label = chave;
	val.temp = temp;
	val.codigo = codigo;
	
	tabelaDeVariaveisTemporarias.insert(pair<string,atributos_t>(chave,val));

}

string geraTemporario()
{
	tempVal++;
	return "$" + intToString(tempVal);
}

void yyerror( string MSG )
{
	cout << MSG << endl;
	exit(0);
}

string esvaziaMap(map<string,atributos_t> mapa)
{
	map<string,atributos_t>::const_iterator
	mit(mapa.begin()),
    mend(mapa.end());
    
    string s;
    
    for(;mit!=mend;++mit)
    
    	s += mit->second.tipo + " " + mit->second.temp +";\n" ;
    
	return s;	
}

//

int stringToInt(string temp)
{
	int result = 0;
	stringstream ss(temp);
	ss >> result;
	return result;
}

float stringToFlt(string temp)
{
	float result = 0;
	stringstream ss(temp);
	ss >> result;
	return result;
}

string intToString(int temp)
{
	stringstream out;
	out << temp;
	return out.str();
}


