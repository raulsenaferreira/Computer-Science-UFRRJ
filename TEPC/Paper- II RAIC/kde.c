#define _USE_MATH_DEFINES // for C
#include <math.h>
#include <stdio.h>
#include<stdlib.h>
#include <time.h>

void KDE_Univariante(double* x, int n, double h, double* pdf);
void KDE_Multivariante(double** x, int n, int xLen, double* pdf);
double KernelFunction(double t);
int main()
{
    const int arraySize = 30000;

    //{ 0.91, 1.01 ,0.95 ,1.13, 1.12 ,0.86 ,0.96, 1.17, 1.36 ,1.10 ,0.98 ,1.27 ,1.13 ,0.92, 1.15 ,1.26, 1.14, 0.88, 1.03, 1.00 ,0.98 ,0.94 ,1.09 ,0.92 ,1.10 ,0.95 ,1.05 ,1.05 ,1.11 ,1.15 };
    double *b = malloc(arraySize * sizeof(double));
    double c[30000] = { 0 };
	const double h = 1;
	int i = 0;

	for(i = 0; i < arraySize; i++)
        b[i] = ((double) rand() / (RAND_MAX)) + 0.200001;

    clock_t init,fin;
    double time_spent;

    init = clock();
	KDE_Univariante(b, arraySize, h, c);
    fin = clock();
    time_spent = (double)(fin - init) / CLOCKS_PER_SEC;
    int k;
	/*for(k = 0; k < 30; k++)
        printf("%f ",c[k]);*/

        printf("%f %f %f", c[0], c[arraySize/2], c[arraySize - 1]);

    return 0;
}

void KDE_Univariante(double* x, int n, double h, double* pdf)
{
    int i,j;
    double kernel;
    double position;

    for(i = 0; i < n; i++)
    {
        kernel = 0.0;
        for(j = 0; j < n; j++)
        {
            long double position = x[i] - x[j];
            kernel += KernelFunction(position/(double)h)/h;
        }
        pdf[i] = kernel/n;
    }
}


void KDE_Multivariante(double** x, int n, int xLen, double* pdf)
{
    int i,j,k, h = 1;
    double kernel, prodKernel;

    for(i = 0; i < n; i++)
    {
        kernel = 0.0;
        for(j = 0; j < n; j++)
        {
            prodKernel = 1.0;
            for(k = 0; k < xLen; k++)
            {
                prodKernel *= KernelFunction((x[i][k] - x[j][k])/h)/h;
            }
            kernel += prodKernel;
        }
        pdf[i] = kernel/n;
    }
}

double KernelFunction(double t)
{
    long double gaussian;

    gaussian = (1/sqrt(2 * M_PI)) * exp(-pow(t,2)/2);

    return gaussian;
}
