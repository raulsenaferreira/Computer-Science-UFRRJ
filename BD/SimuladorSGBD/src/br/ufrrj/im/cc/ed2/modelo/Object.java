package br.ufrrj.im.cc.ed2.modelo;

import java.util.ArrayList;
import java.util.List;

public class Object implements Comparable<Object>{
	
	private int id;
	@SuppressWarnings("rawtypes")
	private List lista = new ArrayList<Object>();
	
	@SuppressWarnings("rawtypes")
	public Object(int id, List lista){
		super();
		this.id = id;
		this.lista = lista;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	@SuppressWarnings("rawtypes")
	public List getLista(){
		return this.lista;
	}

	@Override
	public int compareTo(Object object) {
		int ordem = object.getId();
		return this.id-ordem;
	}
	
	@Override
	public String toString() {
		return "["+id+"]"+lista;
	}

}
