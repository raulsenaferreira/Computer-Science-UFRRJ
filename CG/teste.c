/*
 *  Este programa ilustra o uso das funcoes da liblioteca GLUT
 *  para interagir com o sistema de janelas.
 * 
 *  author: Ting (3/08/08)
 */

#include <GL/glut.h>
#include <stdlib.h>
#include <math.h>

#define X 5*.525731112119133606
#define Z 5*.850650808352039932
#define PI  2*3.14159265358979323846  /* pi */


int objeto=1;

// Esfera
static GLfloat vdata1[12][3] = {
   {-X, 0.0, Z}, {X, 0.0, Z}, {-X, 0.0, -Z}, {X, 0.0, -Z},
   {0.0, Z, X}, {0.0, Z, -X}, {0.0, -Z, X}, {0.0, -Z, -X},
   {Z, X, 0.0}, {-Z, X, 0.0}, {Z, -X, 0.0}, {-Z, -X, 0.0}
   };

static GLfloat vdata2[12][3] = {
   {-X+1, 0.0-2, Z+4}, {X+1, 0.0-2, Z+4}, {-X+1, 0.0-2, -Z+4}, {X+1, 0.0-2, -Z+4},
   {0.0+1, Z-2, X+4}, {0.0+1, Z-2, -X+4}, {0.0+1, -Z-2, X+4}, {0.0+1, -Z-2, -X+4},
   {Z+1, X-2, 0.0+4}, {-Z+1, X-2, 0.0+4}, {Z+1, -X-2, 0.0+4}, {-Z+1, -X-2, 0.0+4}
   };

static GLuint vindices[20][3] = {
  {1,4,0}, {4,9,0}, {4,5,9}, {8,5,4}, {1,8,4},
  {1,10,8}, {10,3,8}, {8,3,5}, {3,2,5}, {3,7,2},
  {3,10,7}, {10,6,7}, {6,11,7}, {6,0,11}, {6,1,0},
  {10,1,6}, {11,0,9}, {2,11,9}, {5,2,9}, {11,2,7}
};

// Cubo
static GLfloat vdata[8][3] = {
   {-4.0, -4.0, -4.0}, {4.0, -4.0, -4.0}, {4.0, 4.0, -4.0}, {-4.0, 4.0, -4.0},
   {-4.0, -4.0, 4.0}, {4.0, -4.0, 4.0}, {4.0, 4.0, 4.0}, {-4.0, 4.0, 4.0}
   };

static GLuint c_vindices[6][4] = {
   {0,3,2,1}, {2,3,7,6}, {0,4,7,3},
   {1,2,6,5}, {4,5,6,7}, {0,1,5,4}
   };

void plano(void)
{
  int i, j;
  
  /* desenhar o plano */
  /* Observe que neste caso o plano estÃ¡ orientado "de costas" (vetor
     normal - 1,-1,-1) para o observador (localizado na origem,
     olhando para -z). Portanto, este vai ver a sequencia de arestas 
     no sentido horario.
  */
  glFrontFace (GL_CW);  
  glBegin(GL_QUADS);
  for (j=0; j<6; j++) 
    for (i=0; i<8; i++) {
      glVertex3f(i*1.0,j*1.0,i-j+3.0);
      glVertex3f((i+1)*1.0,j*1.0,(i+1)-j+3.0);
      glVertex3f((i+1)*1.0,(j+1)*1.0,(i+1)-(j+1)+3.0);
      glVertex3f(i*1.0,(j+1)*1.0,i-(j+1)+3.0);
    }
  glEnd();
  
  glFrontFace (GL_CCW);  /* Voltar para orientacao anti-horÃ¡ria */
}

void esfera_centrada(void)
{
  int i;

  /* desenhar esfera centrada na origem */
  glBegin(GL_TRIANGLES);
  for (i=0; i<20; i++) {
    glVertex3fv(&vdata1[vindices[i][0]][0]);
    glVertex3fv(&vdata1[vindices[i][1]][0]);
    glVertex3fv(&vdata1[vindices[i][2]][0]);
  }
  glEnd();
}

void esfera(void)
{
  int i;

  /* desenhar esfera centrada no ponto (1,-2,4) */
  glBegin(GL_TRIANGLES);
  for (i=0; i<20; i++) {
    glVertex3fv(&vdata2[vindices[i][0]][0]);
    glVertex3fv(&vdata2[vindices[i][1]][0]);
    glVertex3fv(&vdata2[vindices[i][2]][0]);
  }
  glEnd();
}

