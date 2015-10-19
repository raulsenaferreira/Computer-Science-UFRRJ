#include <stdio.h>
#include <cuda.h>
#include <math.h>

__global__ void somaMatrizGPU(int *d_vetA, int passo){

	int id = blockDim.x * blockIdx.x + threadIdx.x;
	
	if(id < passo)
		d_vetA[id] += d_vetA[id+passo];
}

int main(){
	
	int h_Size = 16;
	int j, i = 0;
	int h_vetA[16]={1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16};
	int *d_vetA;
	int passo, indice;
	int block = h_Size;

	passo = h_Size;

	cudaDeviceReset();
	cudaMalloc((void**) &d_vetA, h_Size * sizeof(int));

	cudaMemcpy(d_vetA, h_vetA, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	
	for(i = 0; i < (int) ceil(log(h_Size) / log(2)) ; i++){
		
		passo = passo / 2;

		somaMatrizGPU<<<8, 2>>>(d_vetA, passo);

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
