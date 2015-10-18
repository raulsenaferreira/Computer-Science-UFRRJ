package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Colecao;
import model.Dicionario;
import model.Enum;
import model.Som;
import model.TTS;

public class ForcaController {	
	
	/*
	 * Atributos da Classe
	 * 
	 * */
	private static BufferedReader br;
	private static int contador; // conta as tentativas certas
	private static int limite; // limite de tentativas erradas
	private static boolean ok = false;
	private static char caracter; // armazena a entrada
	private static String respostaSorteada;
	private static Dicionario dicionario;
	private static Colecao objetoSorteado; 
	
	public ForcaController() {
		br = new BufferedReader(new InputStreamReader(System.in));
		newRound();
		while(caracter == 'n' || caracter == 'N')
			newRound();
		
	}
	
	private void newRound(){
		setNewWord();
		limite = 6;
		contador = 0;
		
		contador = respostaSorteada.length();
		
		//pergunta
		TTS.textToSpeech(objetoSorteado.getPergunta());
		System.out.println(objetoSorteado.getPergunta());
		System.out.println(objetoSorteado.getResposta());
		
		// Vetor auxiliaraux
		char[] vetorAuxiliar = prepareArray();
		
		while(contador != 0 && limite != 0)
		{
			System.out.println(vetorAuxiliar);
			System.out.print("Letra: ");
			
			try {
				caracter = br.readLine().toUpperCase().charAt(0);
			} catch (IOException e) {
				TTS.textToSpeech(Enum.ERRO);
				System.exit(0);
			}
			
			if(caracter == 'E')
				TTS.textToSpeech(Enum.E);
			else if (caracter == 'O')
				TTS.textToSpeech(Enum.O);
			else
				TTS.textToSpeech(caracter+"");
			
			
			for (int i = 0; i < respostaSorteada.length(); i++) 
			{
				if((caracter == respostaSorteada.charAt(i)) || 
				   (caracter == 'A' && (respostaSorteada.charAt(i) == 'Á' || respostaSorteada.charAt(i) == 'Â' || respostaSorteada.charAt(i) == 'Ã')) ||
				   (caracter == 'E' && (respostaSorteada.charAt(i) == 'É' || respostaSorteada.charAt(i) == 'Ê')) ||
				   (caracter == 'I' && (respostaSorteada.charAt(i) == 'Í')) ||
				   (caracter == 'O' && (respostaSorteada.charAt(i) == 'Ó' || respostaSorteada.charAt(i) == 'Ô') || respostaSorteada.charAt(i) == 'Õ') ||
				   (caracter == 'U' && (respostaSorteada.charAt(i) == 'Ú')) )
				{
					vetorAuxiliar[2*i] = respostaSorteada.charAt(i);
					ok = true;
					contador --;
					
				}
			}
			
			if(ok){
				TTS.textToSpeech(Enum.LETRA_CERTA);
				TTS.wordProgress(vetorAuxiliar);
			}
			
			if(!ok){
				limite--;
				TTS.textToSpeech(Enum.LETRA_ERRADA);
				TTS.wordProgress(vetorAuxiliar);
			}
				
			
			ok = false;
		}
		
		if(limite == 0){
			TTS.textToSpeech(Enum.EOG);
			TTS.textToSpeech(Enum.ENTER);
		}
		else{
			System.out.println(respostaSorteada);
			TTS.play(objetoSorteado.getSomResposta());
			TTS.textToSpeech(Enum.PARABENS);
			TTS.textToSpeech(Enum.ENTER);
		}
		caracter = lerEntrada();
		
	}
	
	private void setNewWord(){
		try {
			//Instancia um novo Dicionario
			dicionario = new Dicionario();
			//Um novo objeto sorteado recebe um valor aleatório de dicionario
			objetoSorteado = dicionario.sorteio();
			//Salva a resposta em uma variável separada
			respostaSorteada = objetoSorteado.getResposta();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private char[] prepareArray(){
		
		char[] v = new char[respostaSorteada.length() * 2];
		
		for (int i = 0; i < respostaSorteada.length()*2; i++){	
			if(i%2 == 0)
				v[i] = '_';
			else
				v[i] = ' ';
		}
		
		return v;
	}
	
	private char lerEntrada(){
		try {
			return br.readLine().charAt(0);
		} catch (Exception e) {
			return lerEntrada();
		}
	}
	public static void main(String[] args) throws IOException{
		ForcaController fc = new ForcaController();		
	}
}