void cone(void)
{
  int i;
  double step = PI/19.;
  double base[19][2];

  /* desenhar cone */
  base[0][0] = 5.0*cos(0);
  base[0][1] = 5.0*sin(0);
  /* desenhar laterais */
  glBegin(GL_TRIANGLES);
  for (i=1; i<19; i++) {
    base[i][0] = 5.0*cos(i*step);
    base[i][1] = 5.0*sin(i*step);
    glVertex3f(0.0,base[i-1][0],base[i-1][1]);
    glVertex3f(0.0,base[i][0],base[i][1]);
    glVertex3f(8.0,0.0,0.0);
  }
  glVertex3f(0.0,base[18][0],base[18][1]);
  glVertex3f(0.0,base[0][0],base[0][1]);
  glVertex3f(8.0,0.0,0.0);
  glEnd();
    
  /* desenhar base */
  glBegin(GL_POLYGON);
  for (i=0; i<19; i++)
    glVertex3f(0.0, base[i][0],base[i][1]);
  glEnd();
}

void cubo(void)
{
   int i;

   /* desenhar o cubo */
   for (i=0; i<6; i++) {
     glBegin(GL_POLYGON);
     glVertex3fv(&vdata[c_vindices[i][0]][0]);
     glVertex3fv(&vdata[c_vindices[i][1]][0]);
     glVertex3fv(&vdata[c_vindices[i][2]][0]);
     glVertex3fv(&vdata[c_vindices[i][3]][0]);
     glEnd();
   } 
}

void init(void) 
{
   glClearColor (0.0, 0.0, 0.0, 0.0);
   glShadeModel (GL_SMOOTH);
   glFrontFace (GL_CCW);  /* Modo default */
   glEnable (GL_CULL_FACE);
   glCullFace (GL_FRONT);
}

void display(void)
{
   glClear (GL_COLOR_BUFFER_BIT);
   glLoadIdentity();

   glTranslatef(0.0,0.0,-20.0);

   /* colorir com cinza claro */
   glColor3f (0.9, 0.9, 0.9);
   glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); /* Modo default */
   switch(objeto) {
     case 1:
       plano();
       break;
     case 2:
       cubo();
       break;
     case 3:
       esfera_centrada();
       break;
     case 4:
       esfera();
       break;
     case 5:
       cone();
       break;
   }

   /* desenhar linhas vermelhas */
   glColor3f (1.0, 0.0, 0.0);
   glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
   switch(objeto) {
     case 1:
       plano();
       break;
     case 2:
       cubo();
       break;
     case 3:
       esfera_centrada();
       break;
     case 4:
       esfera();
       break;
     case 5:
       cone();
       break;
   }
     
   glFlush ();
}

void reshape (int w, int h)
{
   glViewport (0, 0, (GLsizei) w, (GLsizei) h); 
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity ();
   glFrustum (-1.0, 1.0, -1.0, 1.0, 1.5, 40.0);
   glMatrixMode (GL_MODELVIEW);
}

void right_menu (int id)
{
  if (id == 1) exit(0);
}

void middle_menu (int id)
{
  objeto = id;
 
  // forcar evento de exibicao para atualizar a area de desenho
  glutPostRedisplay();
}

void esfera_menu (int id)
{
  objeto = id;

  // forcar evento de exibicao para atualizar a area de desenho
  glutPostRedisplay();
}

int main(int argc, char** argv)
{
  int sub_menu;

   glutInit(&argc, argv);
   glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
   glutInitWindowSize (500, 500); 
   glutInitWindowPosition (100, 100);
   glutCreateWindow (argv[0]);
   glutDisplayFunc(display);
   // Associar ao botao direito um menu 
   glutCreateMenu(right_menu);
   glutAddMenuEntry("Fecha", 1);
   glutAttachMenu(GLUT_RIGHT_BUTTON);
   // Criar sub-menu
   sub_menu = glutCreateMenu(esfera_menu);
   glutAddMenuEntry("Esfera centrada", 3);
   glutAddMenuEntry("Esfera em (1,-2,4)", 4);
   // Associar ao botao do meio um menu
   glutCreateMenu(middle_menu);
   glutAddMenuEntry("Plano", 1);
   glutAddMenuEntry("Cubo", 2);
   glutAddSubMenu("Esfera", sub_menu);
   glutAddMenuEntry("Cone", 5);
   glutAttachMenu(GLUT_MIDDLE_BUTTON);

   init ();
   glutReshapeFunc(reshape);

   glutMainLoop();
   return 0;
}
