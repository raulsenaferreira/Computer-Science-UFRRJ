package br.ufrrj.im.cc.ed2.projections.category;

import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Selecao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;
import br.ufrrj.im.cc.ed2.join.hash.HashJoin;
import br.ufrrj.im.cc.ed2.join.merge.MergeJoin;
import br.ufrrj.im.cc.ed2.join.nL.NestedLoop;
import br.ufrrj.im.cc.ed2.modelo.Autor;
import br.ufrrj.im.cc.ed2.modelo.Categoria;

public class ProjectionCategory {
	
	private Categoria Categoria;
	private Relacao book;
	private Relacao category;
	
	
	public ProjectionCategory(String an){
		book = new Relacao("Livro");
		category = new Relacao("Categoria");
		Selecao sel = new Selecao(category, an, "descricao");
		Tupla linha = (Tupla) new Selecao(category, an, "descricao").next();
		this.Categoria = new Categoria(linha.getValorCampo("id"), linha.getValorCampo("descricao"));
	}
	
	private void executeQuery(){
		
		Iterator plano = custoJuncaoSelecao();
		if(plano instanceof HashJoin){
			plano = new HashJoin(new Selecao(book, this.Categoria.getDescricao(), "id_autor").getRelacaoTemp(), "id_autor", this.category, "id");
			plano.open();
			Tupla t;
			while((t = (Tupla) plano.next()) != null){
				t.getValorCampo("titulo");
			}
			
		}
		else if(plano instanceof MergeJoin){
			plano = new MergeJoin(new Selecao(book, this.Categoria.getDescricao(), "id_autor").getRelacaoTemp().getNome(), this.category.getNome());
			
		}
		
	}
	
	
	public Iterator custoJuncaoSelecao(){
		HashJoin hj = new HashJoin(book, "id_autor", category, "nome");
		double custoHJ = hj.custo();
		
		MergeJoin mj = new MergeJoin("Livro", "Autor");
		double custoMJ = mj.custo();
		
		NestedLoop nl = new NestedLoop("Livro", "Autor");
		double custoNL = nl.custo();
		
		if(custoHJ < custoMJ && custoHJ < custoNL)
			return hj;// + new Selecao(this.book, this.Autor.getId(), "id_autor").custo();
		if(custoMJ < custoHJ && custoMJ < custoNL)
			return mj;// + new Selecao(this.book, this.Autor.getId(), "id_autor").custo();
		if(custoNL < custoMJ && custoNL < custoHJ)
			return nl;// + new Selecao(this.book, this.Autor.getId(), "id_autor").custo();
		
		return null;
	}
	
	public double custoSelecaoJuncao(){
		Selecao s = new Selecao(this.book, Categoria.getId(), "id_autor");
		
		HashJoin hs = new HashJoin(book, "id_autor", category, "nome");
		double custoHJ = hs.custo() + s.custo();
		
		MergeJoin mj = new MergeJoin("Livro", "Autor");
		double custoMJ = mj.custo() + s.custo();
		
		NestedLoop nl = new NestedLoop("Livro", "Autor");
		double custoNL = nl.custo() + s.custo();
		
		if(custoHJ < custoMJ && custoHJ < custoNL)
			return custoHJ;
		if(custoMJ < custoHJ && custoMJ < custoNL)
			return custoMJ;
		if(custoNL < custoMJ && custoNL < custoHJ)
			return custoNL;
		
		return 0.0;
	}
	
	

}


