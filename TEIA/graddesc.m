%GARDDESC
%  Demontration of the Backpropagation Algorithm
%Last updated: 31/03/2000
%by Isabel Milho

n=1:55;
f1=.05; f2=.02;
func=cos(2*pi*f1*n)+cos(2*pi*f2*n);
dfunc=-2*pi*f1*sin(2*pi*f1*n)-2*pi*f2*sin(2*pi*f2*n);
hold off;
plot(n,func);
hold on;

eta=1;
w=round(1+rand*54); 	% init random value between 1..55
plot(w,func(w),'g+');

for i=1 : 1000,
	w=w-eta*dfunc(round(w)),
	plot(round(w),func(round(w)),'r+');,
	pause;
end
