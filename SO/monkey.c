/* 

Universidade Federal Rural do Rio de Janeiro
Sistemas Operacionais - 2013/2

Luiz Fernando dos Reis de Oliveira
Raul Sena Ferreira

 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/syscall.h>
#include <sys/timeb.h>
#include <semaphore.h>

/* Definitions */
#define MAX_CROSSING 10
#define NUMBER_MONKEYS 10
#define CROSS_RATE 70
#define LENGHT_STEP 1 
#define NUMBER_STEPS 2
#define LEFT 0
#define RIGHT 1

/* Structs */

typedef struct
{
	int side;
	pid_t monkey_id;
} pthread_args;

/* Global */

int crossing;
int goingRight; 
int goingLeft; 
int count;

pthread_mutex_t mux;
sem_t sem;

/* Signatures */

int side(void);
void* monkeyDay(void* monkey_args);
void crossRight(pthread_args* monkey);
void crossLeft(pthread_args* monkey);
void crosses(pthread_args* monkey);

/* Functions */

int side(void)
{
	int rate = rand() % 100;

	if(rate < 50)
		return LEFT;
	else
		return RIGHT;
}

void* monkeyDay(void* monkey_args)
{
	pthread_args* monkey = ((pthread_args*) monkey_args);
	int cross;
	
	monkey->monkey_id = syscall( __NR_gettid );
	monkey->side = side();
	
	while(1)
	{
		cross = rand() % 100;
		
		if(cross < CROSS_RATE)
		{
			if(monkey->side == LEFT)
				crossRight(monkey);
			else
				crossLeft(monkey);
		}
	
		else
		{
			printf("\nHello, I'm monkey %d and I do not want to cross. Bye! \n", monkey->monkey_id);
			
			return;
		}
	}
}

void crossRight(pthread_args* monkey)
{
	int control, flag;
	
	do {
		pthread_mutex_lock(&mux);
	
			if (crossing == 0)
			{
				goingLeft = 0;
				goingRight = 1;
				crossing = 1;
				count = 0;
				flag = 1;
			}
			else if((goingRight == 1) && (count >= MAX_CROSSING))
			{
				flag = 0;
			}

			flag = flag && (goingRight == 1);

		pthread_mutex_unlock(&mux);

	} while (!flag);
	
	pthread_mutex_lock(&mux);			
		sem_post(&sem); 
		count++; 
	pthread_mutex_unlock(&mux);

	pthread_mutex_lock(&mux);			

	pthread_mutex_unlock(&mux);
	
	crosses(monkey); 

	pthread_mutex_lock(&mux);
		sem_wait(&sem); 
	pthread_mutex_unlock(&mux);

	pthread_mutex_lock(&mux);
		sem_getvalue(&sem, &control);
		
		if(control <= 0)
		{
			crossing = 0;
			goingRight = 0;
			count = 0;
		}

	pthread_mutex_unlock(&mux);
}

void crossLeft(pthread_args* monkey)
{
	int control, flag;

	do {
		pthread_mutex_lock(&mux);
		
			if (crossing == 0)
			{
				goingRight = 0;
				goingLeft = 1;
				crossing = 1;
				count = 0;
				flag = 1;
			}		
			else if((goingLeft == 1) && (count >= MAX_CROSSING))
			{
				flag = 0;
			}

			flag = flag && (goingLeft == 1);

		pthread_mutex_unlock(&mux);
		
	} while (!flag);

	pthread_mutex_lock(&mux);			
		sem_post(&sem);
		count++; 
	pthread_mutex_unlock(&mux);

	pthread_mutex_lock(&mux);			

	pthread_mutex_unlock(&mux);

	crosses(monkey);

	pthread_mutex_lock(&mux);
		sem_wait(&sem);
	pthread_mutex_unlock(&mux);

	pthread_mutex_lock(&mux);
		sem_getvalue(&sem, &control);
		
		if(control <= 0)
		{
			crossing = 0;
			goingLeft = 0;
			count = 0;
		}

	pthread_mutex_unlock(&mux);

}

void crosses(pthread_args* monkey)
{
	int i;

	for(i = 0; i < NUMBER_STEPS; i++)
		sleep(LENGHT_STEP);

	if(monkey->side == RIGHT)
	{
		monkey->side = LEFT;
		printf("\nHello, I'm monkey %d and I already have crossed L -> R.", monkey->monkey_id);
	}
	else
	{
		monkey->side = RIGHT;
		printf("\nHello, I'm monkey %d and I already have crossed R -> L.", monkey->monkey_id);
	}
}

int main(int argc, char **argv)
{
	/* Random componentes */
	struct timeb tmb;

	ftime(&tmb);
	srand(tmb.time * 1000 + tmb.millitm);

	/* Thread variables */
	pthread_t monkeys_id[NUMBER_MONKEYS];
	pthread_args monkeys_args[NUMBER_MONKEYS];
	
	/* Other variables */
	int i = 0;
	
	/* Mutex and Semaphore initialization */
	pthread_mutex_init(&mux, NULL);
	sem_init(&sem, 0, 0);
	
	do
	{
		pthread_create(&monkeys_id[i], NULL, monkeyDay, (void*) &monkeys_args[i]);
		i++;
	}while(i < NUMBER_MONKEYS);
	
	for(i = 0; i < NUMBER_MONKEYS; i++)
	{
    	pthread_join(monkeys_id[i], NULL);
    }
	
	/* Finish */
	sem_close(&sem);
	
	return 0;
}
