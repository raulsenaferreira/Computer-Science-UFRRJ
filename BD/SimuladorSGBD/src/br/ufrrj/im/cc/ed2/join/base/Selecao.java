package br.ufrrj.im.cc.ed2.join.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.ufrrj.im.cc.ed2.catalogo.Catalogo;

public class Selecao implements Iterator{
	
	private Relacao relation;
	private String parameter;
	private String column;
	private Tupla tupla;
	
	public Selecao(Relacao rel, String object, String column){
		this.relation = rel;
		this.parameter = object;
		this.column = column;
	}

	@Override
	public Iterator open() {
		this.relation.open();
		return this.relation;
	}

	@Override
	public Iterator next() {
		this.tupla = new Tupla();
		
		while((tupla = (Tupla) relation.next()) != null){
			if(tupla.getValorCampo(column).equals(this.parameter))
				return tupla;
		}
		
		return null;
	}

	@Override
	public Iterator close() {
		relation.close();
		return null;
	}

	@Override
	public double custo() {
		return relation.getNumeroLinhas();
	}
	
	public Relacao getRelacaoTemp(){
		try {
			File f = new File(Catalogo.getInstancia().recuperaNomeArquivo(relation.getNome()+"_temp"));
			FileWriter fr = new FileWriter(f);
			BufferedWriter br = new BufferedWriter(fr);
			
			this.open();
			Tupla t;
			while((t = (Tupla) next()) != null){
				String linha ="";
				t.open();
				ColunaTupla colunaTupla;
				colunaTupla = (ColunaTupla) t.next();
				linha = colunaTupla.getValor();
				while((colunaTupla = (ColunaTupla) t.next()) != null){
					linha += "	"+colunaTupla.getValor();
				}
				
				br.write(linha);
				br.newLine();
			}
			
			br.close();
			fr.close();
			
			Relacao relacaoT = new Relacao(relation.getNome()+"_temp");
			return relacaoT;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
