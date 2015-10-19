#include<stdio.h>
#include <stdlib.h>
/**** C�digo pesquisado no livro do Cormen e comentado por mim em todas as parte do c�digo ****/
void merge(int vec[], int vecSize);

int main(){
    int vet[6] = {4,5,1,6,2,3};
    int n = 6;
    int i;
    printf("vetor original: ");
    for(i = 0; i < n; i++)
        printf("%d, ", vet[i]);

    mergeSort(vet, n);
    printf("\nvetor ordenado: ");
    for(i = 0; i< n; i++)
        printf("%d, ", vet[i]);

    return 0;
}

void merge(int vec[], int vecSize) {
  int mid;
  int i, j, k;
  int* tmp;

  tmp = (int*) malloc(vecSize * sizeof(int));//tmp sera um vetor temporario do mesmo tamanho do vetor original
  if (tmp == NULL) {
    exit(1);//se o vetor tiver tamanho 0 encerra o algoritmo.
  }

  mid = vecSize / 2;// valor inicial de vecSize ser� 2 e depois � aumentado com o outro par de numeros (primeira metade do tamanho do vetor orignal e depois a segunda metade), devido as aplica��es na fun��o mergeSort

  i = 0;
  j = mid;
  k = 0;

  while (i < mid && j < vecSize) {
//se o valor do primeiro for menor que o valor do meio, tmp recebe valor do primeiro e anda uma casa, caso contrario recebe o valor do meio e anda uma casa
    if (vec[i] < vec[j]) {
      tmp[k] = vec[i];
      ++i;

    }
    else {
      tmp[k] = vec[j];
      ++j;

    }
    ++k;// andando no vetor tmp atrav�s do incremento de seu �ndice

  }

  if (i == mid) {//i == a meio, enquanto j < n, tmp[k] recebe valor do vec[j], incrementando o indice dos dois vetores antes de gerar a sa�da
    while (j < vecSize) {
      tmp[k] = vec[j];
      ++j;
      ++k;
    }
  }
  else {//i!= meio, enquanto i < meio, tmp[k] recebe vec[i], incrementando os 2 indices antes de gerar a sa�da.
    while (i < mid) {
      tmp[k] = vec[i];
      ++i;
      ++k;

    }
  }

  for (i = 0; i < vecSize; ++i) {//aqui o vetor original recebe os valores ordenados em tmp, por fim a �ltima execu��o trar� todos os valores ordenados em tmp, ordenando assim o vetor original por completo.
    vec[i] = tmp[i];
    //printf("\n%d", vec[i]); coloquei o print aqui apenas para mostrar os valores sendo passados para o vetor definitivo
  }

  free(tmp);//libera espa�o no vetor tmp para nova aloca��o (novo tamanho)

}

void mergeSort(int vec[], int vecSize) {
  int mid;

  if (vecSize > 1) {

    mid = vecSize / 2;

    mergeSort(vec, mid);

    mergeSort(vec + mid, vecSize - mid);//aqui � onde o tamanho da primeira metade do vetor ser� reajustado depois de cada itera��o do merge descrito acima

    merge(vec, vecSize);

  }
}
