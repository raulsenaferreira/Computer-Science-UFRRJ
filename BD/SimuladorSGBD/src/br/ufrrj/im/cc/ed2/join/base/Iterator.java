package br.ufrrj.im.cc.ed2.join.base;

public interface Iterator {

	Iterator open();
	Iterator next();
	Iterator close();
	public double custo();
	
}
