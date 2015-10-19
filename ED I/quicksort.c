#include<stdio.h>
#include<stdlib.h>
/**** Código pesquisado e comentado por mim em todas as partes do código ****/

void swap(int* a, int* b) { // serve apenas para fazer trocas, assim não é preciso escrever toda a hora
  int tmp;
  tmp = *a;
  *a = *b;
  *b = tmp;
}

int partition(int vec[], int left, int right) {
  int i, j;

  i = left;
  for (j = left + 1; j <= right; ++j) {// aqui é ordenada a partição de acordo com o pivô escolhido (sublistas)
    if (vec[j] < vec[left]) {
      ++i;
      swap(&vec[i], &vec[j]);
    }
  }
  swap(&vec[left], &vec[i]);

  return i;
}

void quickSort(int vec[], int left, int right) {
  int r;

  if (right > left) {
    r = partition(vec, left, right);// aqui organizamos o vetor em partições (sublistas)
    quickSort(vec, left, r - 1);// aqui ordenamos a primeira partição (início até o pivô)
    quickSort(vec, r + 1, right);// aqui ordenamos a segunda partição (pivô até o fim)
  }
}
int main(){
    int vet[6] = {4,7,2,8,1,3};
    int n = 6;
    int i;
    printf("vetor original: ");
    for(i = 0; i < n; i++)
        printf("%d, ", vet[i]);

    quickSort(vet, 0, n-1);
    printf("\nvetor ordenado: ");
    for(i = 0; i< n; i++)
        printf("%d, ", vet[i]);

    return 0;
}
