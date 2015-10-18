import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.ufrrj.im.cc.ed2.index.arvoreB.ArvoreB;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;
import br.ufrrj.im.cc.ed2.join.hash.HashJoin;
import br.ufrrj.im.cc.ed2.join.merge.MergeJoin;
import br.ufrrj.im.cc.ed2.join.nL.NestedLoop;

public class Main {
	
	public static void testaHashJoin(int tipo) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if(tipo ==1){
			System.out.println("Nome do autor: ");
			String nomeAutor = br.readLine();
			Relacao relacao1 = new Relacao("Livro");
			Relacao relacao2 = new Relacao("Autor");
			HashJoin join = new HashJoin(relacao1, "id_autor", relacao2, "id");
			join.open();
			Tupla tupla;
			while ((tupla = (Tupla) join.next()) != null) {
				if(tupla.getValorCampo("nome").equals(nomeAutor))
					System.out.println(tupla.getValorCampo("titulo"));			
			}
			
			join.close();
		}
		else if(tipo==2){
			System.out.println("Descrição da Categoria: ");
			String descCategoria = br.readLine();
			Relacao relacao1 = new Relacao("Livro");
			Relacao relacao2 = new Relacao("Categoria");
			HashJoin join = new HashJoin(relacao1, "id_categoria", relacao2, "id");
			join.open();
			Tupla tupla;
			while ((tupla = (Tupla) join.next()) != null) {
				if(tupla.getValorCampo("descricao").equals(descCategoria))
					System.out.println(tupla.getValorCampo("titulo"));			}
			
			join.close();
		}
		else if(tipo==3){
			System.out.println("Nome da Editora: ");
			String nomeEditora = br.readLine();
			ArvoreB<String, String> arvore = new ArvoreB<String, String>();
			ArvoreB<String, String> arvoreGerada = new ArvoreB<String, String>();
			arvoreGerada = arvore.geraArvore("Livro", nomeEditora);
			
			System.out.println(arvoreGerada.get(nomeEditora));
			
		}
		else{
			Relacao relacao1 = new Relacao("Categoria");
			Relacao relacao2 = new Relacao("Livro");
			
			HashJoin relation = new HashJoin(relacao1, "id", relacao2, "id_autor");
			relation.open();
			
			System.out.println("Nome do Autor: ");
			String nomeAutor = br.readLine();
			
			System.out.println("Descrição da Categoria: ");
			String descCategoria = br.readLine();
			
			System.out.println("Nome da Editora: ");
			String nomeEditora = br.readLine();
			
			Tupla tupla;
			while ((tupla = (Tupla) relation.next()) != null) {
				if(tupla.getValorCampo("id_categoria").equals(6)){
					System.out.println(tupla.getValorCampo("nome"));
				}
			}
			relation.close();
		}
		
		
		
		
	}
	
	public static void nestedLoopConsulta(int tipo) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if(tipo==1){
			System.out.println("Nome do autor: ");
			String nomeAutor = br.readLine();
			NestedLoop relation = new NestedLoop("Autor","Livro");
			relation.open(nomeAutor, tipo);
			
			relation.close();
		}
		else if(tipo==2){
			System.out.println("Descrição da Categoria: ");
			String descCategoria = br.readLine();
			MergeJoin relation = new MergeJoin("Categoria","Livro");
			relation.open(descCategoria,tipo);
			
			relation.close();
		}
		else if(tipo==3){
			int i = 0;
			boolean ok = false;
			System.out.println("Nome da Editora: ");
			String nomeEditora = br.readLine();
			ArvoreB<String, String> arvore = new ArvoreB<String, String>();
			ArvoreB<String, String> arvoreGerada = new ArvoreB<String, String>();
			arvoreGerada = arvore.geraArvore("Livro", nomeEditora);
			
			System.out.println(arvoreGerada.get(nomeEditora));
			
		}
		else{
			System.out.println("Nome do Autor: ");
			String nomeAutor = br.readLine();
			System.out.println("Descrição da Categoria: ");
			String descCategoria = br.readLine();
			System.out.println("Nome da Editora: ");
			String nomeEditora = br.readLine();
			NestedLoop relation = new NestedLoop("Categoria","Livro","Autor");
			relation.open(descCategoria, nomeAutor, nomeEditora);
			
			relation.close();
		}
	}
	
	public static void mergeJoinConsulta(int tipo) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if(tipo==1){
			System.out.println("Nome do autor: ");
			String nomeAutor = br.readLine();
			MergeJoin relation = new MergeJoin("Autor","Livro");
			relation.open(nomeAutor,tipo);
			
			relation.close();
		}
		else if(tipo==2){
			System.out.println("Descrição da Categoria: ");
			String descCategoria = br.readLine();
			MergeJoin relation = new MergeJoin("Categoria","Livro");
			relation.open(descCategoria,tipo);
			
			relation.close();
		}
		else if(tipo==3){
			System.out.println("Nome do autor: ");
			String nomeAutor = br.readLine();
			MergeJoin relation = new MergeJoin("Autor","Livro");
			relation.open(nomeAutor,tipo);
			
			relation.close();
		}
		else{
			System.out.println("Nome do Autor: ");
			String nomeAutor = br.readLine();
			System.out.println("Descrição da Categoria: ");
			String descCategoria = br.readLine();
			System.out.println("Nome da Editora: ");
			String nomeEditora = br.readLine();
			MergeJoin relation = new MergeJoin("Categoria","Livro","Autor");
			relation.open(descCategoria, nomeAutor, nomeEditora);
			
			relation.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int op;  
		System.out.println("Qual consulta deseja realizar?(1,2,3,4) ");
		op = Integer.parseInt(br.readLine());
		
		switch (op) {
			case 1:
				//realiza o primeiro exercício utilizando o nested loop ou merge join ou hash join, a escolha será feita pelo catálogo
				//nestedLoopConsulta(1);
				//mergeJoinConsulta(1);
				testaHashJoin(1);
				break;
			case 2:
				//realiza o segundo exercício utilizando o nested loop ou merge join ou hash join, a escolha será feita pelo catálogo
				//nestedLoopConsulta(2);
				//mergeJoinConsulta(2);
				testaHashJoin(2);
				break;
			case 3:
				//realiza o terceiro exercício utilizando o nested loop ou merge join ou hash join, a escolha será feita pelo catálogo
				//nestedLoopConsulta(3);
				//mergeJoinConsulta(3);
				testaHashJoin(3);
				break;
			case 4:
				//realiza o quarto exercício utilizando o nested loop ou merge join ou hash join, a escolha será feita pelo catálogo
				//nestedLoopConsulta(4);
				//mergeJoinConsulta(4);
				testaHashJoin(4);
				break;
			default:
				break;
		}
	}
}
