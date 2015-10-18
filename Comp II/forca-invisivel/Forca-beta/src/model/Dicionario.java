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
	}

	public Colecao sorteio() throws IOException{
		int i;
		
		File f = new File("Files/Perguntas.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String pergunta = null;
		
		File f2 = new File("Files/Respostas.txt");
		FileReader fr2 = new FileReader(f2);
		BufferedReader br2 = new BufferedReader(fr2);
		String resposta = null;
		
		while(br.ready())
		{
			pergunta = br.readLine();
			this.perguntas.add(pergunta.toUpperCase());
		}
		while(br2.ready())
		{
			resposta = br2.readLine();
			this.respostas.add(resposta.toUpperCase());
		}

		br.close();
		br2.close();
		
		for(i = 0; i < perguntas.size(); i++){
			Colecao colecao = new Colecao();
			colecao.setPergunta(perguntas.get(i));
			colecao.setResposta(respostas.get(i));
			colecao.setSomPergunta(respostas.get(i));
			colecao.setSomResposta(respostas.get(i));
			
			listaDeColecao.add(colecao);
		}
		
		//embaralha/sorteia lista
		Collections.shuffle(listaDeColecao);
		
		return listaDeColecao.get(0);
	}
}
