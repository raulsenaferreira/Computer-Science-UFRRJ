package br.ufrrj.im.cc.ed2.join.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import br.ufrrj.im.cc.ed2.catalogo.Catalogo;
import br.ufrrj.im.cc.ed2.catalogo.Coluna;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class Relacao implements Iterator {
	
	private String nomeRelacao;
	private BufferedReader arquivo;
	
	public Relacao(String nomeRelacao){
		this.nomeRelacao = nomeRelacao;
	}

	/**
	 * A funcao deste metodo e abrir o arquivo e esperar que este seja
	 * lido atraves do metodo next()
	 */
	@Override
	public Iterator open() {
		String nomeArquivo = Catalogo.getInstancia().recuperaNomeArquivo(nomeRelacao);
		try {
			arquivo = new BufferedReader(new FileReader(new File(nomeArquivo)));
		} catch (FileNotFoundException e) {
			geraExcecao("Erro ao abrir o arquivo "+nomeArquivo+" para a relacao "+nomeRelacao+".", e);
		}
		
		return this;
	}


	@Override
	public Iterator next() {
		//le a proxima linha
		String linha = null;
		try {
			linha = arquivo.readLine();
			if(linha == null) return null;
		} catch (IOException e) {
			geraExcecao("Erro na leitura de uma linha da relacao "+nomeRelacao, e);
		}
		//constroi a tupla
		String[] valores = linha.split("\t");
		List<Coluna> colunas = Catalogo.getInstancia().recuperaColunas(nomeRelacao);
		if(valores.length != colunas.size()){
			throw new RuntimeException("O numero de colunas na descricao da relacao "+nomeRelacao+" e incompatavel com"
					+ " o lido do arquivo. Linha lida: "+linha);
		}
		Tupla tuplaRetorno = new Tupla();
		for (int i = 0; i < valores.length; i++) {
			String valor = valores[i];
			String nomeColuna = colunas.get(i).getNomeColuna();
			ColunaTupla colunaTupla = new ColunaTupla(nomeColuna, valor);
			tuplaRetorno.adicionaColuna(colunaTupla);
		}
		//retorna a tupla
		
		return tuplaRetorno;
	}

	@Override
	public Iterator close() {
		try {
			arquivo.close();
		} catch (IOException e) {
			geraExcecao("Erro ao fechar a relacao "+nomeRelacao+".", e);
		}
		return null;
	}
	
	private void geraExcecao(String mensagem, Exception e) {
		e.printStackTrace();
		throw new RuntimeException(mensagem, e);
	}

	public int getNumeroLinhas() {
		return Catalogo.getInstancia().recuperaNumeroLinhas(nomeRelacao);
	}
	
	public String getNome(){
		return this.nomeRelacao;
	}

	@Override
	public double custo() {
		// TODO Auto-generated method stub
		return 0;
	}


}
