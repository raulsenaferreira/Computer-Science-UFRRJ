function x = jacobi( M, b, N, e )
	% Solve Mx = b
	% The diagonal entries of M and their inverses
	d = diag( M );

	if ~all( d ) 
		error 'at least one diagonal entry is zero';
	end

	invd = d.^-1;
	% Matrix of off-diagonal entires of N
	Moff = M - diag( d );

	% Use d.^-1*b as the first approximation to x
	invdb = invd.*b;
	x = db;

	%              -1
	% Iterate x = D  (b - M   *x)
	%                      off
	for k = 1:N
		xprev = x;
		x = invdb - invd.*(Moff*x);

		if norm( x - xprev, inf ) < e
			return;
		end
	end

	error 'the method did not converge';
end