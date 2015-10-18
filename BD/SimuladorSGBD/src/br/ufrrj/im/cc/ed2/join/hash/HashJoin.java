package br.ufrrj.im.cc.ed2.join.hash;

import java.util.Hashtable;

import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class HashJoin implements Iterator {

	private Relacao relacaoConstrucao;
	private Relacao relacaoPoda;
	private Relacao relacao3;
	private String campoRelacao3;
	private String campoRelacao2;
	private String campoRelacao1;
	private Hashtable<String, Tupla> tabela;
	
	public HashJoin(Relacao relacao1, String campoRelacao1, Relacao relacao2, String campoRelacao2) {
		this.relacaoConstrucao = relacao1;
		this.relacaoPoda = relacao2;
		this.campoRelacao1 = campoRelacao1;
		this.campoRelacao2 = campoRelacao2;
		this.relacao3 = null;
	}
	
	public HashJoin(Relacao relacao1, String campoRelacao1, Relacao relacao2, String campoRelacao2, Relacao relacao3, String campoRelacao3) {
		this.relacaoConstrucao = relacao1;
		this.relacaoPoda = relacao2;
		this.campoRelacao1 = campoRelacao1;
		this.campoRelacao2 = campoRelacao2;
		this.relacao3 = relacao3;
		this.campoRelacao3 = campoRelacao3;
	}
	
	@Override
	public Iterator open() {
		tabela = new Hashtable<>();
		relacaoConstrucao.open();
		Tupla tupla;
		while ((tupla = (Tupla) relacaoConstrucao.next()) != null) {
			String valor = tupla.getValorCampo(campoRelacao1);
			tabela.put(valor, tupla);
		}
		relacaoPoda.open();
		return this;
	}

	public Iterator open3() {
		tabela = new Hashtable<>();
		relacaoConstrucao.open();
		Tupla tupla;
		while ((tupla = (Tupla) relacaoConstrucao.next()) != null) {
			String valor = tupla.getValorCampo(campoRelacao1);
			tabela.put(valor, tupla);
		}
		relacaoPoda.open();
		return this;
	}
	
	@Override
	public Iterator next() {
		Tupla tupla = (Tupla) relacaoPoda.next();
		if (tupla == null) {
			return null;
		}
		Tupla tuplaCorrespondente = tabela.get(tupla.getValorCampo(campoRelacao2));
		if(tuplaCorrespondente == null){
			return new Tupla();
		}
		else{
			Tupla tuplaResultante = new Tupla();
			tuplaResultante.concatena(tupla);
			tuplaResultante.concatena(tuplaCorrespondente);
			return tuplaResultante;
		}
	}

	@Override
	public Iterator close() {
		this.relacaoConstrucao.close();
		this.relacaoPoda.close();
		return null;
	}

	@Override
	public double custo() {
		return this.relacaoConstrucao.getNumeroLinhas() +  this.relacaoPoda.getNumeroLinhas();
	}

}
