package br.ufrrj.im.cc.ed2.index.dispersao;

import java.util.SortedSet;
import java.util.TreeSet;

public class Elemento<E> {
	
	private SortedSet<E> lista;
	
	
	public Elemento(){
		lista = new TreeSet<>();
	}
	
	public void adicionar(E objeto){
		lista.add(objeto);
	}
	
	public E recuperar(E objeto){
		for (E elemento : lista) {
			if(elemento.equals(objeto))
				return elemento;
		}
		return null;
	}
	
	public void imprimeTodos(){
		for (E elemento : lista) {
			System.out.println(elemento);
		}
	}

}
