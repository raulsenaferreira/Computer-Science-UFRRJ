%
%
%
%
function graddesc()

clc;
close all;

n     = 1:55;
f1    = .05; f2=.02;
func   = cos(2*pi*f1*n)+cos(2*pi*f2*n);
dfunc = -2*pi*f1*sin(2*pi*f1*n)-2*pi*f2*sin(2*pi*f2*n);
hold off; plot(n, func,'b', n, dfunc, 'g'); hold on;

eta = 2;
%eta= .01;

w = 1;
%w=15;
%w=25;
%w=39;
w=42;
%w=round(1+rand*54); 	% init random value between 1..55
plot(w,func(w),'go');
hold on;

STEPS = 100;
all_w = zeros(1,STEPS);
for i=1 : STEPS

    % Passo.
    w = w - eta * dfunc(round(w));

    % Guardar todos os valores de w.
    all_w(i) = w;    
    
	plot(round(w),func(round(w)),'r+'); grid on; 
    hold on; xlabel('w');
    title( sprintf('Step %d w=%.2f',i,w) );
    pause(.5);
end


figure;
plot( 1:STEPS, all_w);
title( 'Evolução do valor de w' ); grid on;
    
