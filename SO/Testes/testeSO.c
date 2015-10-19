#include <stdio.h>
#include <unistd.h>

int main(){
	
	printf("processo: %d\n",getpid());
	pid_t id = fork();

	if(id == 0){
		printf("Processo filho!! -> %d, cujo o pai eh->%d\n",getpid(),getppid());
		printf("ID de usuario real e efetivo->%d - %d\n", getuid(), geteuid());
		printf("ID de grupo real e efetivo->%d - %d\n", getgid(), getegid());
	}
	else{
		printf("Processo pai!! -> %d\n",getpid());
		printf("ID de usuario real e efetivo->%d - %d\n", getuid(), geteuid());
		printf("ID de grupo real e efetivo->%d - %d\n", getgid(), getegid());
	}
	
	return 0;
}
