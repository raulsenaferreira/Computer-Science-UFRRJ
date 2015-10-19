#include <stdio.h>
#include <cuda.h>

typedef struct biscoito{
	int indiceBloco;
	int indiceThread;
	int global;
} Biscoito;

__global__ void somaMatrizGPU(int *d_vetA, int *d_vetB, int *d_vetC, Biscoito *bis){
	int id = blockDim.x * blockIdx.x + threadIdx.x;

	bis[id].indiceThread = threadIdx.x;
	bis[id].indiceBloco = blockDim.x;
	bis[id].global = id;

	d_vetC[id] = d_vetA[id] + d_vetB[id];
}

int main(){

	int h_Size = 16;
	int i = 0;
	int h_vetA[16]={1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16};
	int h_vetB[16]={1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16};
	int h_vetC[16];	
	int *d_vetA;
	int *d_vetB;	
	int *d_vetC;
	Biscoito *biscoito = (Biscoito *) malloc(16 * sizeof(Biscoito)) ;
	Biscoito *bis;

	cudaDeviceReset();
	cudaMalloc((void**) &d_vetA, h_Size * sizeof(int));
	cudaMalloc((void**) &d_vetB, h_Size * sizeof(int));
	cudaMalloc((void**) &d_vetC, h_Size * sizeof(int));
	cudaMalloc((void**) &bis, 16 * sizeof(Biscoito));

	cudaMemcpy(d_vetA, h_vetA, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	cudaMemcpy(d_vetB, h_vetB, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	cudaMemcpy(d_vetC, h_vetC, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	cudaMemcpy(bis, biscoito, 16 * sizeof(Biscoito),  cudaMemcpyHostToDevice);
	
	somaMatrizGPU<<<8, 2>>>(d_vetA, d_vetB, d_vetC, bis);

	cudaDeviceSynchronize();
	
	cudaMemcpy(h_vetC, d_vetC, h_Size * sizeof(int),  cudaMemcpyDeviceToHost);
	cudaMemcpy(biscoito, bis, 16 * sizeof(Biscoito),  cudaMemcpyDeviceToHost);

	/*for(i=0; i < h_Size; i++){
		printf("%d, ", h_vetC[i]);	
	}*/
	
	for(i=0; i < 2; i++){
		printf("%d, %d, %d -- \n", biscoito[i].indiceBloco, biscoito[i].indiceThread, biscoito[i].global);	
	}
	
	cudaFree(d_vetA);
	cudaFree(d_vetB);
	cudaFree(d_vetC);
	cudaFree(bis);

	return 0;
		
}
