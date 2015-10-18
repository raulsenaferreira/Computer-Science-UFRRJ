package br.ufrrj.im.cc.ed2.modelo;

public class Livro {
	
	private String id;
	private String titulo;
	private String editora;
	private String id_categoria;
	private String id_autor;
	
	public Livro(){
		this.id = null;
		this.titulo = null;
		this.editora = null;
		this.id_autor= null;
		this.id_categoria = null;
	}
	
	public Livro(String id, String titulo, String editora, String autor, String categoria){
		this.id = id;
		this.titulo = titulo;
		this.editora = editora;
		this.id_autor = autor;
		this.id_categoria = categoria;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(String id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getId_autor() {
		return id_autor;
	}

	public void setId_autor(String id_autor) {
		this.id_autor = id_autor;
	}

	
}
