package br.ufrrj.im.cc.ed2.join.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Tupla implements Iterator, Comparable<Tupla>{
	

	private List<ColunaTupla> listaColunas;
	private int contador;
	
	public Tupla(){
		listaColunas = new LinkedList<>();
	}

	@Override
	public Iterator open() {
		contador = 0;
		return null;
	}

	@Override
	public Iterator next() {
		if(contador < listaColunas.size())
			return listaColunas.get(contador++);
		return null;
	}

	@Override
	public Iterator close() {
		return null;
	}

	public void adicionaColuna(ColunaTupla colunaTupla) {
		listaColunas.add(colunaTupla);
	}
	
	@Override
	public String toString() {
		String resultado = "";
		for (ColunaTupla colunaTupla : listaColunas) {
			resultado += colunaTupla.toString();
		}
			return resultado;
	}

	public String getValorCampo(String campoRelacao) {
		for (ColunaTupla colunaTupla : listaColunas) {
			if(colunaTupla.getNomeColuna().equals(campoRelacao))
				return colunaTupla.getValor();
		}
		return null;
	}
	
	public List getValoresRelacao(){
		List listaValores = new ArrayList<>();
		for (ColunaTupla colunaTupla : listaColunas) {
			listaValores.add(colunaTupla.getValor());
		}
		return listaValores;
	}
	
	public void concatena(Tupla tupla) {
		listaColunas.addAll(tupla.listaColunas);
	}

	@Override
	public double custo() {
		return 1;
	}

	@Override
	public int compareTo(Tupla t) {
		return this.getValorCampo("id").compareTo(t.getValorCampo("id"));
	}
	
}
