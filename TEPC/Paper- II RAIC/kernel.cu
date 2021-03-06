#define _USE_MATH_DEFINES
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include "math_constants.h"
#include "math_functions.h"
#include <stdlib.h>
#include <time.h>

#include <math.h>
#include <stdio.h>

cudaError_t Calculate_KDE(const double *x, double *pdf, const int h, unsigned int size);
__device__ double GaussianKernel(double t);

__global__ void KDE(const double *x, double *pdf, const int h, const int size)
{
    int id = blockIdx.x * blockDim.x + threadIdx.x;
	int i = 0;
	double sum = 0;

	for(i = 0; i < size; i++)
	{
		sum += GaussianKernel((x[id] - x[i])/h)/h;
	}
	pdf[id] = sum/size;
}

__device__ double GaussianKernel(double t)
{
	double gaussian;

	gaussian = (1/sqrt(2 * M_PI)) * exp(-pow(t,2)/2);

	return gaussian;
}

__global__ void KDEMultivariante(const double *x, double *pdf, const int h, const int size, const int vecSize)
{
	int id = blockIdx.x * blockDim.x + threadIdx.x;
	int i = 0, j = 0;
	double sum = 0, prod = 1;

	for(i; i < size; i++)
	{
		prod = 1;

		for(j; j < vecSize; j++)
		{
			prod *= GaussianKernel(x[id*vecSize + j] - x[i*vecSize + j]);
		}
		sum += prod;
	}

	pdf[id] = sum/size;
}

int main()
{
    const int arraySize = 30000;

    //{ 0.91, 1.01 ,0.95 ,1.13, 1.12 ,0.86 ,0.96, 1.17, 1.36 ,1.10 ,0.98 ,1.27 ,1.13 ,0.92, 1.15 ,1.26, 1.14, 0.88, 1.03, 1.00 ,0.98 ,0.94 ,1.09 ,0.92 ,1.10 ,0.95 ,1.05 ,1.05 ,1.11 ,1.15 };
	double *b = (double*)malloc(arraySize * sizeof(double));
	double c[30000] = {0};

	const double h = 1;
	int i = 0;

	for(i = 0; i < arraySize; i++)
        b[i] = ((double) rand() / (RAND_MAX)) + 0.200001;

	/*for(i = 0; i < arraySize; i++)
        c[i] = 0.0;*/

    clock_t init,fin;
    double time_spent;

	printf("%f %f %f\n", b[0], b[arraySize/2], b[arraySize - 1]);

    init = clock();
    // Add vectors in parallel.
    cudaError_t cudaStatus = Calculate_KDE(b, c, h, arraySize);
	fin = clock();
    time_spent = (double)(fin - init) / CLOCKS_PER_SEC;

    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "addWithCuda failed!");
        return 1;
    }

	/* int k;
	for(k = 0; k < 30; k++)
        printf("%f \n",c[k]);*/

	printf("%f %f %f\n", c[0], c[arraySize/2], c[arraySize - 1]);
	printf("Executado em %f segundos\n", time_spent);

    // cudaDeviceReset must be called before exiting in order for profiling and
    // tracing tools such as Nsight and Visual Profiler to show complete traces.
    cudaStatus = cudaDeviceReset();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaDeviceReset failed!");
        return 1;
    }

    return 0;
}

// Helper function for using CUDA to add vectors in parallel.
cudaError_t Calculate_KDE(const double *x, double *pdf, const int h, unsigned int size)
{
	double *observation = 0;
    double *result = 0;

    cudaError_t cudaStatus;

    // Choose which GPU to run on, change this on a multi-GPU system.
    cudaStatus = cudaSetDevice(0);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaSetDevice failed!  Do you have a CUDA-capable GPU installed?");
        goto Error;
    }

    // Allocate GPU buffers for three vectors (two input, one output)    .
    cudaStatus = cudaMalloc((void**)&result, size * sizeof(double));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed!");
        goto Error;
    }

    cudaStatus = cudaMalloc((void**)&observation, size * sizeof(double));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed!");
        goto Error;
    }


    // Copy input vectors from host memory to GPU buffers.
    cudaStatus = cudaMemcpy(observation, x, size * sizeof(double), cudaMemcpyHostToDevice);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy 1 failed!");
        goto Error;
    }

    cudaStatus = cudaMemcpy(result, pdf, size * sizeof(double), cudaMemcpyHostToDevice);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy  2 failed!");
        goto Error;
    }

    // Launch a kernel on the GPU with one thread for each element.
    KDE<<<128, 256>>>(observation, result, h, size);

    // Check for any errors launching the kernel
    cudaStatus = cudaGetLastError();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "addKernel launch failed: %s\n", cudaGetErrorString(cudaStatus));
        goto Error;
    }
    
    // cudaDeviceSynchronize waits for the kernel to finish, and returns
    // any errors encountered during the launch.
    cudaStatus = cudaDeviceSynchronize();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaDeviceSynchronize returned error code %d after launching addKernel!\n", cudaStatus);
        goto Error;
    }

    // Copy output vector from GPU buffer to host memory.
    cudaStatus = cudaMemcpy(pdf, result, size * sizeof(double), cudaMemcpyDeviceToHost);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy 3 failed!");
        goto Error;
    }

Error:
    cudaFree(result);
    cudaFree(observation);
    
    return cudaStatus;
}
