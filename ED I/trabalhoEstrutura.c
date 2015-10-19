#include<stdio.h>
#include<stdlib.h>
#include<string.h>

struct lista{
    char* cpf;
    char* nome;
    struct lista* prox;
}; typedef struct lista Lista;


Lista* criarLista(void);
Lista* inserirNaLista(Lista* lista, char* cpf, char* nome);
Lista* inserirNaLstSemRepet(Lista* lista, char* cpf, char* nome);
Lista* buscarNaLista(Lista* lista, char* cpf);
Lista* retiraDaLista(Lista* lista, char* cpf);
Lista* insereOrdenadoNaLista(Lista* lista, char* cpf, char* nome);
Lista* insereOrdNaLstSemRepet(Lista* lista, char* cpf, char* nome);
void imprimirLista(Lista* lista);
void liberaLista(Lista* lista);
int listaVazia(Lista* lista);


int main(void){
    char* cpf = (char*)malloc(sizeof(char));
    char* nome = (char*)malloc(sizeof(char));
    int i, qtd;

    Lista* lista;

    lista = criarLista();
    /*
    printf("Quantos registros deseja inserir na lista?\n");
    scanf("%d", &qtd);

    for(i = 0; i < qtd; i++){
        printf("Insira seu CPF: ");
        scanf("%s",cpf);
        printf("Insira seu Nome: ");
        scanf("%s",nome);

        lista = insereOrdNaLstSemRepet(lista, cpf, nome);
    }

    */
    // basta fazer os testes utilizando os métodos, ex. logo abaixo:
    lista = insereOrdNaLstSemRepet(lista, "07348605605", "lol");
    lista = insereOrdNaLstSemRepet(lista, "07348605600", "aaa");
    lista = insereOrdNaLstSemRepet(lista, "07348605600", "bbb");
    lista = insereOrdNaLstSemRepet(lista, "07348605699", "ccc");
    lista = insereOrdNaLstSemRepet(lista, "07048605699", "ddd");
    lista = insereOrdNaLstSemRepet(lista, "07448605699", "eee");


    imprimirLista(lista);

    return 0;
}

void liberaLista(Lista* lista){
    Lista* p = lista;

    while(p != NULL){
        Lista* proximo = p->prox;
        free(p);
        p = proximo;
    }
}

Lista* criarLista(void){
    return NULL;
}

Lista* inserirNaLista(Lista* lista, char* cpf, char* nome){
    Lista* novo = (Lista*)malloc(sizeof(Lista));
    novo->cpf = cpf;
    novo->nome = nome;
    novo->prox = lista;

    return novo;
}

void imprimirLista(Lista* lista){
    Lista* p;

    for(p = lista; p != NULL; p = p->prox){
        printf("Nome: %s, CPF: %s\n", p->nome, p->cpf);
    }
}

int listaVazia(Lista* lista){
    return lista == NULL;
}

Lista* buscarNaLista(Lista* lista, char* cpf){
    Lista* lst;

    for(lst = lista; lst != NULL; lst = lst->prox){
        int ok = strcmp(lst->cpf, cpf);
        if(ok==0){
            printf("encontrado");
            return lst;
        }
    }

    return NULL;
}

Lista* retiraDaLista(Lista* lista, char* cpf){
    Lista* anterior;
    Lista* p = lista;
    int ok = strcmp(p->cpf,cpf);

    while(p != NULL && ok != 0){
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

Lista* insereOrdenadoNaLista(Lista* lista, char* cpf, char* nome){
    Lista* novo;
    Lista* anterior = NULL;
    Lista* p = lista;
    int ok;

    while(p != NULL && (strcmp(p->cpf, cpf) < 0)){
            anterior = p;
            p = p->prox;
    }

    novo = (Lista*)malloc(sizeof(Lista));
    novo->cpf = cpf;
    novo->nome = nome;

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

Lista* inserirNaLstSemRepet(Lista* lista, char* cpf, char* nome){
    Lista* novo = (Lista*)malloc(sizeof(Lista));
    Lista* p;

    for(p = lista; p != NULL; p = p->prox){
        int ok = strcmp(p->cpf, cpf);
        if(ok == 0){
            return NULL;
        }
    }
    novo->cpf = cpf;
    novo->nome = nome;
    novo->prox = lista;

    return novo;
}

Lista* insereOrdNaLstSemRepet(Lista* lista, char* cpf, char* nome){
    Lista* novo;
    Lista* anterior = NULL;
    Lista* p = lista;
    Lista* ok = lista;

    while(p != NULL && (strcmp(p->cpf, cpf) < 0)){
            anterior = p;
            p = p->prox;
    }

    for(p = lista; p != NULL; p= p->prox)
        if(strcmp(p->cpf, cpf) == 0)
            return lista;

    novo = (Lista*)malloc(sizeof(Lista));
    novo->cpf = cpf;
    novo->nome = nome;

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
