typedef struct lista Lista;
typedef struct contato Contato;
int existeNaLista(Lista* lista, char* nome);
Lista* criarLista(void);
Lista* buscarNaLista(Lista* lista, char* nome);
Lista* retiraDaLista(Lista* lista, char* nome);
Lista* insereOrdenadoNaLista(Lista* lista, char* nome, char* telefone);
void imprimirLista(Lista* lista, int novaAgenda);
void liberaLista(Lista* lista);
Lista* retiraRepetidoDaLista(Lista* lista, char* nome);
Lista* retiraDaLista(Lista* lista, char* nome);
