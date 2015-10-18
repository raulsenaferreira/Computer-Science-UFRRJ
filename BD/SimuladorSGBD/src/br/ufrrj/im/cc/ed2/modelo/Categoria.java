package br.ufrrj.im.cc.ed2.modelo;

public class Categoria {
	private String id;
	private String descricao;
	
	public Categoria(){
		this.id = null;
		this.descricao = null;
	}
	
	public Categoria(String id, String descricao){
		this.id = id;
		this.descricao = descricao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
