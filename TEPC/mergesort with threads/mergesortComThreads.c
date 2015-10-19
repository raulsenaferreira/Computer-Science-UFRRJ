#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <sys/types.h>
#include <time.h>
#define _BLOCK 1000000
#define _TAM 1000000

typedef struct index {

	int id;
	int size;
	
} Index;

void * runBaby(void * ind);
void mergeSort(int inicio, int vecSize, Index ind);
void merge(int inicio, int vecSize);
void mergeSection(int firstPoint, int firstSize, int secondPoint, int secondSize, int fullSize);

int vec[_TAM];
int _NUMTHREAD;

int main(void) {

	

	/* Calculando número de threads */
	
	if((_TAM % _BLOCK) != 0 )
	
		_NUMTHREAD = _TAM / _BLOCK + 1;
		
	else
	
		_NUMTHREAD = _TAM / _BLOCK;

	/* --- */

	Index ind[_NUMTHREAD];

	pthread_t id[_NUMTHREAD];

	int i = 0, 
		j = 0, 
		k = 0;

	/* Lendo arquivo com a lista não ordenada */

	FILE *arq = fopen("output1000000.txt", "r");

	while(!feof(arq)) {
	
		fscanf(arq, "%d", &vec[k++]);
	}

	/*for(i = 0; i < _TAM; i++) 
	
		printf("%d - %d\n", i, vec[i]);*/

	/* --- */

	/* Dividindo a lista não ordenada em blocos e criando as threads */

	clock_t init = clock();

	for(j = 0; j < _NUMTHREAD; j++) {
		
			ind[j].id = j;
			
			if((_TAM % _BLOCK) != 0 ) {
			
				if(j + 1 == _NUMTHREAD)
			
					ind[j].size = _TAM % _BLOCK;

				else
			
					ind[j].size = _BLOCK;
					
			}
			
			else {
			
				ind[j].size = _BLOCK;
			}

			pthread_create(&id[j], NULL, runBaby, (void *) &ind[j]);
	}

	/* --- */
	
	for(j = 0; j < _NUMTHREAD; j++)
	
		pthread_join(id[j], NULL);
		
		clock_t end = clock();
	
	float result = (float)(end - init) / CLOCKS_PER_SEC;
	
	printf("%d - Threads\n%d - Blocks\nTempo MergeSort: %.20f segundos\n",_NUMTHREAD, _BLOCK, result);

	for(j = 0; j < _NUMTHREAD - 1; j++) {
	
		mergeSection(ind[j].id * _BLOCK, ind[j].size, ind[j+1].id*_BLOCK, ind[j+1].size, ind[j].size+ind[j+1].size);
		ind[j+1].id = ind[j].id;
		ind[j+1].size += ind[j].size;
	}
	
	/*for(i = 0; i < _TAM; i++) 
	
		printf("%d\n", vec[i]);*/
	
	
	clock_t end1 = clock();
	
	float result1 = (float)(end1 - end) / CLOCKS_PER_SEC;
	
	printf("Fim MergeSection: %f segundos\nTempo Total: %f segundos\n\n", result1, result+result1);
	

	return 0;
}

void * runBaby(void * ind) {

	int i;

	Index index = *(Index *) ind;
	
	int inicio = index.id * _BLOCK;

	int fim = (inicio + index.size) - 1;
	
	//printf("id = %d. tamanho = %d. inicio = %d. fim = %d\n\n", index.id, index.size, inicio, fim);

	mergeSort(inicio, index.size, index);
}

void mergeSort(int inicio, int vecSize, Index ind) {
	
	int mid = vecSize / 2;

	/*int i = 0;

	if(ind.id == 9) {

		i = inicio;

		do{

			printf("%d ", vec[i++]);
			
		}while(i<vecSize+inicio);
		
		//printf("\nInicio %d - VecSize %d - Mid %d\n\n", inicio, vecSize, mid);
	}*/

	if (vecSize > 1) {
		
		mergeSort(inicio, mid, ind);
		mergeSort(inicio + mid, vecSize - mid, ind);
			
		merge(inicio, vecSize);
	}
}

void merge(int inicio, int vecSize) {

	int mid;
	int i, j, k;
	int* tmp;

	tmp = (int*) malloc(vecSize * sizeof(int));

	if (tmp == NULL) {
		exit(1);
	}
 
	mid = (vecSize) / 2;
 
	i = inicio;
	j = mid + inicio;
	k = 0;

	while (i < mid+inicio && j < vecSize+inicio) {

		if (vec[i] <= vec[j]) {
			tmp[k] = vec[i++];
		}

		else {
			tmp[k] = vec[j++];
		}

		++k;
	}
	if (i == mid+inicio) {

		while (j < vecSize+inicio) {

			tmp[k++] = vec[j++];
		}
	}

	else {

		while (i < mid+inicio) {

			tmp[k++] = vec[i++];
		}
	}

	k = 0;

	for (i = inicio; i < vecSize+inicio; i++) {
		
		vec[i] = tmp[k++];
	
	}
	
	free(tmp);
}

void mergeSection(int firstPoint, int firstSize, int secondPoint, int secondSize, int fullSize) {

	int mid;
	int i, j, k;
	int* tmp;

	tmp = (int*) malloc(fullSize * sizeof(int));

	if (tmp == NULL) {
		exit(1);
	}
  
	i = firstPoint;
	j = secondPoint;
	k = 0;

	while (i < secondPoint && j < fullSize) {

		if (vec[i] <= vec[j]) {
			tmp[k] = vec[i++];
		}

		else {
			tmp[k] = vec[j++];
		}

		++k;
	}
	if (i == secondPoint) {

		while (j < fullSize) {

			tmp[k++] = vec[j++];
		}
	}

	else {

		while (i < secondPoint) {

			tmp[k++] = vec[i++];
		}
	}

	k = 0;

	for (i = firstPoint; i < fullSize; i++) {
		
		vec[i] = tmp[k++];
	
	}
	
	free(tmp);
}
