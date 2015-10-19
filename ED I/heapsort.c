#include <stdio.h>
#include <stdlib.h>
/**** Código pesquisado no wikipédia e comentado por mim, explicando cada pedaço do código.
        No final do código também coloquei a opção de descomentar o printf e assim
         exibir cada atualização completa do vetor dentro do algoritmo ****/

void heapsort (int vet[], int n);

int main(){
    int i;
    int n = 6;
    int vet[6] = {2,4,3,1,6,5};
    printf("Vetor original: ");
    for (i = 0; i < n; i++)
        printf("[ %d ]", vet[i]);

    heapsort(vet, n);
    printf("\nVetor modificado: ");
    for (i = 0; i < n; i++)
        printf("[ %d ]", vet[i]);
    return 0;
}

void heapsort (int vet[], int n){
    int i = n/2;
    int pai;
    int filho;
    int temp;
    int t;

    for ( ; ; ) {//loop infinito, por isso o for está sem parâmetros, a condição de parada é o "break;" que faz sair da iteração mesmo estando dentro do else.
        if (i > 0){
            i--; //decrementamos o valor de i pois no c o indice começa no 0 e queremos pegar o meio do vetor e caminhando com i em direção ao início
            temp = vet[i]; //temp armazena o valor contido na metade do vetor, e vai armazenando valores da metade para o ínicio


        }
        else{
            n--;//o i entrará aqui quando chegar ao valor 0, ou seja, o i será o indice da primeira casa
                // estamos decrementando o n pois o valor da última casa já será o maior valor do vetor, por tanto não precisamos incluí-lo na ordenação.
            if(n == 0)
                return;// ** aqui terminamos a iteração, tudo está ordenado. **
            temp = vet[n];//recebendo o valor da última casa corrente para posteriormente ser usado no vetor pai
            vet[n] = vet[0];//aqui começa a ordenação do vetor em ordem crescente, colocando o maior valor pra última casa

        }

        pai = i; // pai é índice do nó pai que tem uma folha como seu filho
        filho = i*2 + 1; // filho é índice da folha, ou seja, último valor atual da árvore (vetor), o +1 serve pra corrigir o decremento que foi feito antes.

        while (filho < n){
            if((filho + 1 < n) && (vet[filho + 1] > vet[filho])){
                filho++;// se o valor do filho for maior do que o seu pred, então o índice deve ser incrementado visando uma troca posterior

            }

            if(vet[filho] > temp){
                vet[pai] = vet[filho];//se o valor contido no nó filho for maior do que o valor contido no nó pai, então a casa que contém o nó pai recebe esse valor, obrigando o maior valor a ser o pai, arrumando assim aos poucos a árvore binária
                pai = filho;//atualizamos o índice pai
                filho = pai*2 + 1; //atualizamos o índice filho

            }

            else
                break;//condição de parada
        }
        vet[pai] = temp;//aqui a última casa recebe o valor da casa do meio, completando assim a troca de posições

        /* aqui dá pra ver o passo a passo de ordenação do algoritmo, basta descomentar
        printf("\nestado atual do vetor: ");
        for(t = 0; t < 6; t++)
            printf("[ %d ]", vet[t]);   */
    }
}


