%
% 
%
%
function funcao1()

%
% O vector de pontos.
x = -75 : 0.1 : 75;

% A frequencia 1
a=.05; 

% A frequencia 2
b=.02;

% A constante (offset)
k = 0;

% A funcao 1
f1 = k + cos(2*pi*a*x) + cos(2*pi*b*x);

close all;
figure;
derivada_f1 = -2*pi*a*sin(2*pi*a*x) - 2*pi*b*sin(2*pi*b*x);
plot(x,f1,x,derivada_f1);
grid on;
xlabel(' x ');
ylabel(' f(x) ');
title (' f(x)=cos(2*pi*a*x) + cos(2*pi*b*x) e sua derivada');
legend(' Função f(x)', 'Derivada f''x()', -1 );




