#include <stdio.h>
#include <cuda.h>
#include <math.h>

__global__ void somaMatrizGPU(int *d_vetA, int indice, int passo){

	int id = blockDim.x * blockIdx.x + threadIdx.x;
	
	if((id % indice) == 0)

		d_vetA[id] += d_vetA[id+passo];
}

int main(){
	
	int h_Size = 16;
	int j, i = 0;
	int h_vetA[16]={1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16};
	int *d_vetA;
	int passo, indice;
	int block = h_Size;

	cudaDeviceReset();
	cudaMalloc((void**) &d_vetA, h_Size * sizeof(int));

	cudaMemcpy(d_vetA, h_vetA, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	
	for(i = 0; i < 4; i++){
		
		indice = pow(2, i+1);
		passo = pow(2, i);

		somaMatrizGPU<<<8, 2>>>(d_vetA, indice, passo);

		cudaMemcpy(h_vetA, d_vetA, h_Size * sizeof(int),  cudaMemcpyDeviceToHost);

		for(j=0; j < h_Size; j++){
			printf("%d, ", h_vetA[j]);	
		}
		printf("\n");
	}
	
	cudaDeviceSynchronize();
	
	cudaFree(d_vetA);

	return 0;
		
}
