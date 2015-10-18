package br.ufrrj.im.cc.ed2.join.merge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufrrj.im.cc.ed2.join.base.ColunaTupla;
import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;
import br.ufrrj.im.cc.ed2.modelo.Object;

public class MergeJoin implements Iterator {
	private Relacao relacaoConstrucao;
	private Relacao relacaoPoda;
	private Relacao relacaoAux;
	private ArrayList<Tupla> mergeJoin;
	private String nomeColuna1, nomeColuna2, nomeColuna3;
	
	public MergeJoin(String relacao1, String relacao2) {
		this.relacaoConstrucao = new Relacao(relacao1);
		this.relacaoPoda = new Relacao(relacao2);
		this.mergeJoin = new ArrayList<>();
	}
	
	public MergeJoin(Relacao relacao1, String coluna1, Relacao relacao2, String coluna2) {
		this.relacaoConstrucao = relacao1;
		this.relacaoPoda = relacao2;
		this.nomeColuna1 = coluna1;
		this.nomeColuna2 = coluna2;
		this.mergeJoin = new ArrayList<>();
	}
	
	public MergeJoin(String relacao1, String relacao2, String relacao3) {
		this.relacaoConstrucao = new Relacao(relacao1);
		this.relacaoPoda = new Relacao(relacao2);
		this.relacaoAux = new Relacao(relacao3);
		this.mergeJoin = new ArrayList<>();
	}

	public List ordenaRegistros(Relacao relacao, int index){
		Tupla tupla;
		List<Object> vetor = new ArrayList<Object>();
		//List<Tupla> listaTuplas = new ArrayList<Tupla>();
		
		relacao.open();
		while ((tupla = (Tupla) relacao.next()) != null) {
			//listaTuplas.add(tupla);
			Object registro = new Object(Integer.parseInt((String) tupla.getValoresRelacao().get(index)), tupla.getValoresRelacao());
			vetor.add(registro);
		}
		relacao.close();
		//Collections.sort(listaTuplas);
		Collections.sort(vetor);
		
		return vetor;
	}

	public Iterator open() {
		ArrayList<Tupla> tupla1 = new ArrayList<>();
		ArrayList<Tupla> tupla2 = new ArrayList<>();
		
		
				
		return null;
	}


	public Iterator open(String texto, int tipo) {
		List<Object> vetor1 = new ArrayList<Object>();
		List<Object> vetor2 = new ArrayList<Object>();
		
		vetor1 = ordenaRegistros(relacaoConstrucao, 0);
		
		if(tipo==1){
			vetor2 = ordenaRegistros(relacaoPoda, 4);
		}
		else if(tipo==2){
			vetor2 = ordenaRegistros(relacaoPoda, 3);
		}
		
		//merge join
		int i = 0;
		int flag = 0;
		String idRelacao = null;
		
		//pego o primary key referente ao que estou buscando
		while(flag == 0){
			if(vetor1.get(i).getLista().get(1).equals(texto)){
				idRelacao = (String) vetor1.get(i).getLista().get(0);
				flag=1;
			}
			i++;
		}
		
		//faco o merge com as foreign keys que estão na segunda tabela
		if(tipo==1){
			for (i = 0; i < vetor2.size(); i++) {
				if(idRelacao.equals((String) vetor2.get(i).getLista().get(4))){
					Tupla t = new Tupla();
					//ColunaTupla ct = new ColunaTupla(nomeColuna, valor)
					System.out.println((String) vetor2.get(i).getLista().get(1));
				}
				else{
					int pk = Integer.parseInt(idRelacao);
					int fk = Integer.parseInt((String) vetor2.get(i).getLista().get(4));
					
					if(pk<fk){
						return null;
					}
				}
			}
		}
		
		else if(tipo==2){
			for (i = 0; i < vetor2.size(); i++) {
				if(idRelacao.equals((String) vetor2.get(i).getLista().get(3))){
					System.out.println((String) vetor2.get(i).getLista().get(1));
				}
				else{
					int pk = Integer.parseInt(idRelacao);
					int fk = Integer.parseInt((String) vetor2.get(i).getLista().get(3));
					
					if(pk<fk){
						return null;
					}
				}
			}
		}
		else if(tipo==3){
			
		}
		else{
			
		}
		
		return this;
	}
	
	public Iterator next() {
		
		return null;
	}

	public Iterator close() {
		//fecha as duas relations
		return null;
	}



	public void open(String descCategoria, String nomeAutor, String nomeEditora) {
		int i = 0;
		int flag = 0;
		String idRelacao1=null;
		String idRelacao2=null;
		String idRelacao3=null;
		
		List<Object> vetor1 = new ArrayList<Object>();
		List<Object> vetor2 = new ArrayList<Object>();
		List<Object> vetor3 = new ArrayList<Object>();
		
		vetor1 = ordenaRegistros(relacaoConstrucao, 0);
		vetor2 = ordenaRegistros(relacaoPoda, 3);
		vetor3 = ordenaRegistros(relacaoAux, 0);
		
		//merge join
		//pego o primary key referente ao que estou buscando
		while(flag == 0 && i < vetor1.size()){
			if(vetor1.get(i).getLista().get(1).equals(descCategoria)){
				idRelacao1 = (String) vetor1.get(i).getLista().get(0);
				flag=1;
			}
			i++;
		}
		
		//faço o merge com as foreign keys que estão na segunda e terceira tabelas
		for (i = 0; i < vetor2.size(); i++) {
			String nE = (String) vetor2.get(i).getLista().get(2);
			idRelacao2 = (String) vetor2.get(i).getLista().get(3);
			if(idRelacao1.equals(idRelacao2) && nE.equals(nomeEditora)){
				idRelacao3 = (String) vetor2.get(i).getLista().get(4);
				
				for (int j = 0; j < vetor3.size(); j++){
					String nA = (String) vetor3.get(j).getLista().get(1);
					String idAutor = (String) vetor3.get(j).getLista().get(0);
					if(idRelacao3.equals(idAutor) && nA.equals(nomeAutor)){
						System.out.println(vetor2.get(i).getLista().get(1));
					}
					else{
						int pk = Integer.parseInt(idRelacao3);
						int fk = Integer.parseInt(idRelacao2);
						
						if(pk<fk){
							return;
						}
					}
				}
			}
			else{
				int pk = Integer.parseInt(idRelacao1);
				int fk = Integer.parseInt(idRelacao2);
				
				if(pk<fk){
					return;
				}
			}
		}
	}

	@Override
	public double custo() {
		return relacaoConstrucao.getNumeroLinhas()+relacaoPoda.getNumeroLinhas();
	}
}
