package br.ufrrj.im.cc.ed2.catalogo;

import java.util.List;


public class ItemCatalogo {
	
	private String nomeArquivo;
	private String nomeRelacao;
	private List<Coluna> colunas;
	private int numeroLinhas;
	//outros metadados... 
	
	public ItemCatalogo(String nomeRelacao, String nomeArquivo, List<Coluna> colunas, int numeroLinhas){
		this.nomeRelacao = nomeRelacao;
		this.nomeArquivo = nomeArquivo;
		this.colunas = colunas;
		this.numeroLinhas = numeroLinhas;
	}
	
	public boolean comparaNomeRelacao(String nomeRelacao2) {
		return nomeRelacao.equals(nomeRelacao2);
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public List<Coluna> getColunas() {
		return colunas;
	}

	public int numeroLinhas() {
		return numeroLinhas;
	}
	
}
