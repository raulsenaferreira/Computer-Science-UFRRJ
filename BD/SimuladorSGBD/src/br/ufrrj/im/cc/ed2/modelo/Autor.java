package br.ufrrj.im.cc.ed2.modelo;

public class Autor {
	
	private String nome;
	private String id;
	
	public Autor(){
		this.nome = null;
		this.id = null;
	}
	
	public Autor(String nome, String id){
		this.nome = nome;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
