%
%
%
%
function grad()

close all;

figure;
w = -2*pi : 0.01 : 2*pi;
subplot(2,1,1); plot(w,cos(w)); grid on; title('cos(x)');
subplot(2,1,2); plot(w,-sin(w)); grid on; title('-sin(x)');

N = 500;
x0  = pi/4; 
x1  = [ x0, zeros(1,N)]
x2  = [ x0, zeros(1,N)]
x3  = [ x0, zeros(1,N)]

eta1 = 0.01;
eta2 = 0.1;
eta3 = 0.5;

for i=1 : 1 : N,
    d = -sin(x1(i));	x1(i+1) = x1(i) - eta1*d;
    d = -sin(x2(i));	x2(i+1) = x2(i) - eta2*d;
    d = -sin(x3(i));	x3(i+1) = x3(i) - eta3*d;
    i = i + 1;
end

figure;
plot(0:1:N,x1,0:1:N,x2, 0:1:N,x3); 
grid on;
legend('eta=0.01','eta=0.1', 'eta=0.5', -1);


