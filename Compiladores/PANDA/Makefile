all: 	
		lex panda.l
		yacc -d panda.y
		g++ -o panda y.tab.c -lfl
		./panda < entrada.txt > saida.cpp
		g++ -o saida saida.cpp
		./saida
