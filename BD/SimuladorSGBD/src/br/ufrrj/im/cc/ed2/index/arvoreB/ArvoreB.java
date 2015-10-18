package br.ufrrj.im.cc.ed2.index.arvoreB;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;


public class ArvoreB<Chave extends Comparable<Chave>, Valor>  {
    private static final int maximoFilhos = 4;    // maximo de filhos por arvore, no = maximoFilhos -1
    private No raiz;             // raiz da arvore
    private int altura;                // altura da arvore
    private int nElementos;              
    private Relacao relacaoConstrucao;

    // helper B-tree node data type
    private static final class No {
        private int qtdFilhos;                             // numero de filhos
        private Entrada[] filhos = new Entrada[maximoFilhos];          	// vetor de filhos
        private No(int k) { qtdFilhos = k; }             // cria um no com k filhos
    }

    
    private static class Entrada {
        private Comparable chave;
        private Object valor;
        private No proximo;     // interacao dos elementos dentro da arvore
        public Entrada(Comparable chave, Object valor, No proximo) {
            this.chave   = chave;
            this.valor = valor;
            this.proximo  = proximo;
        }
    }

    public ArvoreB() { raiz = new No(0); }
 
    // procura valor pela chave
    public Valor get(Chave c) { return busca(raiz, c, altura); }
    private Valor busca(No x, Chave c, int altura) {
        Entrada[] filhos = x.filhos;

        // no externo
        if (altura == 0) {
            for (int j = 0; j < x.qtdFilhos; j++) {
                if (equal(c, filhos[j].chave)) return (Valor) filhos[j].valor;
            }
        }

        // no interno
        else {
            for (int j = 0; j < x.qtdFilhos; j++) {
                if (j+1 == x.qtdFilhos || menor(c, filhos[j+1].chave))
                    return busca(filhos[j].proximo, c, altura-1);
            }
        }
        return null;
    }


    // insere elemento
    public void put(Chave key, Valor value) {
        No split = insere(raiz, key, value, altura); 
        nElementos++;
        if (split == null) 
        	return;

        // pega a raiz dividida e joga na nova raiz
        No novaRaiz = new No(maximoFilhos/2);
        novaRaiz.filhos[0] = new Entrada(raiz.filhos[0].chave, null, raiz);
        novaRaiz.filhos[1] = new Entrada(split.filhos[0].chave, null, split);
        raiz = novaRaiz;
        altura++;
    }


    private No insere(No no, Chave chave, Valor valor, int altura) {
        int j;
        Entrada t = new Entrada(chave, valor, null);

        //se é folha
        if (altura == 0) {
            for (j = 0; j < no.qtdFilhos; j++) {
                if (menor(chave, no.filhos[j].chave)) 
                	break;
            }
        }
        
        else {
            for (j = 0; j < no.qtdFilhos; j++) {
                if ((j+1 == no.qtdFilhos) || menor(chave, no.filhos[j+1].chave)) {
                    No split = insere(no.filhos[j++].proximo, chave, valor, altura-1);
                    //se nulo, não splita porque não chegou no maximo de filhos, caso contrário, splita 
                    if (split == null) 
                    	return null;
                    t.chave = split.filhos[0].chave;
                    t.proximo = split;
                    break;
                }
            }
        }

        for (int i = no.qtdFilhos; i > j; i--) no.filhos[i] = no.filhos[i-1];
        no.filhos[j] = t;
        no.qtdFilhos++;
        if (no.qtdFilhos < maximoFilhos) 
        	return null;
        else
        	return split(no);
    }

    // divide No pela metade
    private No split(No h) {
        No novoNo = new No(maximoFilhos/2);
        h.qtdFilhos = maximoFilhos/2;
        for (int j = 0; j < maximoFilhos/2; j++)
            novoNo.filhos[j] = h.filhos[maximoFilhos/2+j]; 
        return novoNo;    
    }

    // menor
    private boolean menor(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }
    // igual?
    private boolean equal(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    
    public  ArvoreB<String, String> geraArvore(String relacao, String nomeEditora) {
		  this.relacaoConstrucao = new Relacao(relacao);
		  Tupla tupla;
		
		  relacaoConstrucao.open();
		  ArvoreB<String, String> arvore = new ArvoreB<String, String>();
		  String arvoreIndice = new String();
		  
		  while ((tupla = (Tupla) relacaoConstrucao.next()) != null) {
			  String nomeLivro = (String) tupla.getValoresRelacao().get(1);
			  String nE = (String) tupla.getValoresRelacao().get(2);
			  
			  if(nE.equals(nomeEditora)){
				  arvoreIndice+=nomeLivro+"\n";
			  }
		  }
		  arvore.put(nomeEditora, arvoreIndice);
		  
		  relacaoConstrucao.close();

		  return arvore;
	  }
}
