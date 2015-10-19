
load simplefit_dataset

%%
X = simplefitInputs';
X = [ones(size(X,1),1) X];

Y = simplefitTargets';

%%
plot(X(:,2),Y(:,1),'bx')
%%

THETA = [1 ; 1];
%%
alpha = 0.0001;
C = J(X, Y, THETA )
C0 = C + 10000;
%%
%while (norm(C - C0) > 0.0000001)
    C0 = J( X, Y, THETA )

%%gradiente descendente
    for i = 1:size(X,2)
        temp(i) = THETA(i) - alpha*(1/size(X,1))*sum((h(X, THETA) - Y).*X(:,i));
    end

    THETA = temp';
    C = J( X, Y, THETA )
    
    plot(X(:,2),Y(:,1),'bo')
    hold on
    plot(X(:,2),h(X,THETA),'k-')
    hold off
%end

%%
plot(X(:,2),Y(:,1),'bo')
hold on
plot(X(:,2),h(X,THETA),'r--')


%%
THETA = pinv(X'*X)*X'*Y;

plot(X(:,2),h(X,THETA),'g')