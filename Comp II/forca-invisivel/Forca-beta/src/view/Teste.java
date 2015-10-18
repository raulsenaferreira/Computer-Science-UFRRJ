package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Colecao;
import model.Dicionario;
import model.Som;

public class Teste {
	public static void main(String[] args) throws IOException
	{
		Dicionario dicionario = new Dicionario();
		Colecao objetoSorteado = dicionario.sorteio();
		String respostaSorteada = objetoSorteado.getResposta();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int contador = respostaSorteada.length();
		int limite = 8;
		boolean ok = false;
		char caracter;
		
		//pergunta
		Som.tocaSom("perguntas/"+respostaSorteada);
		System.out.println(objetoSorteado.getPergunta());
		System.out.println(objetoSorteado.getResposta());
		char[] aux = new char[respostaSorteada.length() * 2];
		
		for (int i = 0; i < respostaSorteada.length()*2; i++)
		{	
			if(i%2 == 0)
				aux[i] = '_';
			else
				aux[i] = ' ';
		}
		
		while(contador != 0 && limite != 0)
		{
			System.out.println(aux);
			System.out.print("Letra: ");
			caracter = br.readLine().toUpperCase().charAt(0);
			
			for (int i = 0; i < respostaSorteada.length(); i++) 
			{
				if((caracter == respostaSorteada.charAt(i)) || 
				   (caracter == 'A' && (respostaSorteada.charAt(i) == 'Á' || respostaSorteada.charAt(i) == 'Â' || respostaSorteada.charAt(i) == 'Ã')) ||
				   (caracter == 'E' && (respostaSorteada.charAt(i) == 'É' || respostaSorteada.charAt(i) == 'Ê')) ||
				   (caracter == 'I' && (respostaSorteada.charAt(i) == 'Í')) ||
				   (caracter == 'O' && (respostaSorteada.charAt(i) == 'Ó' || respostaSorteada.charAt(i) == 'Ô') || respostaSorteada.charAt(i) == 'Õ') ||
				   (caracter == 'U' && (respostaSorteada.charAt(i) == 'Ú')) )
				{
					aux[2*i] = respostaSorteada.charAt(i);
					ok = true;
					contador --;
				}
			}
			
			if(ok == false)
				limite--;
			
			ok = false;
		}
		
		if(limite == 0)
		{
			Som.tocaSom("sons/fimDeJogoErro");
			Som.tocaSom("respostas/mensagem");
		}
		else
		{
			System.out.println(respostaSorteada);
			Som.tocaSom(objetoSorteado.getSomResposta());
			Som.tocaSom("sons/parabens");
			Som.tocaSom("respostas/mensagem");
		}
	}
}
