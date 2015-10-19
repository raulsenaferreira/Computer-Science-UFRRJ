#include<stdio.h>
#include<math.h>
#include<string.h>
#include<stdlib.h>

 convertebinario(int dec, char *str, int npaginas){
  
  
int i,q[80],r[80],z=0,j,a=0; 

  i=0;                   
  while (dec != 1){     
    q[i]= dec / 2;  
    r[i] = dec % 2; 
    dec=q[i];
    i++;
  }
   j = npaginas - (i+1);
 
  z=0;
  for(a=0;a<j;a++){
    str[z] = '0';
    z++;
  }
  
  if (q[i-1] == 1) {
       str[z] = '1';
       z++;
   }
  
  while ((i>=0)&& (z < npaginas)) {    
     if (r[i-1] == 1)  
       str[z] = '1';
     else 
       str[z] = '0';
     
     z++;  
     i--;
   }
   
  
return(0);
}


int main(int argc, char *argv[]){
	

	float d = 0;
	int proc[10000];
	int npaginas =0;
	int flag=0;
	int **matproc;
	if(argc < 5){	
		printf(" Entrada: <tamanho da pagina> <numero de bits do end. logico> <numero total de requisicoes no arquivo> <numero de processos>\n");
		fflush(stdout);
		exit(1);
	}

	d = log2f((float) atoi(argv[1]));
	npaginas = atoi(argv[2]) - d;
	int endlogico = atoi(argv[2]);
	FILE *arq;
	arq = fopen("germem2.txt", "w");
	
	// sorteio
	int Iacesso, num, i=0, j, z;
	char Cacesso, *str, *str2;
	int numreq = (int)atoi(argv[3]);
	int numproc = ((int)atoi(argv[4]));
	int *numreqproc;
	int idproc;
	int gerado=0;
	char zero = '0';
	
	str = (char *) malloc(npaginas * sizeof(char));
	
	
	matproc = (int **)malloc( numproc * sizeof(int*));
	for(i=0;i < numproc;i++)
	  matproc[i] = (int *)malloc( numreq * sizeof(int));
	
	numreqproc = (int *)malloc( numproc * sizeof(int));
	
	for(i=0;i < numproc;i++){
	  numreqproc[i] = 0;
	  for(j=0;j < numreq;j++)
	    matproc[i][j] = -1;
	}
	
	 z=0;
	while(z < numreq){
	    
	    Iacesso = rand()%2;
	    if(Iacesso == 1) Cacesso = 'L'; else  Cacesso = 'E';
	    idproc = rand()%numproc;
	    //L:
	    num = gerado + rand()% (int)(pow(npaginas,2)) +1;
	    gerado = num; 
	    
	    for(i=0;i < numproc;i++){
	      if(i != idproc){
		for(j=0;j < numreq;j++)
		    if (matproc[i][j] == num){
		      printf("Achou");
		      exit(1);
		    }
	      }
		    
	    }
	    
	    matproc[idproc][numreqproc[idproc]] = num;
	    numreqproc[idproc]++;
	    
	    convertebinario(num, str, npaginas);
	    
	    fprintf (arq,"%d %c ", idproc, Cacesso);
	    
	    for(i=0;i < endlogico - npaginas;i++)
	      fprintf (arq,"%c",zero);
	    
	    for(i=0;i < npaginas;i++)
	      fprintf (arq,"%c", str[i]);
	    fprintf(arq,"\n");
	    z++;
	}
	fclose(arq);
	return 1;

}
