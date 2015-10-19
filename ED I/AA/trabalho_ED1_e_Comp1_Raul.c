#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "listaEncadeada.h"

/*struct contato{
    char nome[81]; // nome do contato
    char tel[10];  // ddd+número de contato, ex. 2155557777
}; typedef struct contato Contato;
*/
struct lista{
    char* nome; // nome do contato
    char* tel;  // ddd+número de contato, ex. 2155557777
    struct lista* prox;
}; typedef struct lista Lista;

void apresentacao(Lista* agenda);

int main(){
    Lista* agenda;
    agenda = criarLista();
    apresentacao(agenda);

    return 0;
}

Lista* criarLista (void){
    return NULL;
}
int listaVazia(Lista* lista){
    return lista == NULL;
}
int existeNaLista(Lista* lista, char* nome){
    char* palavra = (char*)malloc(sizeof(char));
    int fim;
    int isRepetido = 0;
    Lista* lst;
    FILE* entrada;

    entrada = fopen("agenda.txt", "r+");

    if(entrada == NULL){
        printf("Criando novo arquivo!");
        entrada = fopen("agenda.txt", "w+");
        //exit(1);
    }

    while(!feof(entrada)){
        fscanf(entrada,"%s", palavra);
        if(strcmp(palavra, nome) == 0)
            isRepetido = 1;
    }

    fclose(entrada);

    for(lst = lista; lst != NULL; lst = lst->prox)
        if(strcmp(lst->nome, nome)==0)
            isRepetido = 1;

    free(palavra);

    return isRepetido;
}

