package br.ufrrj.im.cc.ed2.join.base;

public class ColunaTupla  implements Iterator{
	private String nomeColuna;
	private String valor;
	
	public ColunaTupla(String nomeColuna, String valor) {
		this.nomeColuna = nomeColuna;
		this.valor = valor;
	}

	@Override
	public Iterator open() {
		return null;
	}

	@Override
	public Iterator next() {
		return null;
	}

	@Override
	public Iterator close() {
		return null;
	}
	
	public String getNomeColuna(){
		return nomeColuna;
	}
	
	public String getValor(){
		return valor;
	}
	
	@Override
	public String toString() {
		return "["+nomeColuna+"]: "+valor;
	}

	@Override
	public double custo() {
		// TODO Auto-generated method stub
		return 0;
	}

}
