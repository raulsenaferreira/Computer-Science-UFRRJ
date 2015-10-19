#include <stdio.h>
#include <stdlib.h>
/**** C�digo pesquisado no wikip�dia e comentado por mim, explicando cada peda�o do c�digo.
        No final do c�digo tamb�m coloquei a op��o de descomentar o printf e assim
         exibir cada atualiza��o completa do vetor dentro do algoritmo ****/

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

    for ( ; ; ) {//loop infinito, por isso o for est� sem par�metros, a condi��o de parada � o "break;" que faz sair da itera��o mesmo estando dentro do else.
        if (i > 0){
            i--; //decrementamos o valor de i pois no c o indice come�a no 0 e queremos pegar o meio do vetor e caminhando com i em dire��o ao in�cio
            temp = vet[i]; //temp armazena o valor contido na metade do vetor, e vai armazenando valores da metade para o �nicio


        }
        else{
            n--;//o i entrar� aqui quando chegar ao valor 0, ou seja, o i ser� o indice da primeira casa
                // estamos decrementando o n pois o valor da �ltima casa j� ser� o maior valor do vetor, por tanto n�o precisamos inclu�-lo na ordena��o.
            if(n == 0)
                return;// ** aqui terminamos a itera��o, tudo est� ordenado. **
            temp = vet[n];//recebendo o valor da �ltima casa corrente para posteriormente ser usado no vetor pai
            vet[n] = vet[0];//aqui come�a a ordena��o do vetor em ordem crescente, colocando o maior valor pra �ltima casa

        }

        pai = i; // pai � �ndice do n� pai que tem uma folha como seu filho
        filho = i*2 + 1; // filho � �ndice da folha, ou seja, �ltimo valor atual da �rvore (vetor), o +1 serve pra corrigir o decremento que foi feito antes.

        while (filho < n){
            if((filho + 1 < n) && (vet[filho + 1] > vet[filho])){
                filho++;// se o valor do filho for maior do que o seu pred, ent�o o �ndice deve ser incrementado visando uma troca posterior

            }

            if(vet[filho] > temp){
                vet[pai] = vet[filho];//se o valor contido no n� filho for maior do que o valor contido no n� pai, ent�o a casa que cont�m o n� pai recebe esse valor, obrigando o maior valor a ser o pai, arrumando assim aos poucos a �rvore bin�ria
                pai = filho;//atualizamos o �ndice pai
                filho = pai*2 + 1; //atualizamos o �ndice filho

            }

            else
                break;//condi��o de parada
        }
        vet[pai] = temp;//aqui a �ltima casa recebe o valor da casa do meio, completando assim a troca de posi��es

        /* aqui d� pra ver o passo a passo de ordena��o do algoritmo, basta descomentar
        printf("\nestado atual do vetor: ");
        for(t = 0; t < 6; t++)
            printf("[ %d ]", vet[t]);   */
    }
}


