package br.ufrrj.im.cc.ed2.index.dispersao;

public class TabelaDispersao<E> {
	
	private Elemento<E>[] tabela;
	
	@SuppressWarnings("unchecked")
	public TabelaDispersao(int tamanho){
		tabela = new Elemento[tamanho];
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = new Elemento<E>();			
		}
	}
	
	public TabelaDispersao(){
		this(25);
	}
	
	public void adiciona(E objeto){
		int index = objeto.hashCode() % tabela.length;
		tabela[index].adicionar(objeto);
	}
	
	public E recupera(E elemento){
		int index = elemento.hashCode() % tabela.length;
		return tabela[index].recuperar(elemento);
	}

	
	public void imprimeTabela(){
		for (int i = 0; i < tabela.length; i++) {
			Elemento<E> elemento = tabela[i];
			elemento.imprimeTodos();
			System.out.println("------------");
		}
	}
	

}