Lista* retiraRepetidoDaLista(Lista* lista, char* nome){
   Lista* anterior;
   Lista* p;//iterador
   Lista* l;
   l = criarLista();

   int fim;

   FILE* entrada;
   entrada = fopen("agenda.txt", "r+");

   if(entrada == NULL){
       printf("arquivo não pôde ser aberto!");
       exit(1);
   }

   while(!feof(entrada)){
        char* palavra = (char*)malloc(sizeof(char));
        char* tel = (char*)malloc(sizeof(char));
        fscanf(entrada,"%s", palavra);
        fscanf(entrada,"%s", tel);
        l = insereOrdenadoNaLista(l, palavra, tel);
        imprimirLista(l, 0);
   }

    fclose(entrada);
    p = lista; //ponteiro iterador da lista
    //enquanto não chegar ao final da lista e o elemento igual não for localizado.
    while((p != NULL) && (strcmp(p->nome,nome) != 0)){
       anterior = p;
       p = p->prox;
   }

   if(p == NULL) //não há elemento repetido (ou a lista está vazia)
       return lista; //Não remove ninguém

   if(anterior == NULL) //O elemento repetido é o primeiro
       lista = p->prox; //Remove o primeiro elemento

   else
       anterior->prox = p->prox; //Remove o elemento repetido

   free(p);
   //imprimirLista(l, 1);
   return lista;
}
Lista* insereOrdenadoNaLista(Lista* lista, char* nome, char* telefone){
    Lista* novo;
    Lista* anterior = NULL;
    Lista* p = lista;

    while(p != NULL && (strcmp(p->nome, nome) < 0)){
            anterior = p;
            p = p->prox;
    }

    novo = (Lista*)malloc(sizeof(Lista));
    novo->nome = nome;
    novo->tel = telefone;

    if(anterior == NULL){
        novo->prox = lista;
        lista = novo;
    }

    else{
        novo->prox = anterior->prox;
        anterior->prox = novo;
    }

    return lista;
}
Lista* buscarNaLista(Lista* lista, char* nome){
    char* palavra = (char*)malloc(sizeof(char));
    char* tel = (char*)malloc(sizeof(char));
    char escolha;
    int naoEncontrado = 1;
    Lista* lst;
    FILE* entrada;

    entrada = fopen("agenda.txt", "r+");

    while(!feof(entrada)){
        fscanf(entrada,"%s", palavra);
        fscanf(entrada,"%s", tel);

        if(strcmp(palavra, nome) == 0){
            printf("Nome: %s\t Telefone: %s\n",palavra, tel);
        }
    }

    fclose(entrada);

    free(palavra);

    for(lst = lista; lst != NULL; lst = lst->prox){
        int ok = strcmp(lst->nome, nome);
        if(ok==0){
            naoEncontrado = 0;
            printf("Nome: %s\n",lst->nome);
            printf("Telefone: %s\n",lst->tel);
            imprimirLista(lst, 0);
        }
    }

    if(naoEncontrado == 1){
        printf("Contato não encontrado!\n");
    }

    printf("Deseja voltar ao menu? (S) Sim ou (N) Não\n");
    getchar();
    scanf("%c", &escolha);

    if(escolha == 'S' || escolha == 's')
        apresentacao(lst);
    else{
        imprimirLista(lst, 0);
        exit(1);
    }
}
void imprimirLista(Lista* lista, int novaAgenda){
    Lista* p;
    FILE *saida;

    if(novaAgenda==0)
        saida = fopen("agenda.txt", "a");

    else{
        saida = fopen("agenda.txt", "w");
    }

    if(saida == NULL){
        printf("Erro ao gravar arquivo.");
        exit (1);
    }

    for(p = lista; p != NULL; p = p->prox){
        fprintf(saida, "%s\t",p->nome);
        fprintf(saida, "%s\n",p->tel);
    }

    fclose(saida);
}
Lista* retiraDaLista(Lista* lista, char* nome){
    char* palavra = (char*)malloc(sizeof(char));
    char* tel = (char*)malloc(sizeof(char));
    char escolha;
    int naoEncontrado = 1;
    FILE* entrada;

    entrada = fopen("agenda.txt", "r+");

    while(!feof(entrada)){
        fscanf(entrada,"%s", palavra);
        fscanf(entrada,"%s", tel);

        if(strcmp(palavra, nome) != 0){
            lista = insereOrdenadoNaLista(lista, palavra, tel);
            printf("entrou no 1o if");
        }
    }

    fclose(entrada);

    free(palavra);

    Lista* anterior;
    Lista* p = lista;


    while(p != NULL && strcmp(p->nome,nome)!=0){
            anterior = p;
            p = p->prox;
    }

    if(p == NULL)
        return lista;

    if(anterior == NULL)
        lista = p->prox;
    else
        anterior->prox = p->prox;

    free(p);

    return lista;
}
void apresentacao(Lista* agenda){
    char* nome = (char*)malloc(sizeof(char));
    char* telefone = (char*)malloc(sizeof(char));
    int numero;
    system("cls");
    system("clear");
    printf("\t\t Bem vindo(a) a Super Agenda! \n");
    printf("**************************************************************\n");
    printf("\tEscolha 1 para inserir um contato na Agenda: \n");
    printf("\tEscolha 2 para procurar um contato na Agenda: \n");
    printf("\tEscolha 3 para excluir um contato na Agenda: \n");
    printf("\tEscolha qualquer outra tecla para para sair da Agenda: \n");
    printf("**************************************************************\n");
    printf("Escolha uma das opções acima: ");
    scanf("%d", &numero);
    char escolha;
    switch(numero){
        case 1:
            printf("Digite o nome do contato: \n");
            scanf("%s", nome);
            int ok = existeNaLista(agenda, nome);
            if(ok==0){
                printf("Digite o telefone do contato: \n");
                scanf("%s", telefone);
                agenda = insereOrdenadoNaLista(agenda, nome, telefone);
                printf("Deseja voltar ao menu? (S) Sim ou (N) Não\n");
                getchar();
                scanf("%c", &escolha);

                if(escolha == 'S' || escolha == 's')
                    apresentacao(agenda);
                else{
                    imprimirLista(agenda, 0);
                    exit(1);
                }
            }
            else{
                printf("Contato já existe na lista! (S) para sobreescrever o telefone, (N) para inserir como novo contato ou qualquer outra tecla para cancelar. \n");
                getchar();
                scanf("%c", &escolha);
                if(escolha=='S'|| escolha == 's'){
                    printf("Digite o telefone do contato: \n");
                    scanf("%s", telefone);
                    agenda = retiraRepetidoDaLista(agenda, nome);
                    agenda = insereOrdenadoNaLista(agenda, nome, telefone);
                    printf("Deseja voltar ao menu? (S) Sim ou (N) Não");
                    getchar();
                    scanf("%c", &escolha);

                    if(escolha == 'S' || escolha == 's')
                        apresentacao(agenda);
                    else{
                        imprimirLista(agenda, 1);
                        exit(1);
                    }
                }

                if(escolha=='N' || escolha=='n'){
                    printf("Digite o telefone do contato: \n");
                    scanf("%s", telefone);
                    agenda = insereOrdenadoNaLista(agenda, nome, telefone);
                    imprimirLista(agenda, 1);
                }
                else
                    apresentacao(agenda);
            }
            break;

        case 2:
            printf("Digite o nome do contato: \n");
            scanf("%s", nome);
            agenda = buscarNaLista(agenda, nome);
            break;

        case 3:
            printf("Digite o nome do contato: \n");
            scanf("%s", nome);
            agenda = retiraDaLista(agenda, nome);
            imprimirLista(agenda, 1);
            break;

        default:
            imprimirLista(agenda, 0);
            exit(1);
    }
}
