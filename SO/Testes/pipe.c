#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int main(){
	int pipedesc[2], ret, n;
	char buff[10];
	ret = pipe(pipedesc);
   
	if ( ret < 0 ) {
		printf("Erro no pipe.\n");
		exit(1);
	}
  
	ret = fork();
   
	if(ret < 0){
		printf("Erro na criação de um novo processo.\n");
		exit(1);
	}
	else if ( ret == 0 ){//está no filho
		close(pipedesc[1]);
		
		while((n=read(pipedesc[0],buff,sizeof(buff)))<= 0);
		buff[0]='R';
		write(1, buff, n); 
		printf("\n");
		close(pipedesc[0]);
	}
	else{//está no pai
		close(pipedesc[0]);
		buff[1]='A';
		buff[2]='U';
		buff[3]='L';
		write(pipedesc[1], buff , 4);
		close(pipedesc[1]);
	}
	
	return 0;
}
