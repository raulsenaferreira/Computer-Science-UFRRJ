/**
 * @author Raul
 * 
 *  Classe Dicionario:
 *  	Responsável por pegar todas as perguntas e respostas do arquivo,
 *  	colocá-los em uma coleção, e escolher uma aleatóriamente dentro desta coleção.
 * 
 * */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dicionario {
	private ArrayList<String> perguntas;
	private ArrayList<String> respostas;
	List<Colecao> listaDeColecao;
	
	public Dicionario() throws IOException{
		perguntas = new ArrayList<String>();
		respostas = new ArrayList<String>();
		listaDeColecao = new ArrayList<Colecao>();
		
		File f = new File("Files/Perguntas.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String pergunta = null;
		
		File f2 = new File("Files/Respostas.txt");
		FileReader fr2 = new FileReader(f2);
		BufferedReader br2 = new BufferedReader(fr2);
		String resposta = null;
		
		while(br.ready() && br2.ready())
		{
			//add pergunta à lista de perguntas
			pergunta = br.readLine();
			this.perguntas.add(pergunta.toUpperCase());
			//add resposta à lista de respostas
			resposta = br2.readLine();
			this.respostas.add(resposta.toUpperCase());
		}

		br.close();
		br2.close();
		
		for(int i = 0; i < perguntas.size(); i++){
			Colecao colecao = new Colecao(
					perguntas.get(i), 
					respostas.get(i)
			);
			
			listaDeColecao.add(colecao);
		}
	}

	public Colecao sorteio() throws IOException{
		
		Collections.shuffle(listaDeColecao); //embaralha lista
		
		return listaDeColecao.get(0);
	}
}
