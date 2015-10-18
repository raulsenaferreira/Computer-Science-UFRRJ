package model;

public class Enum {
	
	public static final String PARABENS = "Parabéns, você acertou!";
	public static final String ENTER = "pressione N para um novo jogo, ou eh para sair.";
	public static final String LETRA_ERRADA = "letra errada.";
	public static final String _ = "traço";
	public static final String LETRA_CERTA  = "letra certa!";
	public static final String PROGRESSO = "seu progresso é";
	public static final String ERRO = "o jogo apresentou um erro fatal e será encerrado agora";
	public static final String EOG = "infelizmente você errou, fim de jogo"; //End Of Game
	
	public static final String A = "a";
	public static final String B = "b";
	public static final String C = "c";
	public static final String D = "d";
	public static final String E = "eh";
	public static final String F = "f";
	public static final String G = "g";
	public static final String H = "h";
	public static final String I = "i";
	public static final String J = "j";
	public static final String K = "k";
	public static final String L = "l";
	public static final String M = "m";
	public static final String N = "n";
	public static final String O = "oh";
	public static final String P = "p";
	public static final String Q = "q";
	public static final String R = "r";
	public static final String S = "s";
	public static final String T = "t";
	public static final String U = "u";
	public static final String V = "v";
	public static final String W = "w";
	public static final String X = "x";
	public static final String Y = "y";
	public static final String Z = "z";
	
	
	public static String parser(char[] arg){
		StringBuffer aux = new StringBuffer();
		
		for(int i = 0; i < arg.length; i++){
			if (arg[i] == 'É' || arg[i] == 'E')
				aux.append(E+", ");
			else if (arg[i] == 'Ó' || arg[i] == 'O')
				aux.append(O+", ");
			else if (arg[i] == '_')
				aux.append(_+", ");
			else if (arg[i] != ' ')
				aux.append(arg[i]+", ");
		}
		
		return aux.toString();
	}
	
	
	
}
