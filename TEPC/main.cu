#include <stdio.h>
#include <cuda.h>
__global__ void somaMatrizGPU(int *d_vetA, int *d_vetB, int *d_vetC){
	int id = blockDim.x * blockIdx.x + threadIdx.x;	
	
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

	cudaDeviceReset();
	cudaMalloc((void**) &d_vetA, h_Size * sizeof(int));
	cudaMalloc((void**) &d_vetB, h_Size * sizeof(int));
	cudaMalloc((void**) &d_vetC, h_Size * sizeof(int));

	cudaMemcpy(d_vetA, h_vetA, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	cudaMemcpy(d_vetB, h_vetB, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	cudaMemcpy(d_vetC, h_vetC, h_Size * sizeof(int),  cudaMemcpyHostToDevice);
	
	somaMatrizGPU<<<8, 2>>>(d_vetA, d_vetB, d_vetC);

	cudaDeviceSynchronize();

	
	cudaMemcpy(h_vetC, d_vetC, h_Size * sizeof(int),  cudaMemcpyDeviceToHost);

	for(i=0; i < h_Size; i++){
		printf("%d", h_vetC[i]);	
	}
	
	cudaFree(d_VetA);
	cudaFree(d_VetB);
	cudaFree(d_VetC);

	return 0;
		
}
