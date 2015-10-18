package br.ufrrj.im.cc.ed2.catalogo;

public class Coluna implements Comparable<Coluna>{
	private String nome;
	private String tipo;
	private int ordem;
	//outros metadados... histograma de valores, por exemplo.
	
	public Coluna(String nome, String tipo, int ordem) {
		this.nome = nome;
		this.tipo = tipo;
		this.ordem = ordem;
	}

	@Override
	public int compareTo(Coluna outraColuna) {
		if(this.ordem > outraColuna.ordem)
			return 1;
		else
			return -1;
	}
	
	public String getNomeColuna(){
		return nome;
	}
	

}
