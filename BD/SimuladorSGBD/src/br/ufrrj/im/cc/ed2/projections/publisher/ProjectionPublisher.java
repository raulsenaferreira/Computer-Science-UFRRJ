package br.ufrrj.im.cc.ed2.projections.publisher;

import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Selecao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;
import br.ufrrj.im.cc.ed2.join.hash.HashJoin;
import br.ufrrj.im.cc.ed2.join.merge.MergeJoin;
import br.ufrrj.im.cc.ed2.join.nL.NestedLoop;
import br.ufrrj.im.cc.ed2.modelo.Autor;

public class ProjectionPublisher {
	private Autor Autor;
	private Relacao book;
	private Relacao author;
	
	public static void main(String[] args) {
		ProjectionPublisher pj = new ProjectionPublisher("Luis Fernando Orleans");
		pj.custoJuncaoSelecao();
	}


	public ProjectionPublisher(String an){
		book = new Relacao("Livro");
		author = new Relacao("Autor");
		Tupla linha = (Tupla) new Selecao(author, an, "nome").next();
		this.Autor = new Autor(linha.getValorCampo("nome"), linha.getValorCampo("id"));
	}
	
	private void executeQuery(){
	
		Iterator plano = custoJuncaoSelecao();
		if(plano instanceof HashJoin){
			plano = new HashJoin(new Selecao(book, this.Autor.getNome(), "id_autor").getRelacaoTemp(), "id_autor", this.author, "id");
			plano.open();
			Tupla t;
			while((t = (Tupla) plano.next()) != null){
				t.getValorCampo("titulo");
			}
	
		}
		else if(plano instanceof MergeJoin){
			plano = new MergeJoin(new Selecao(book, this.Autor.getNome(), "id_autor").getRelacaoTemp().getNome(), this.author.getNome());
	
		}
	
	}
	
	
	public Iterator custoJuncaoSelecao(){
		HashJoin hj = new HashJoin(book, "id_autor", author, "nome");
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
		Selecao s = new Selecao(this.book, Autor.getId(), "id_autor");
	
		HashJoin hs = new HashJoin(book, "id_autor", author, "nome");
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
